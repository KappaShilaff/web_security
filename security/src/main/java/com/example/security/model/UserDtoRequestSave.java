package com.example.security.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Value;

@Value
public class UserDtoRequestSave {
    @NotBlank(message = "newUsername is null") @Size(max = 15, message = "max size 15") @Pattern(regexp = "[A-Za-z0-9_]*", message = "incorrect characters")
    String username;
    @Size(min = 8, max = 30, message = "min 8 max 30") @Pattern(regexp = "[A-Za-z0-9!@#$%^&*()_+=-]*", message = "incorrect characters")
    String password;
    @Email(message = "incorrect email")
    String email;
}
