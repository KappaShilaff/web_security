package com.example.client.controller;

import com.example.client.dto.TestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Администрирование тестовых записей"})
@RestController
public class TestController {
    static final String urlTest = "http://localhost:8080/test";

    @ApiOperation(value = "Добавление тестовой записи")
    @PostMapping("/ololo")
    public String postTest(@RequestBody TestDto testDto) {
        HttpEntity<TestDto> requestBody = new HttpEntity<>(testDto, UserController.headers);
        try {
            return UserController.restTemplate.postForObject(urlTest, requestBody, String.class);
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

    @ApiOperation(value = "Вывод всех тестовых записей")
    @GetMapping("/ololo")
    public String getAllTests() {
        HttpEntity<String> httpEntity = new HttpEntity<>(UserController.headers);
        try {
            return UserController.restTemplate.exchange(urlTest, HttpMethod.GET, httpEntity, String.class).getBody();
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

}