package com.example.security.repos;

import com.example.security.model.User;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableJpaRepositories
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
