package com.sqlcinema.backend.common;

import com.sqlcinema.backend.model.UserAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static String JWT_SECRET;
    public static final long ONE_WEEK_AS_MILLISECONDS = 1000 * 60 * 60 * 24 * 7;
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Authorization";
    public static final String LOGOUT_URL = "/api/account/logout";


    @Value("${jwt.secret}")
    public void setJwtSecret(String jwtSecret) {
        JWT_SECRET = jwtSecret;
    }
    
    public static String[] createStringArray(String... strings) {
        return strings;
    }

    public static Object[] createObjectArray(Object... objects) {
        return objects;
    }

    public static String[] getAnonymousEndpoints() {
        return createStringArray(
                "/api/account/register",
                "/api/account/login",
                LOGOUT_URL,
                "/api/movie/**"
        );
    }
    
    public static UserAccount getCurrentUser() {
        return (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
