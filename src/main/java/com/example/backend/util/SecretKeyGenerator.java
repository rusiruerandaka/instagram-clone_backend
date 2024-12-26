package com.example.backend.util;

import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SecretKeyGenerator {
    public static void main(String[] args) throws Exception {
        // Generate a 256-bit key for HMAC-SHA256
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();

        // Encode the key as a Base64 string
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Generated SECRET_KEY: " + encodedKey);
    }
}
