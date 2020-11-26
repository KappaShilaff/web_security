package com.example.security.model;

import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.*;

@Value
@AllArgsConstructor
public class UserDtoRequestUpdateWithAdmin {
    @NotNull(message = "newUsername is null") @Size(max = 15, message = "max size 15") @Pattern(regexp = "[A-Za-z0-9_]*", message = "incorrect characters")
    String newUsername;
    @NotBlank(message = "oldUsername is null or empty") @Size(max = 15, message = "max size 15") @Pattern(regexp = "[A-Za-z0-9_]*", message = "incorrect characters")
    String oldUsername;
    @Email(message = "incorrect email")
    String email;

    public UserDtoRequestUpdateWithAdmin(UserDtoRequestUpdateWithUser requestUpdateWithUser) {
        this.email = requestUpdateWithUser.getEmail();
        this.newUsername = requestUpdateWithUser.getNewUsername();
        this.oldUsername = "";
    }
}
