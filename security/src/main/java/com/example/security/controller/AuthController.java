package com.example.security.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@Slf4j
public class AuthController {

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ApiOperation(value = "Проверка header (на то что логин и пароль валиден).")
    @GetMapping("/header")
    public String getHeader() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @ApiOperation(value = "Получение Authentication. username == id.toString !!")
    @GetMapping
    public Authentication getAuthentication(){
        log.info("Get authentication id {}", SecurityContextHolder.getContext().getAuthentication().getName());
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
