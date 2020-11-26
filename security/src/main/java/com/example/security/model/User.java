package com.example.security.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String username;
    private String password;
    private String email;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Column(name = "not_blocked")
    private boolean notBlocked;

    public User(String username, String password, Role role, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.notBlocked = true;
    }
}
