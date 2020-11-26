package com.example.security.security;

import com.example.security.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class SecurityUser implements UserDetails {

    private final String username;
    private final String password;
    private final String email;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isNotBlocked;

    public SecurityUser(String username, String password, List<SimpleGrantedAuthority> authorities, String email, boolean isNotBlocked) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.email = email;
        this.isNotBlocked = isNotBlocked;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNotBlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserDetails fromUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getId().toString(), user.getPassword(), true, true,
                true, user.isNotBlocked() , user.getRole().getAuthority()
        );
    }
}
