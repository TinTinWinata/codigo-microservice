package com.codigo.microservices.api.gateway.utility;

import com.codigo.microservices.api.gateway.constant.PropertyConstant;
import com.codigo.microservices.api.gateway.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;

import java.util.Date;

public class JwtUtility {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String generateToken(User user) {
        try {
            String serializedUser = objectMapper.writeValueAsString(user);
            return Jwts.builder()
                    .setSubject(serializedUser)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + PropertyConstant.JWT_EXPIRATION_TIME_MS))
                    .signWith(PropertyConstant.JWT_SECRET_KEY)
                    .compact();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing user object", e);
        }
    }

    public static String generateRefreshToken(User user) {
        try {
            String serializedUser = objectMapper.writeValueAsString(user);
            return Jwts.builder()
                    .setSubject(serializedUser)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + PropertyConstant.JWT_REFRESH_EXPIRATION_TIME_MS))
                    .signWith(PropertyConstant.JWT_SECRET_KEY)
                    .compact();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing user object", e);
        }
    }

    public static User validateToken(String token) {
        try {
            String serializedUser = Jwts.parserBuilder()
                    .setSigningKey(PropertyConstant.JWT_SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return objectMapper.readValue(serializedUser, User.class);
        } catch (Exception e) {
            throw new RuntimeException("Error validating token", e);
        }
    }
}
