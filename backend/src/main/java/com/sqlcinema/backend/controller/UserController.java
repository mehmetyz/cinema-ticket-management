package com.sqlcinema.backend.controller;

import com.sqlcinema.backend.model.User;
import com.sqlcinema.backend.model.UserAccount;
import com.sqlcinema.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    
    private final Logger logger; 
    private final UserService userService;
    
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable int userId) {
        
        if (userId < 0) {
            return ResponseEntity.badRequest().build();
        }
        
        UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (userAccount.getUserId() != userId) {
            return ResponseEntity.badRequest().build();
        }
        
        logger.info("User id: " + userId);
        User user = userService.getUserById(userId);
        
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(user);
    }
    
    @PutMapping("/{userId}/update")
    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody User user) {
        userService.updateUser(userId, user);
        return ResponseEntity.ok(user);
    }
    
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<User> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
