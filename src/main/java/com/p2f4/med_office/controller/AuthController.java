package com.p2f4.med_office.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p2f4.med_office.config.ApiConfiguration;
import com.p2f4.med_office.core.AuthService;
import com.p2f4.med_office.dto.UserDTO;
import com.p2f4.med_office.dto.LoginResponse;
import com.p2f4.med_office.dto.RefreshTokenRequest;
import com.p2f4.med_office.dto.LoginRequest;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import static com.p2f4.med_office.config.ApiConfiguration.API_BASE_PATH;

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
                        request.getPhoneNumber() 
                        );

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> initSession(@Valid @RequestBody LoginRequest request, HttpServletResponse response){

        LoginResponse loginResponse = authService.Login(request.getEmail(), request.getPassword());
        
        Cookie accessCookie = new Cookie(authCookieName, loginResponse.getToken());
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(true);
        accessCookie.setAttribute("sameSite","None" );
        accessCookie.setPath("/");
        accessCookie.setMaxAge(jwtExpiration.intValue() / 1000); 

        Cookie refreshCookie = new Cookie(refreshCokieName, loginResponse.getRefreshToken());
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        accessCookie.setAttribute("sameSite","None" );
        refreshCookie.setPath(REFRESH_URL);
        refreshCookie.setMaxAge(jwtRefreshExpiration.intValue() / 1000);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);

        return ResponseEntity.status(HttpStatus.OK).body(loginResponse.getUserDTO());
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserDTO> refreshToken(@RequestBody RefreshTokenRequest request, HttpServletResponse response) {
        
        LoginResponse loginResponse = authService.refreshToken(request.getRefreshToken());

        Cookie accessCookie = new Cookie("access_token", loginResponse.getToken());
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(true);
        accessCookie.setAttribute("sameSite","None" );
        accessCookie.setPath("/");
        accessCookie.setMaxAge(jwtExpiration.intValue() / 1000); 

        response.addCookie(accessCookie);

        return ResponseEntity.status(HttpStatus.OK).body(loginResponse.getUserDTO());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response){
        Cookie accessTokenCookie = new Cookie("access_token", null);
        accessTokenCookie.setMaxAge(0);
        accessTokenCookie.setPath("/");
        
        Cookie refreshTokenCookie = new Cookie("refresh_token", null);
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setPath("/api/auth/refresh");
        
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.noContent().build();
    }

}
