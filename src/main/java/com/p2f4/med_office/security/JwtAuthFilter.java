package com.p2f4.med_office.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.p2f4.med_office.core.JwtService;
import com.p2f4.med_office.domain.UserRepository;
import com.p2f4.med_office.entity.User;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Profile("!test")
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Value("${spring.application.security.cookies.auth-cookie.name}")
    private String authCookieName;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService, UserRepository userRepository){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
                @NonNull HttpServletRequest request, 
                @NonNull HttpServletResponse response,
                @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            if (request.getServletPath().contains("/auth/login")) {
                filterChain.doFilter(request, response);
                return;
            }
            
            final Optional<String> token = getJwtFromCookie(request);
            if (token.isEmpty()){
                filterChain.doFilter(request, response);
                return;
            }

            String jwtToken = token.get();
            final String userEmail = jwtService.extractUserEmail(jwtToken);

            if (userEmail == null){
                filterChain.doFilter(request, response);
                return; 
            }

            if (SecurityContextHolder.getContext().getAuthentication() != null){
                filterChain.doFilter(request, response);
                return;
            }

            final UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            final Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
            
            if(user.isEmpty()){
                filterChain.doFilter(request, response);
                return;
            }

            if (!jwtService.isTokenValid(token.get(), user.get())) {
                filterChain.doFilter(request, response);
                return;
            }

            final var authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
        } catch (BadCredentialsException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                {
                    "title": "Invalid token",
                    "detail": "Nombre de usuario o contraseña incorectos"
                }
            """);
            return;
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expired");
            return;
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid Token");
        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Server error");
        }
    }

    private Optional<String> getJwtFromCookie(HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return Optional.empty();
        }
        return (Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals(authCookieName))
            .map(Cookie::getValue)
            .findFirst());
    }

}
