package com.sqlcinema.backend.service.impl;

import com.sqlcinema.backend.model.User;
import com.sqlcinema.backend.model.request.UserUpdateRequest;
import com.sqlcinema.backend.model.response.UserResponse;
import com.sqlcinema.backend.repository.UserRepository;
import com.sqlcinema.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findUserById(userId);
    }

    @Override
    public void updateUser(int userId, UserUpdateRequest user) {
        userRepository.updateUser(userId, user);
    }

    @Override
    public void deleteUser(int userId) {
        userRepository.deleteUser(userId);
    }

    @Override
    public List<UserResponse> getUsers() {
        return userRepository.getUsers();
    }
}
