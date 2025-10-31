package com.p2f4.med_office.core;

import com.p2f4.med_office.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${spring.application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${spring.application.security.jwt.expiration}")
    private Long jwtExpiration;
    @Value("${spring.application.security.jwt.refresh-token.expiration}")
    private Long jwtRefreshExpiration;

    public String generateToken(User user){
        return buildToken(user, jwtExpiration);
    }

    public String generateRefreshToken(User user){
        return buildToken(user, jwtRefreshExpiration);
    }
    
    private String buildToken(User user, Long expiration){
        return Jwts.builder()
                   .id(user.getIdUser().toString())
                   .claims(Map.of("name", user.getName(), "idRole", user.getIdRole()))
                   .subject(user.getEmail())
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(new Date(System.currentTimeMillis() + expiration))
                   .signWith(getSignInKey())
                   .compact();

    }

    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserEmail(String token){
        Claims jwtToken = Jwts.parser() 
                          .verifyWith(getSignInKey())
                          .build()
                          .parseSignedClaims(token)
                          .getPayload();
        
        return jwtToken.getSubject();
    }

    public boolean isTokenValid(String token, User user){
        String userEmail = extractUserEmail(token);
        return userEmail.equals(user.getEmail()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        Claims jwtToken = Jwts.parser() 
                          .verifyWith(getSignInKey())
                          .build()
                          .parseSignedClaims(token)
                          .getPayload();
        
        return jwtToken.getExpiration();
    }

}
