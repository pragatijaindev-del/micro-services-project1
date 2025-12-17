package com.example.user_service.service;

import com.example.user_service.entity.User;
//Defines user management and authentication operations.
public interface UserService {
    User register(User user);
    String login(String email, String password);
    User getUserById(Long id);
}