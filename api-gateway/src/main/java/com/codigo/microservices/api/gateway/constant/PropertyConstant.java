package com.codigo.microservices.api.gateway.constant;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class PropertyConstant {
    // JWT Constants
    public static final Key JWT_SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static final long JWT_EXPIRATION_TIME_MS =  24 * 60 * 60 * 1000; // 1 Day
}
