package com.aseds.tournamentmicroservice.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@Slf4j
public class JwtUtil {
    private static final String USERNAME_CLAIM = "username";
    private static final String ROLE_CLAIM = "role";

    @Value("${jwt.secret}")
    private String secretKey;

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private String extractClaim(String token, String claimName) {
        return extractAllClaims(token).get(claimName).toString();
    }

    public String extractUsername(String token) {
        return extractClaim(token, USERNAME_CLAIM);
    }


    public String extractRole(String token) {
        return extractClaim(token, ROLE_CLAIM);
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (JwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            log.error("JWT token is empty or null: {}", e.getMessage());
            return false;
        }
    }
}