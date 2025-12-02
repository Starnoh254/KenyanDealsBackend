package com.starnoh.ecommerce.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final String issuer;
    private final Duration expiration;

    public JwtService(
            @Value("${jwt.secret}") String base64Secret,
            @Value("${jwt.issuer:ecommerce-api}") String issuer,
            @Value("${jwt.expiration-minutes:60}") long expirationMinutes
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
        this.issuer = issuer;
        this.expiration = Duration.ofMinutes(expirationMinutes);
    }

    // Generate a JWT for a given user id
    public String generateToken(Long userId) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration.toMillis());
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate signature, issuer, and expiration
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Extract user id (stored in `sub`)
    public Long extractUserId(String token) {
        Claims claims = parseClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .requireIssuer(issuer)
                .build()
                .parseClaimsJws(stripBearer(token))
                .getBody();
    }

    private String stripBearer(String token) {
        if (token == null) return "";
        String t = token.trim();
        return t.regionMatches(true, 0, "Bearer ", 0, 7) ? t.substring(7).trim() : t;
    }
}
