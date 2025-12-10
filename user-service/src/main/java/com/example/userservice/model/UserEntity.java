package com.example.userservice.model;



@Entity
public class UserEntity {
    @Id
    private String username;
    private String password;

    public UserEntity() {}
}
