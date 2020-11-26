package com.example.security.model;

import lombok.ToString;

import java.util.HashSet;
import java.util.Set;


@ToString
public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    public Set<AuthorityImpl> getAuthority() {
        Set<AuthorityImpl> authorities = new HashSet<>();
        authorities.add(new AuthorityImpl(getRole()));
        return authorities;
    }
}
