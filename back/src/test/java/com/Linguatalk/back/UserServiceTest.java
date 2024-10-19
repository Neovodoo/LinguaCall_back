package com.Linguatalk.back;

import com.Linguatalk.back.model.User;
import com.Linguatalk.back.repository.UserRepository;
import com.Linguatalk.back.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest  {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUserWithValidData() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("validPassword");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user.getLogin(), user.getPassword());

        assertNotNull(createdUser);
        assertEquals("validUser", createdUser.getLogin());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUserWithEmptyLogin() {
        assertThrows(IllegalArgumentException.class, () -> userService.createUser("", "password"));
    }

    @Test
    void testCreateUserWithEmptyPassword() {
        assertThrows(IllegalArgumentException.class, () -> userService.createUser("validUser", ""));
    }

    @Test
    void testFindUserWithExistingCredentials() {
        User user = new User();
        user.setLogin("existingUser");
        user.setPassword("password123");

        when(userRepository.findByLogin("existingUser")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findUserByLogin("existingUser");

        assertTrue(foundUser.isPresent());
        assertEquals("existingUser", foundUser.get().getLogin());
        verify(userRepository, times(1)).findByLogin("existingUser");
    }

    @Test
    void testFindUserWithoutExistingCredentials() {
        when(userRepository.findByLogin("nonexistentUser")).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.findUserByLogin("nonexistentUser");

        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findByLogin("nonexistentUser");
    }

    @Test
    void testCreateUserAndLoginWithValidData() {
        // Creating a new user
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("validPassword");

        // Mocking the repository behavior
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findByLogin("validUser")).thenReturn(Optional.of(user));

        // Test creating a user
        User createdUser = userService.createUser("validUser", "validPassword");
        assertNotNull(createdUser);
        assertEquals("validUser", createdUser.getLogin());

        // Test logging in with valid credentials
        boolean isAuthenticated = userService.authenticateUser("validUser", "validPassword");
        assertTrue(isAuthenticated);

        // Verifying repository interactions
        verify(userRepository, times(1)).save(any(User.class));
        verify(userRepository, times(1)).findByLogin("validUser");
    }

    @Test
    void testCreateUserAndLoginWithInvalidPassword() {
        // Creating a new user
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("validPassword");

        // Mocking the repository behavior
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findByLogin("validUser")).thenReturn(Optional.of(user));

        // Test creating a user
        User createdUser = userService.createUser("validUser", "validPassword");
        assertNotNull(createdUser);
        assertEquals("validUser", createdUser.getLogin());

        // Test logging in with an invalid password
        boolean isAuthenticated = userService.authenticateUser("validUser", "invalidPassword");
        assertFalse(isAuthenticated);

        // Verifying repository interactions
        verify(userRepository, times(1)).save(any(User.class));
        verify(userRepository, times(1)).findByLogin("validUser");
    }

    @Test
    void testLoginWithNonexistentUser() {
        // Mocking the repository behavior for a nonexistent user
        when(userRepository.findByLogin("nonexistentUser")).thenReturn(Optional.empty());

        // Test logging in with a nonexistent user
        boolean isAuthenticated = userService.authenticateUser("nonexistentUser", "password");
        assertFalse(isAuthenticated);

        // Verifying repository interactions
        verify(userRepository, times(1)).findByLogin("nonexistentUser");
    }

}
