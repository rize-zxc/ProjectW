package com.example.postproject.services;

import com.example.postproject.cache.SimpleCache;
import com.example.postproject.exceptions.BadRequestException;
import com.example.postproject.exceptions.InternalServerErrorException;
import com.example.postproject.models.User;
import com.example.postproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SimpleCache cache;

    @InjectMocks
    private UserService userService;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setId(1L);
        validUser.setUsername("testuser");
        validUser.setEmail("test@example.com");
        validUser.setPassword("password");
    }

    @Test
    void createUser_WithValidData_ShouldReturnUser() {
        when(userRepository.save(any(User.class))).thenReturn(validUser);

        User result = userService.createUser(validUser);

        assertNotNull(result);
        assertEquals(validUser.getId(), result.getId());
        verify(userRepository).save(validUser);
        verify(cache).remove("all_users");
    }

    @Test
    void createUser_WithNullUser_ShouldThrowBadRequestException() {
        assertThrows(BadRequestException.class, () -> userService.createUser(null));
    }

    @Test
    void createUser_WithEmptyEmail_ShouldThrowBadRequestException() {
        validUser.setEmail("");
        assertThrows(BadRequestException.class, () -> userService.createUser(validUser));
    }

    @Test
    void getAllUsers_ShouldReturnUsersFromCache() {
        List<User> cachedUsers = Arrays.asList(validUser);
        when(cache.get("all_users")).thenReturn(Optional.of(cachedUsers));

        List<User> result = userService.getAllUsers();

        assertEquals(1, result.size());
        verify(cache).get("all_users");
        verify(userRepository, never()).findAll();
    }

    @Test
    void getAllUsers_ShouldReturnUsersFromDatabase() {
        List<User> users = Arrays.asList(validUser);
        when(cache.get("all_users")).thenReturn(Optional.empty());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(1, result.size());
        verify(userRepository).findAll();
        verify(cache).put("all_users", users);
    }

    @Test
    void getUserById_WithValidId_ShouldReturnUser() {
        when(cache.get("user_1")).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.of(validUser));

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(validUser.getId(), result.get().getId());
        verify(cache).put("user_1", validUser);
    }

    @Test
    void getUserById_WithInvalidId_ShouldThrowBadRequestException() {
        assertThrows(BadRequestException.class, () -> userService.getUserById(-1L));
    }

    @Test
    void updateUser_WithValidData_ShouldUpdateUser() {
        User updatedData = new User();
        updatedData.setEmail("new@example.com");
        updatedData.setUsername("newuser");

        when(userRepository.findById(1L)).thenReturn(Optional.of(validUser));
        when(userRepository.save(any(User.class))).thenReturn(validUser);

        User result = userService.updateUser(1L, updatedData);

        assertEquals("new@example.com", result.getEmail());
        assertEquals("newuser", result.getUsername());
        verify(cache).put("user_1", validUser);
        verify(cache).remove("all_users");
    }

    @Test
    void deleteUser_WithValidId_ShouldDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
        verify(cache).remove("user_1");
        verify(cache).remove("all_users");
    }
}