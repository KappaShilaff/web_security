package com.example.client.dto;

import lombok.Getter;

@Getter
public class UserDtoReqSave {
    private String username;
    private String password;
    private String role;
    private String email;

    public UserDtoReqSave(String username, String password, String role, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }
}
