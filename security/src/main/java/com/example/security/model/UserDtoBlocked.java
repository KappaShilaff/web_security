package com.example.security.model;

import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Value
public class UserDtoBlocked {
    @NotNull(message = "username is null") @Size(max = 15, message = "max size 15") @Pattern(regexp = "[A-Za-z0-9_]*", message = "incorrect characters")
    String username;
    boolean notBlocked;
    Long id;
}
