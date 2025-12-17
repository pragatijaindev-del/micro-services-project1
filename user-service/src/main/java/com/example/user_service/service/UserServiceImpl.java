package com.example.user_service.service;

import com.example.user_service.entity.User;
import com.example.user_service.exception.BusinessException;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // User registration
    @Override
    public User register(User user) {
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    // User login
    @Override
    public String login(String email, String password) {

        // Fetch user by email
        User user = repo.findByEmail(email);

        if (user == null) {
            throw new BusinessException("User not found");
        }

        // Match raw password with encrypted password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException("Invalid credentials");
        }

        // Generate JWT token
        return jwtUtil.generateToken(user.getEmail());
    }

    // Fetch user by ID
    @Override
    public User getUserById(Long id) {
        return repo.findById(id).orElse(null);
    }
}
