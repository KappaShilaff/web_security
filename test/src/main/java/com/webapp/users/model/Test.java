package com.webapp.users.model;

import com.webapp.users.dto.TestDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;


import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "t_test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String test;

    public Test(String test) {
        this.test = test;
    }

    public Test(TestDto testDto) {
        this.test = testDto.getTest();
    }

    public Test() {
    }
}
