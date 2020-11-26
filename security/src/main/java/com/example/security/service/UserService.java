package com.example.security.service;

import com.example.security.model.*;
import org.springframework.http.HttpHeaders;

public interface UserService {

    public UserDtoResponse saveUserRoleUser(UserDtoRequestSave userDtoRequestSave);

    public UserDtoResponse saveUserRoleAdmin(UserDtoRequestSave userDtoRequestSave);

    public UserDtoResponse deleteUser(Long id);

    public Iterable<UserDtoResponse> getAllUsers();

    public UserDtoResponse updateUserRoleUser(UserDtoRequestUpdateWithUser userDtoRequestUpdateWithUser);

    public UserDtoResponse updateUserRoleAdmin(UserDtoRequestUpdateWithAdmin userDtoRequestUpdateWithAdmin);

    public UserDtoResponse getUser(String username);

    public HttpHeaders getAuth(UserDtoRequestSave userDtoRequestSave);

    public UserDtoResponse deleteUserByUser();

    public UserDtoResponse putBlockUser(UserDtoBlocked userDtoBlocked);

}
