package com.aeromaintenance.Utils;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtUtil {

    private String secretKey = "hjkloibbmf567tyuyutyuiohjkloibbmf567tyuyutyuio";

    @SuppressWarnings("deprecation")
    public String generateToken(String username, String roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @SuppressWarnings("deprecation")
    public Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("Token is expired: " + e.getMessage());
        } catch (JwtException e) {
            System.out.println("Invalid token: " + e.getMessage());
        }
        return null;
    }

    public String extractUsername(String token) {
        Claims claims = extractClaims(token);
        return (claims != null) ? claims.getSubject() : null;
    }

    public boolean isTokenExpired(String token) {
        Claims claims = extractClaims(token);
        return (claims != null) && claims.getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername != null && extractedUsername.equals(username) && !isTokenExpired(token));
    }
}