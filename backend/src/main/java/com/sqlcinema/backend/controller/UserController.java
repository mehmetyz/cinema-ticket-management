package com.sqlcinema.backend.controller;

import com.sqlcinema.backend.model.Role;
import com.sqlcinema.backend.model.User;
import com.sqlcinema.backend.model.UserAccount;
import com.sqlcinema.backend.model.response.UserResponse;
import com.sqlcinema.backend.service.UserAccountService;
import com.sqlcinema.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    
    private final Logger logger; 
    private final UserService userService;
    private final UserAccountService userAccountService;
    
    @GetMapping({"/{userId}", "/me"})
    @PreAuthorize("hasAnyAuthority('USER', 'USER_MANAGER', 'ADMIN')")
    public ResponseEntity<UserResponse> getUser(@PathVariable(value = "userId", required = false) Integer userId) {
        if (userId != null && userId < 0) {
            return ResponseEntity.badRequest().build();
        }
        UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (userId == null) {
            userId = userAccount.getUserId();
        }
                                                
        if ((userAccount.getRole() == Role.USER 
                && userAccount.getUserId() != userId)
                || userAccount.getRole() == Role.THEATRE_MANAGER) {
            return ResponseEntity.badRequest().build();
        }
        
        logger.info("User id: " + userId);
        User user = userService.getUserById(userId);
        
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        
        UserResponse userResponse = UserResponse.fromUser(user);
        
        if (userId != userAccount.getUserId()) {
            userAccount = userAccountService.getUserAccountById(userId);
        }
        
        userResponse.setUsername(userAccount.getUsername());
        userResponse.setRole(userAccount.getRole());
        
        return ResponseEntity.ok(userResponse);
    }
    
    @PutMapping("/{userId}/update")
    @PreAuthorize("hasAnyAuthority('USER', 'USER_MANAGER', 'ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody User user) {
        userService.updateUser(userId, user);
        return ResponseEntity.ok(user);
    }
    
    @DeleteMapping("/{userId}/delete")
    @PreAuthorize("hasAnyAuthority('USER', 'USER_MANAGER', 'ADMIN')")
    public ResponseEntity<User> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
