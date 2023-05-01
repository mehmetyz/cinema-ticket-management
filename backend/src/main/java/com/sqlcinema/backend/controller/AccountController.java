package com.sqlcinema.backend.controller;

import com.sqlcinema.backend.model.Role;
import com.sqlcinema.backend.model.UserAccount;
import com.sqlcinema.backend.model.request.LoginRequest;
import com.sqlcinema.backend.model.request.RegisterRequest;
import com.sqlcinema.backend.service.JwtService;
import com.sqlcinema.backend.service.UserAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@Controller
@AllArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
    
    private final JwtService jwtService;
    private final UserAccountService userAccountService;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger;
    
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {

        UserAccount userAccount = userAccountService.getUserAccountByUsername(loginRequest.getUsername());
        
        if (passwordEncoder.matches(loginRequest.getPassword(), userAccount.getPassword())) {
            badRequest().body("Invalid password");
        }
        userAccountService.loginUser(userAccount);
        return ok(jwtService.generateToken(userAccount));
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        logger.info("Register request: " + registerRequest);
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userAccountService.registerUser(
                registerRequest.getUsername(), 
                registerRequest.getEmail(),
                registerRequest.getPassword());
        
        return ok("User registered");
    }
    
    
    @PostMapping("/assign-role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> assignUserRole(@RequestParam String username, @RequestParam String role) {
        userAccountService.assignRole(username, Role.valueOf(role));
        return ok("User role assigned");
    }
    
}
