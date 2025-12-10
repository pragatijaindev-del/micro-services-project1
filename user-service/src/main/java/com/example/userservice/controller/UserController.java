package com.example.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/users/test")
    public String test (){
        return "Hy User Service";

    }
    @GetMapping("/users/{id}")
public String getUser(@PathVariable String id){
        return "User details via id ";
}
}
