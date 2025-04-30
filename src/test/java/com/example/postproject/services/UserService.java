package com.example.postproject.services;

import com.example.postproject.cache.SimpleCache;
import com.example.postproject.models.User;
import com.example.postproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void createUser() {
        when(userRepository.save(any(User.class))).thenReturn(validUser);
        User result = userService.createUser(validUser);
        assertNotNull(result);
        verify(cache).remove("all_users");
    }

    @Test
    void getAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(validUser));
        List<User> result = userService.getAllUsers();
        assertEquals(1, result.size());
    }

    @Test
    void getUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(validUser));
        Optional<User> result = userService.getUserById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void updateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(validUser));
        User updatedUser = userService.updateUser(1L, validUser);
        assertEquals("test@example.com", updatedUser.getEmail());
    }

    @Test
    void deleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        userService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }
}
