package com.example.backend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtTest {
    private static final String SECRET_KEY = "your_base64_encoded_secret_key";

    public static void main(String[] args) {
        Key signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));

        String jwt = Jwts.builder()
                .setSubject("test")
                .signWith(signingKey)
                .compact();

        System.out.println("Generated JWT: " + jwt);

        // Test parsing the JWT
        String subject = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();

        System.out.println("Extracted Subject: " + subject);
    }
}
