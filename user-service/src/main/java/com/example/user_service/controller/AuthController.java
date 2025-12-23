package com.example.user_service.controller;

import com.example.user_service.dto.LoginRequest;
import com.example.user_service.entity.User;
import com.example.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        System.out.println("Register Controller");
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Login controller hit");
        return service.login(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @GetMapping("/test")
    public String testPIA() {
        return "test success";
    }
}
