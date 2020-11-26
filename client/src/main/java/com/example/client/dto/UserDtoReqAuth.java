package com.example.client.dto;

import lombok.Data;

@Data
public class UserDtoReqAuth {
    private String username;
    private String password;

    public UserDtoReqAuth(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
