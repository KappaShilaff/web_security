package com.example.security.model;

import lombok.Value;

import javax.validation.constraints.*;

@Value
public class UserDtoRequestUpdateWithUser {
    @NotNull(message = "newUsername is null") @Size(max = 15, message = "max size 15") @Pattern(regexp = "[A-Za-z0-9_]*", message = "incorrect characters")
    String newUsername;
    @Email(message = "incorrect email")
    String email;
}
