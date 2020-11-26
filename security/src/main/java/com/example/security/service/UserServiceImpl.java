package com.example.security.service;

import com.example.security.exception.UserExistsException;
import com.example.security.exception.UserNotFoundException;
import com.example.security.model.*;
import com.example.security.repos.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@Transactional
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    @Override
    public UserDtoResponse saveUserRoleUser(UserDtoRequestSave userDtoRequestSave) throws UserExistsException {
        log.debug("try to save user {} {}", userDtoRequestSave.getUsername(), userDtoRequestSave.getEmail());

        User userFromBd = userRepository.findByUsername(userDtoRequestSave.getUsername()).orElse(null);
        if (userFromBd != null) {
            throw new UserExistsException(userDtoRequestSave.getUsername());
        }
        return saveUserRole(userDtoRequestSave, Role.USER);
    }

    @Override
    public UserDtoResponse saveUserRoleAdmin(UserDtoRequestSave userDtoRequestSave) throws UserNotFoundException {
        log.debug("admin with id: {} try to save admin {} {}", SecurityContextHolder.getContext().getAuthentication().getName(),
                userDtoRequestSave.getUsername(), userDtoRequestSave.getEmail());

        User userFromBd = userRepository.findByUsername(userDtoRequestSave.getUsername()).orElse(null);
        if (userFromBd != null)
            throw new UserExistsException(userDtoRequestSave.getUsername());
        return saveUserRole(userDtoRequestSave, Role.ADMIN);
    }

    private UserDtoResponse saveUserRole(UserDtoRequestSave userDtoRequestSave, Role role) throws AccessDeniedException {
        User user = new User(userDtoRequestSave.getUsername(), encoder.encode(userDtoRequestSave.getPassword()), role, userDtoRequestSave.getEmail());
        userRepository.save(user);

        log.info("Save user: {} {} {} {}", user.getId(), user.getUsername(), user.getEmail(), user.getRole());
        return new UserDtoResponse(user);
    }

    @Override
    public UserDtoResponse deleteUser(Long id) throws UserNotFoundException {
        log.debug("admin with id: {} try to delete user with id: {}",
                SecurityContextHolder.getContext().getAuthentication().getName(), id);

        User userFromBd = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);

        log.info("Admin id: {} delete user by id: {}", SecurityContextHolder.getContext().getAuthentication().getName(), id);
        return new UserDtoResponse(userFromBd);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Iterable<UserDtoResponse> getAllUsers() {
        log.debug("admin with id {} try to get all users", SecurityContextHolder.getContext().getAuthentication().getName());

        List<UserDtoResponse> usersDtoResp = new LinkedList<>();
        for (User user : userRepository.findAll()) {
            usersDtoResp.add(new UserDtoResponse(user));
        }

        log.info("Admin id: {} get all users", SecurityContextHolder.getContext().getAuthentication().getName());
        return usersDtoResp;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public HttpHeaders getAuth(UserDtoRequestSave userDtoRequestSave) {
        User userFromBd = userRepository.findByUsername(userDtoRequestSave.getUsername())
                .orElseThrow(() -> new UserNotFoundException(userDtoRequestSave.getUsername()));
        if (encoder.matches(userDtoRequestSave.getPassword(), userFromBd.getPassword())) {
            String userCredentials = userDtoRequestSave.getUsername() + ":" + userDtoRequestSave.getPassword();
            String encodedCredentials =
                    new String(Base64.encodeBase64(userCredentials.getBytes()));
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Basic " + encodedCredentials);
            return httpHeaders;
        } else {
            throw new AccessDeniedException("");
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserDtoResponse getUser(String username) {
        log.debug("try to get user with username {}", username);

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        log.info("Get user by username: {}", username);
        return new UserDtoResponse(user);
    }


    @Override
    public UserDtoResponse updateUserRoleUser(UserDtoRequestUpdateWithUser userDtoRequestUpdateWithUser) {
        log.debug("try to update user {}", userDtoRequestUpdateWithUser);

        Long id = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());

        User userFromBd = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return updateUser(new UserDtoRequestUpdateWithAdmin(userDtoRequestUpdateWithUser), userFromBd);
    }

    @Override
    public UserDtoResponse updateUserRoleAdmin(UserDtoRequestUpdateWithAdmin userDtoRequestUpdateWithAdmin) {
        log.debug("admin with id: {} try to update user {}", SecurityContextHolder.getContext().getAuthentication().getName(),
                userDtoRequestUpdateWithAdmin);

        User userFromBd = userRepository.findByUsername(userDtoRequestUpdateWithAdmin.getOldUsername())
                .orElseThrow(() -> new UserNotFoundException(userDtoRequestUpdateWithAdmin.getOldUsername()));
        return updateUser(userDtoRequestUpdateWithAdmin, userFromBd);
    }

    private UserDtoResponse updateUser(UserDtoRequestUpdateWithAdmin userDtoRequestUpdateWithAdmin, User userFromBd) {
        if (!userDtoRequestUpdateWithAdmin.getNewUsername().equals("")) {
            User findUserFromBdByNewUsername = userRepository.findByUsername(userDtoRequestUpdateWithAdmin.getNewUsername()).orElse(null);
            if (findUserFromBdByNewUsername != null) {
                throw new UserExistsException(userDtoRequestUpdateWithAdmin.getNewUsername());
            }
            userFromBd.setUsername(userDtoRequestUpdateWithAdmin.getNewUsername());
        }
        if (!userDtoRequestUpdateWithAdmin.getEmail().equals("")) {
            userFromBd.setEmail(userDtoRequestUpdateWithAdmin.getEmail());
        }
        userRepository.save(userFromBd);

        log.info("Update user: {} {} {} {}", userFromBd.getId(), userFromBd.getUsername(), userFromBd.getEmail(), userFromBd.getRole());
        return new UserDtoResponse(userFromBd);
    }

    public UserDtoResponse deleteUserByUser() {
        log.debug("try to delete user himself with id {}", SecurityContextHolder.getContext().getAuthentication().getName());

        Long id = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());

        User userFromBd = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(userFromBd);

        log.info("User delete himself id: {}", id);
        return new UserDtoResponse(userFromBd);
    }

    public UserDtoResponse putBlockUser(UserDtoBlocked userDtoBlocked) {
        log.debug("admin with id: {} try to set isNotBlack user {}", SecurityContextHolder.getContext().getAuthentication().getName(),
                userDtoBlocked);

        User userFromBd;

        if (userDtoBlocked.getId() != null) {
            userFromBd = userRepository.findById(userDtoBlocked.getId()).orElseThrow(
                    () -> new UserNotFoundException(userDtoBlocked.getId()));
        } else {
            userFromBd = userRepository.findByUsername(userDtoBlocked.getUsername()).orElseThrow(
                    () -> new UserNotFoundException(userDtoBlocked.getUsername()));
        }

        userFromBd.setNotBlocked(userDtoBlocked.isNotBlocked());
        userRepository.save(userFromBd);

        log.info("Admin id: {} set isNotBlocked: {} for user with id {}",
                SecurityContextHolder.getContext().getAuthentication().getName(), userFromBd.isNotBlocked(), userFromBd.getId());
        return new UserDtoResponse(userFromBd);
    }
}
