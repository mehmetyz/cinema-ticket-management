package com.sqlcinema.backend.security;

import com.sqlcinema.backend.manager.ActivityManager;
import com.sqlcinema.backend.model.activity.ActivityType;
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
    private final ActivityManager activityManager;
    
    
    @SneakyThrows
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final int userId = Integer.parseInt(request.getHeader("user-id"));
        final String username = userAccountService
                .getUserAccountById(userId)
                .getUsername();
        userAccountService.logoutUser(username);
        activityManager.addActivity(userId, ActivityType.LOGOUT, "User has logged out");
        request.getSession().invalidate();
        request.logout();
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
