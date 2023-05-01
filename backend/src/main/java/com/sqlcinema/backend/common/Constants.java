package com.sqlcinema.backend.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static String JWT_SECRET;
    public static final long ONE_WEEK_AS_MILLISECONDS = 1000 * 60 * 60 * 24 * 7;
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Authorization";
    public static final String SIGN_UP_URL = "/api/account/register";
    public static final String SIGN_IN_URL = "/api/account/login";
    public static final String LOGOUT_URL = "/api/account/logout";
    
    
    @Value("${jwt.secret}")
    public void setJwtSecret(String jwtSecret) {
        JWT_SECRET = jwtSecret;
    }
}
