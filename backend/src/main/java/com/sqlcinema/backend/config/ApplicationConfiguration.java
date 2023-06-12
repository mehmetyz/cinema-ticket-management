package com.sqlcinema.backend.config;

import com.sqlcinema.backend.common.CustomLogger;
import com.sqlcinema.backend.common.LogFileProcessor;
import com.sqlcinema.backend.service.UserAccountService;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.FileNotFoundException;
import java.util.Collections;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Configuration
@RequiredArgsConstructor
@Order(2)
public class ApplicationConfiguration {
    private final UserAccountService userAccountService;

    @Value("${logger.file.path}")
    private String loggerFilePath;

    @Bean
    public CustomLogger logger() {
        LogFileProcessor processor;
        try {
            processor = new LogFileProcessor(loggerFilePath, objectMapper());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new CustomLogger(processor);
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
        corsConfiguration.setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList(CorsConfiguration.ALL));
        corsConfiguration.setExposedHeaders(Collections.singletonList("X-Total-Count"));
        
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService((UserDetailsService) userAccountService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
