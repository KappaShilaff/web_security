package com.webapp.users.dao;

import com.webapp.users.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface TestDao extends JpaRepository<Test, Long> {
    Test findByTest(String test);
}
