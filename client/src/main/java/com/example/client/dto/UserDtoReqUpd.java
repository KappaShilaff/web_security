package com.example.client.dto;

import lombok.Data;

@Data
public class UserDtoReqUpd {
    private String newUsername;
    private String oldUsername;

    public UserDtoReqUpd(String newUsername, String oldUsername) {
        this.newUsername = newUsername;
        this.oldUsername = oldUsername;
    }
}
