package com.webapp.users.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TestDto {
    private String test;

    @JsonCreator
    public TestDto(@JsonProperty("test") String test) {
        this.test = test;
    }
}
