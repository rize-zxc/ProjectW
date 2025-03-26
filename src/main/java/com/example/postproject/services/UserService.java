package com.example.postproject.services;

import com.example.postproject.cache.SimpleCache;
import com.example.postproject.models.User;
import com.example.postproject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SimpleCache cache;

    public UserService(UserRepository userRepository, SimpleCache cache) {
        this.userRepository = userRepository;
        this.cache = cache;
    }

    private String getUserCacheKey(Long id) {
        return "user_" + id;
    }

    private String getAllUsersCacheKey() {
        return "all_users";
    }

    public User createUser(User user) {
        User createdUser = userRepository.save(user);

        cache.remove(getAllUsersCacheKey());
        return createdUser;
    }

    public List<User> getAllUsers() {
        String cacheKey = getAllUsersCacheKey();
        Optional<Object> cachedUsers = cache.get(cacheKey);
        if (cachedUsers.isPresent()) {
            return (List<User>) cachedUsers.get();
        }

        List<User> users = userRepository.findAll();
        cache.put(cacheKey, users);
        return users;
    }

    public Optional<User> getUserById(Long id) {
        String cacheKey = getUserCacheKey(id);
        Optional<Object> cachedUser = cache.get(cacheKey);
        if (cachedUser.isPresent()) {
            return Optional.of((User) cachedUser.get());
        }

        Optional<User> user = userRepository.findById(id);
        user.ifPresent(u -> cache.put(cacheKey, u));
        return user;
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setUsername(userDetails.getUsername());

        User updatedUser = userRepository.save(user);
        cache.put(getUserCacheKey(id), updatedUser);
        cache.remove(getAllUsersCacheKey());
        return updatedUser;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        cache.remove(getUserCacheKey(id));
        cache.remove(getAllUsersCacheKey());
    }
}