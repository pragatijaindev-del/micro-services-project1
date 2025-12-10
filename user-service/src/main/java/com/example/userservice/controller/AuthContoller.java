package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthContoller {
 @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")  // Handles POST requests for login
    public String login(@RequestBody User user){
        if(user.getUsername().equals("pragati")&& user.getPassword().equals("12345")){
            return jwtUtil.generateToken(user.getUsername());

        }
        return "Invalid Credentials";
    }


    @GetMapping("/validate")  // validating  token, checking authentication
    public String validate(@RequestParam String token)   { // sending token in URL

        return jwtUtil.validateToken(token);
    }

}
