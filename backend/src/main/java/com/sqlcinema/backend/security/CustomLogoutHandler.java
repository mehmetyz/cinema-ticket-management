package com.sqlcinema.backend.security;

import com.sqlcinema.backend.service.UserAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomLogoutHandler implements LogoutHandler {
    
    
    private final UserAccountService userAccountService;
    
    
    @SneakyThrows
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String username = authentication.getName();
        userAccountService.logoutUser(username);
        request.getSession().invalidate();
        request.logout();
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
