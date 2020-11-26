package com.example.security.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.Authentication;

import java.util.Set;

@Data
public class AuthenticationImpl implements Authentication {

    private boolean authenticated;
    private Set<AuthorityImpl> authorities;
    private Object credentials;
    private Object detail;
    private String name;
    private Object principal;

    @JsonCreator
    public AuthenticationImpl(@JsonProperty("authenticated") boolean authenticated,
                              @JsonProperty("authorities") Set<AuthorityImpl> authorities,
                              @JsonProperty("credentials") Object credentials,
                              @JsonProperty("details") Object detail,
                              @JsonProperty("name") String name,
                              @JsonProperty("principal") Object principal) {
        this.authenticated = authenticated;
        this.authorities = authorities;
        this.credentials = credentials;
        this.detail = detail;
        this.name = name;
        this.principal = principal;
    }

    @Override
    public Set<AuthorityImpl> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getDetails() {
        return detail;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        authenticated = b;
    }

    @Override
    public String getName() {
        return name;
    }
}
