package com.codigo.microservices.api.gateway.util;

import com.codigo.microservices.api.gateway.constant.PropertyConstant;
import io.jsonwebtoken.Jwts;

import java.util.Date;

public class JwtUtil {
    public static String generateToken(String phoneNumber) {
        return Jwts.builder()
                .setSubject(phoneNumber)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + PropertyConstant.JWT_EXPIRATION_TIME_MS))
                .signWith(PropertyConstant.JWT_SECRET_KEY)
                .compact();
    }

    public static String validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(PropertyConstant.JWT_SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
