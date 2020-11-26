package com.example.client.controller;

import com.example.client.dto.UserDtoReqAuth;
import com.example.client.dto.UserDtoReqSave;
import com.example.client.utils.EncodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Управление авторизацией"})
@RestController
public class AuthController {
    private final static String urlAuthHeaders = "http://localhost:8010/auth/header";

    @ApiOperation(value = "Авторизация")
    @PostMapping("/auth/header")
    public String getHeaders(@RequestBody UserDtoReqAuth userDtoReqAuth) {
        HttpHeaders buffHeaders = EncodeUtil.getHttpHeaders(userDtoReqAuth);
        HttpEntity<String> httpEntity = new HttpEntity<>(buffHeaders);
        try {
            String result = UserController.restTemplate.exchange(urlAuthHeaders, HttpMethod.GET,
                    httpEntity, String.class).getBody();
            UserController.headers = buffHeaders;
            return result;
        } catch (Exception e) {
            UserController.headers = new HttpHeaders();
            return e.getLocalizedMessage();
        }
    }

    @ApiOperation(value = "Logout")
    @GetMapping("/logout")
    public String logout() {
        UserController.headers = new HttpHeaders();
        return "logout";
    }

}
