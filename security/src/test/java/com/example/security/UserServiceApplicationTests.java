package com.example.security;

import com.example.security.exception.UserExistsException;
import com.example.security.exception.UserNotFoundException;
import com.example.security.model.*;
import com.example.security.repos.UserRepository;
import com.example.security.service.UserServiceImpl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@Import(UserServiceImpl.class)
public class UserServiceApplicationTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserServiceImpl userService;

    private final String username = "testUsername";
    private final String password = "testPassword";
    private final String newUsername = "testNewUsername";
    private final String email = "test@test.test";
    private final String newEmail = "newTest@newTest.newTest";
    private final UserDtoRequestSave userDtoRequestSave
            = new UserDtoRequestSave(username, password, email);

    private final UserDtoRequestUpdateWithAdmin userDtoRequestUpdateWithAdmin
            = new UserDtoRequestUpdateWithAdmin(newUsername, username, newEmail);

    private final UserDtoRequestUpdateWithUser userDtoRequestUpdateWithUser
            = new UserDtoRequestUpdateWithUser(newUsername, newEmail);

    private final User user = new User(username, password, Role.USER, email);
    private final User newUser = new User(newUsername, password, Role.USER, newEmail);

    @Test
    public void saveUserWithRoleUser() {
        User userUser = new User(username, encoder.encode(password), Role.USER, email);
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.save(userUser)).thenReturn(userUser);

        UserDtoResponse response = userService.saveUserRoleUser(userDtoRequestSave);

        assertEquals(new UserDtoResponse(userUser), response);
        verify(userRepository, times(1)).save(userUser);
    }

    @Test(expected = UserExistsException.class)
    public void failSaveUserWithRoleUser() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDtoResponse response = userService.saveUserRoleUser(userDtoRequestSave);

        assertNotEquals(new UserDtoResponse(user), response);
        verify(userRepository, never()).save(user);
    }

    @Test
    public void saveUserWithRoleAdmin() {
        User admin = new User(username, encoder.encode(password), Role.ADMIN, email);
        when(userRepository.save(admin)).thenReturn(admin);

        UserDtoResponse response = userService.saveUserRoleAdmin(userDtoRequestSave);

        assertEquals(new UserDtoResponse(admin), response);
        verify(userRepository, times(1)).save(admin);
    }

    @Test(expected = UserExistsException.class)
    public void failSaveUserWithRoleAdmin() {
        User admin = new User(username, encoder.encode(password), Role.ADMIN, email);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(admin));

        UserDtoResponse response = userService.saveUserRoleAdmin(userDtoRequestSave);

        assertNotEquals(new UserDtoResponse(admin), response);
        verify(userRepository, never()).save(admin);
    }

    @Test
    public void deleteUser() {
        when(userRepository.findById(10L)).thenReturn(Optional.of(user));

        UserDtoResponse response = userService.deleteUser(10L);

        assertEquals(new UserDtoResponse(user), response);
        verify(userRepository, times(1)).deleteById(10L);
    }

    @Test(expected = UserNotFoundException.class)
    public void failDeleteUser() {
        UserDtoResponse response = userService.deleteUser(10L);

        assertNotEquals(new UserDtoResponse(user), response);
        verify(userRepository, never()).deleteById(10L);
    }

    @Test
    public void getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<UserDtoResponse> userDtoResponses = new ArrayList<>();
        users.add(user);
        users.add(newUser);
        userDtoResponses.add(new UserDtoResponse(user));
        userDtoResponses.add(new UserDtoResponse(newUser));
        when(userRepository.findAll()).thenReturn(users);

        Iterable<UserDtoResponse> response = userService.getAllUsers();

        assertEquals(userDtoResponses, response);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void getUser() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDtoResponse response = userService.getUser(username);

        assertEquals(new UserDtoResponse(user), response);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test(expected = UserNotFoundException.class)
    public void failGetUser() {
        UserDtoResponse response = userService.getUser(username);

        assertNotEquals(new UserDtoResponse(user), response);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void updateUserRoleUser() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("1337");
        when(userRepository.findById(1337L)).thenReturn(Optional.of(user));

        UserDtoResponse response = userService.updateUserRoleUser(userDtoRequestUpdateWithUser);

        assertEquals(new UserDtoResponse(newUser), response);
        verify(userRepository, times(1)).save(newUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void failNotFoundUpdateUserRoleUser() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("1337");

        UserDtoResponse response = userService.updateUserRoleUser(userDtoRequestUpdateWithUser);

        assertNotEquals(new UserDtoResponse(newUser), response);
        verify(userRepository, never()).save(newUser);
    }

    @Test(expected = UserExistsException.class)
    public void failExistUpdateUserRoleUser() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("1337");
        when(userRepository.findById(1337L)).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(userDtoRequestUpdateWithUser.getNewUsername())).thenReturn(Optional.of(newUser));

        UserDtoResponse response = userService.updateUserRoleUser(userDtoRequestUpdateWithUser);

        assertNotEquals(new UserDtoResponse(newUser), response);
        verify(userRepository, never()).save(newUser);
    }

    @Test
    public void updateUserRoleAdmin() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDtoResponse response = userService.updateUserRoleAdmin(userDtoRequestUpdateWithAdmin);

        assertEquals(new UserDtoResponse(newUser), response);
        verify(userRepository, times(1)).save(newUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void failNotFoundUpdateUserRoleAdmin() {
        UserDtoResponse response = userService.updateUserRoleAdmin(userDtoRequestUpdateWithAdmin);

        assertNotEquals(new UserDtoResponse(newUser), response);
        verify(userRepository, never()).save(newUser);
    }

    @Test(expected = UserExistsException.class)
    public void failExistUpdateUserRoleAdmin() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(newUsername)).thenReturn(Optional.of(newUser));

        UserDtoResponse response = userService.updateUserRoleAdmin(userDtoRequestUpdateWithAdmin);

        assertNotEquals(new UserDtoResponse(newUser), response);
        verify(userRepository, never()).save(newUser);
    }

    @Test
    public void deleteUserByUser() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("1337");
        when(userRepository.findById(1337L)).thenReturn(Optional.of(user));

        UserDtoResponse response = userService.deleteUserByUser();

        assertEquals(new UserDtoResponse(user), response);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void putBlockUserByUsername() {
        User userBlocked = new User(username, password, Role.USER, email);
        UserDtoBlocked userDtoBlocked = new UserDtoBlocked(username, false, null);
        userBlocked.setNotBlocked(false);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDtoResponse response = userService.putBlockUser(userDtoBlocked);

        assertEquals(new UserDtoResponse(userBlocked), response);
        verify(userRepository, times(1)).save(userBlocked);
    }

    @Test(expected = UserNotFoundException.class)
    public void failPutBlockUserByUsername() {
        User userBlocked = new User(username, password, Role.USER, email);
        UserDtoBlocked userDtoBlocked = new UserDtoBlocked(username, false, null);
        userBlocked.setNotBlocked(false);
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        UserDtoResponse response = userService.putBlockUser(userDtoBlocked);

        assertNotEquals(new UserDtoResponse(userBlocked), response);
        verify(userRepository, never()).save(userBlocked);
    }

    @Test
    public void putBlocUserById() {
        User userBlocked = new User(username, password, Role.USER, email);
        UserDtoBlocked userDtoBlocked = new UserDtoBlocked("", false, 1L);
        userBlocked.setNotBlocked(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDtoResponse response = userService.putBlockUser(userDtoBlocked);

        assertEquals(new UserDtoResponse(userBlocked), response);
        verify(userRepository, times(1)).save(userBlocked);
    }

    @Test(expected = UserNotFoundException.class)
    public void failPutBlocUserById() {
        User userBlocked = new User(username, password, Role.USER, email);
        UserDtoBlocked userDtoBlocked = new UserDtoBlocked("", false, 1L);
        userBlocked.setNotBlocked(false);

        UserDtoResponse response = userService.putBlockUser(userDtoBlocked);

        assertNotEquals(new UserDtoResponse(userBlocked), response);
        verify(userRepository, never()).save(userBlocked);
    }
}
