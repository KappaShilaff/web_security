package com.example.client.controller;

import com.example.client.dto.UserDtoReqSave;
import com.example.client.dto.UserDtoReqUpd;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/users")
@Api(tags = {"Администрирование пользоватей"})
@RestController
public class UserController {
    public static HttpHeaders headers = new HttpHeaders();
    public static RestTemplate restTemplate = new RestTemplate();
    private final static String urlUser = "http://localhost:8010/users";

    @ApiOperation(value = "Вывод всех пользователей")
    @GetMapping
    public String getAllUsers() {
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        try {
            return restTemplate.exchange(urlUser,
                    HttpMethod.GET, httpEntity, String.class).getBody();
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

    @ApiOperation(value = "Вывод пользователя по username")
    @GetMapping("/{username}")
    public String getUser(@PathVariable String username) {
        try {
            return restTemplate.getForObject(urlUser + '/' + username,
                    String.class, username);
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

    @ApiOperation(value = "Добавить нового пользователя")
    @PostMapping
    public String postUser(@RequestBody UserDtoReqSave userDtoReqSave) {
        HttpEntity httpEntity = new HttpEntity(userDtoReqSave, headers);
        try {
            return restTemplate.postForEntity(urlUser,
                    httpEntity, String.class).getBody();
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

    @PutMapping()
    public String updateUser(@RequestBody UserDtoReqUpd userDtoReqUpd) {
        HttpEntity httpEntity = new HttpEntity(userDtoReqUpd, headers);
        try {
            return restTemplate.exchange(urlUser, HttpMethod.PUT, httpEntity,
                    String.class).getBody();
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }
}
