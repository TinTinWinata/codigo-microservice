package com.codigo.microservices.voucher.utility;


import com.codigo.microservices.voucher.constant.PropertyConstant;
import com.codigo.microservices.voucher.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;


public class JwtUtility {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static UserDto validateToken(String token) {
        try {
            String serializedUser = Jwts.parserBuilder()
                    .setSigningKey(PropertyConstant.JWT_SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return objectMapper.readValue(serializedUser, UserDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error validating token", e);
        }
    }
}
