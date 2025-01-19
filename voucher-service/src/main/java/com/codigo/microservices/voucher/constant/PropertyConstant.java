package com.codigo.microservices.voucher.constant;

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

    // Redist
    public static final String REDIS_VOUCHER_KEY = "voucher:";
}
