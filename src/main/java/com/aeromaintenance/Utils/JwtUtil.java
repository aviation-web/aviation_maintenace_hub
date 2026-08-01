package com.aeromaintenance.Utils;

import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtUtil {

    // Use a secure random secret key (at least 256-bit for HS256)
    //private static final String SECRET_KEY = "hjkloibbmf567tyuyutyuiohjkloibbmf567tyuyutyuio";
    //private static final long EXPIRATION_TIME = 60 * 60 * 1000; // 1 hour

    //private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    private final Key key;
    private final Key refreshKey;
    private final long expirationTime;
    
    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.refresh.secret}") String refreshSecret,
            @Value("${jwt.expiration}") long expirationTime) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.refreshKey = Keys.hmacShaKeyFor(refreshSecret.getBytes());
        this.expirationTime = expirationTime;
    }
    // Generate JWT Token
    //@NoLogging
    public String generateToken(String username, String roles) {
        return Jwts.builder()
        		.setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    public String generateRefreshToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(refreshKey, SignatureAlgorithm.HS256)
                .compact();
    }


 // Extract all claims from the token
    public Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
            return null;
        } catch (JwtException e) {
            System.out.println("Invalid JWT: " + e.getMessage());
            return null;
        }
    }

    public Claims extractRefreshClaims(String token) {
    try {
        return Jwts.parserBuilder()
                .setSigningKey(refreshKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

    } catch (ExpiredJwtException e) {
        System.out.println("Refresh token expired: " + e.getMessage());
        return null;

    } catch (JwtException e) {
        System.out.println("Invalid refresh JWT: " + e.getMessage());
        return null;
    }
}

    // Extract username from token
    public String extractUsername(String token) {
        Claims claims = extractClaims(token);
        return claims != null ? claims.getSubject() : null;
    }
    
    public String extractUsernameFromRefreshToken(String token) {
    Claims claims = extractRefreshClaims(token);
    return claims != null ? claims.getSubject() : null;
}

public boolean isTokenExpired(String token) {
    try {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration().before(new Date());

    } catch (ExpiredJwtException e) {
        return true;

    } catch (JwtException e) {
        return true;
    }
}
    // // Check if token is expired
    // public boolean isTokenExpired(String token) {
    //     Claims claims = extractClaims(token);
    //     return claims == null || claims.getExpiration().before(new Date());
    // }

    // Validate token with username
    public boolean validateToken(String token, String username) {
       Claims claims = extractClaims(token);
    return claims != null
            && username.equals(claims.getSubject())
            && claims.getExpiration().after(new Date());
    }
    
    public boolean validateRefreshToken(String token) {
    Claims claims = extractRefreshClaims(token);

    return claims != null &&
           claims.getExpiration().after(new Date());
}
}