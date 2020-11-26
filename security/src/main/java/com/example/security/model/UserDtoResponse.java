package com.example.security.model;

import lombok.Value;

@Value
public class UserDtoResponse {
    Long id;
    String username;
    Role role;
    String email;
    boolean isNotBlocked;

    public UserDtoResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.email = user.getEmail();
        this.isNotBlocked = user.isNotBlocked();
    }
}
