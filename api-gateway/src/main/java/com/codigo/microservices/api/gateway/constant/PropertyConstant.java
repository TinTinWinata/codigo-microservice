package com.codigo.microservices.api.gateway.constant;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class PropertyConstant {
    // JWT Constants
    public static final String SECRET_KEY_STRING = "CODIGOMOBILEAPPDEVELOPMENTCOMPANY";
    public static final Key JWT_SECRET_KEY = new SecretKeySpec(
            SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8),
            "HmacSHA256"
    );
    public static final long JWT_EXPIRATION_TIME_MS =  24 * 60 * 60 * 1000; // 1 Day
    public static final long JWT_REFRESH_EXPIRATION_TIME_MS =  24 * 60 * 60 * 1000 * 7; // 7 Day
}
