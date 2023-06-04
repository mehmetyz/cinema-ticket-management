package com.sqlcinema.backend.service;

import com.sqlcinema.backend.model.User;
import com.sqlcinema.backend.model.request.UserUpdateRequest;
import com.sqlcinema.backend.model.response.UserResponse;

import java.util.List;

public interface UserService {
    User getUserByEmail(String email);
    User getUserById(int userId);
    void updateUser(int userId, UserUpdateRequest user);
    void deleteUser(int userId);

    List<UserResponse> getUsers();
}