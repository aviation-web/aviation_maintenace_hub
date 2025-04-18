package com.aeromaintenance.Utils;

import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtParser;
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
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 )) // 1 hours
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @SuppressWarnings("deprecation")
	public Claims extractClaims(String token) {
        try {
            JwtParser jwtParser = Jwts.parser()
            		.setSigningKey(secretKey).build();
                    

            return jwtParser.parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("Token is expired: " + e.getMessage());
            // Handle expiration logic
        } catch (JwtException e) {
            System.out.println("Invalid token: " + e.getMessage());
            // Handle other JWT-related errors
        }
        return null; // Return null if token is invalid or expired
    }
   


    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
}
