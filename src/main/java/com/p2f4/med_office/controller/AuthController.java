package com.p2f4.med_office.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p2f4.med_office.config.ApiConfiguration;
import static com.p2f4.med_office.config.ApiConfiguration.API_BASE_PATH;
import com.p2f4.med_office.core.AuthService;
import com.p2f4.med_office.dto.LoggedUserDTO;
import com.p2f4.med_office.dto.LoginRequest;
import com.p2f4.med_office.dto.LoginResponse;
import com.p2f4.med_office.dto.UserDTO;
import com.p2f4.med_office.dto.LoggedUserDTO;
import com.p2f4.med_office.dto.LoginRequest;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import static com.p2f4.med_office.config.ApiConfiguration.API_BASE_PATH;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping(API_BASE_PATH + "/auth")
public class AuthController {

    private final AuthService authService;

    @Value("${spring.application.security.jwt.expiration}")
    private Long jwtExpiration;
    @Value("${spring.application.security.jwt.refresh-token.expiration}")
    private Long jwtRefreshExpiration;
    @Value("${spring.application.security.cookies.auth-cookie.name}")
    private String authCookieName;
    @Value("${spring.application.security.cookies.refresh-cookie.name}")
    private String refreshCokieName;

    private final static String REFRESH_URL = ApiConfiguration.API_BASE_PATH + "/auth/refresh";

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createMedicalOffice(
            @Valid @RequestBody UserDTO request) {

        UserDTO newUser = authService.createUser(
                request.getEmail(),
                request.getPassword(),
                request.getIdRole(),
                request.getName(),
                request.getLastName(),
                request.getDocument(),
                request.getDocumentType(),
                request.getPhoneNumber());

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoggedUserDTO> initSession(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {

        LoginResponse loginResponse = authService.Login(request.getEmail(), request.getPassword());

        // Access token
        ResponseCookie accessCookie = ResponseCookie.from(authCookieName, loginResponse.getToken())
            .httpOnly(true)
            .secure(false)
            .sameSite("Lax") 
            .path("/")
            .maxAge(jwtExpiration.intValue() / 1000)
            .build();

        // Refresh token
        ResponseCookie refreshCookie = ResponseCookie.from(refreshCokieName, loginResponse.getRefreshToken())
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .path(REFRESH_URL)
            .maxAge(jwtRefreshExpiration.intValue() / 1000)
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return ResponseEntity.status(HttpStatus.OK).body(loginResponse.getLoggedUserDTO());
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoggedUserDTO> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        
        final Optional<String> token = getTokenFromCookie(request, refreshCokieName);
        if (token.isEmpty()){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new BadCredentialsException("Invalid token");
        }
        String refreshToken = token.get();
        
        LoginResponse loginResponse = authService.refreshToken(refreshToken);

        ResponseCookie accessCookie = ResponseCookie.from("access_token", loginResponse.getToken())
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .path("/")
            .maxAge(jwtExpiration.intValue() / 1000)
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

        return ResponseEntity.status(HttpStatus.OK).body(loginResponse.getLoggedUserDTO());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie accessTokenCookie = ResponseCookie.from("access_token", "")
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .path("/")
            .maxAge(0)
            .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", "")
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .path("/api/auth/refresh")
            .maxAge(0)
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return ResponseEntity.noContent().build();
    }

    private Optional<String> getTokenFromCookie(HttpServletRequest request, String tokenName) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return Optional.empty();
        }
        return (Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals(tokenName))
            .map(Cookie::getValue)
            .findFirst());
    }

}
