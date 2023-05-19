package com.sqlcinema.backend.service;

import com.sqlcinema.backend.model.User;

public interface UserService {
    User getUserByEmail(String email);
    User getUserById(int userId);
    void updateUser(int userId, User user);
    void deleteUser(int userId);
}