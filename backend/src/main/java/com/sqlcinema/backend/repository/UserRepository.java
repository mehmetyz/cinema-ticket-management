package com.sqlcinema.backend.repository;

import com.sqlcinema.backend.model.Role;
import com.sqlcinema.backend.model.User;
import com.sqlcinema.backend.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.logging.Logger;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    
    private final JdbcTemplate jdbcTemplate;
    public UserAccount findUserAccountByUsername(String username) {
        UserAccount userAccount =  jdbcTemplate
                                    .queryForObject("SELECT ua.*, m.role FROM UserAccount ua " +
                                                    "LEFT JOIN Manager m ON ua.user_id = m.user_id " +
                                                    "WHERE ua.username = ?", 
                                                    new BeanPropertyRowMapper<>(UserAccount.class), username);
        
        if (userAccount != null && userAccount.getRole() == null) {
            userAccount.setRole(Role.USER);
        }
        
        return userAccount;
    }

    public UserAccount findUserAccountById(int userId) {
        return jdbcTemplate.queryForObject("SELECT * FROM UserAccount WHERE user_id = ?",
                new BeanPropertyRowMapper<>(UserAccount.class), userId);
    }
    
    
    public void registerUser(String email, String username, String password) {
        jdbcTemplate.update("CALL create_user(?, ?, ?)", username, email, password);
    }
    
    public void loginUser(String username) {
        jdbcTemplate.update("UPDATE UserAccount " +
                            "SET status = 'ACTIVE' " +
                            "WHERE username = ?", username);
    }
    
    public void logoutUser(String username) {
        jdbcTemplate.update("UPDATE UserAccount " +
                            "SET status = 'INACTIVE' " +
                            "WHERE username = ?", username);
    }
    public void assignRole(int userId, Role role) {
        
        
        if (Role.USER.equals(role)) {
            jdbcTemplate.update("unassign_manager(?)", userId);
            return;
        }
        
        jdbcTemplate.update("CALL assign_manager(?, ?)", userId, role.getAuthority());
    }
    
    public void deleteUser(int userId) {
        jdbcTemplate.update("UPDATE UserAccount " +
                            "SET status = 'DELETED' " +
                            "WHERE user_id = ?", userId);
    }
    
    public User findUserById(int userId) {
        return jdbcTemplate.queryForObject("SELECT * FROM User WHERE user_id = ?", 
                                            new BeanPropertyRowMapper<>(User.class), userId);
    }
    
    public void updateUser(int userId, User user) {
        jdbcTemplate.update("UPDATE User " +
                            "SET full_name = ?, avatar_name = ?, phone_number = ?, birth_date = ? " +
                            "WHERE user_id = ?", user.getFullName(), user.getAvatarName(),
                user.getPhoneNumber(), user.getBirthDate(), userId);
        
    }
}                                               