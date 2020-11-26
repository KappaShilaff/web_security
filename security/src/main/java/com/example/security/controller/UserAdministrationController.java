package com.example.security.controller;

import com.example.security.model.*;
import com.example.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/users")
@RestController
@Api(tags = {"Администрирование пользоватей"})
public class UserAdministrationController {

    private final UserService userService;

    @ApiOperation(value = "Добавление пользователя с ролью пользователя.")
    @PostMapping
    public UserDtoResponse saveUserRoleUser(@Validated @RequestBody UserDtoRequestSave userDtoRequestSave) {
        return userService.saveUserRoleUser(userDtoRequestSave);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Добавление пользователя с ролью админа.")
    @PostMapping("/adm")
    public UserDtoResponse saveUserRoleAdmin(@Validated @RequestBody UserDtoRequestSave userDtoRequestSave) {
        return userService.saveUserRoleAdmin(userDtoRequestSave);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Удаление пользователя по id. (только для админов)")
    @DeleteMapping("/{id}")
    public UserDtoResponse deleteUser(@PathVariable Long id) {

        return userService.deleteUser(id);
    }

    @ApiOperation(value = "Возвращает пользователя.")
    @GetMapping("/{username}")
    public UserDtoResponse getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Возвращает всех юзеров.")
    @GetMapping
    public Iterable<UserDtoResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ApiOperation(value = "Смена своего username и/или емейл.")
    @PutMapping
    public UserDtoResponse updateUserRoleUser(@Validated @RequestBody UserDtoRequestUpdateWithUser userDtoRequestUpdateWithUser) {
        return userService.updateUserRoleUser(userDtoRequestUpdateWithUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Смена username и/или емейл любого юзера. (только для админов)")
    @PutMapping("/adm")
    public UserDtoResponse updateUserRoleAdmin(@Validated @RequestBody UserDtoRequestUpdateWithAdmin userDtoRequestUpdateWithAdmin) {
        return userService.updateUserRoleAdmin(userDtoRequestUpdateWithAdmin);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ApiOperation(value = "Юзер удаляет сам себя.")
    @DeleteMapping
    public UserDtoResponse deleteUserByUser() {
        return userService.deleteUserByUser();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Блокировка аккаунта. если id == null, то блокировка идёт по username.")
    @PutMapping("/block")
    public UserDtoResponse putBlockUser(@Validated @RequestBody UserDtoBlocked userDtoBlocked) {
        return userService.putBlockUser(userDtoBlocked);
    }
}
