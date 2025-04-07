package com.example.postproject.controllers;

import com.example.postproject.models.User;
import com.example.postproject.services.StatusService;
import com.example.postproject.services.UserService;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@SuppressWarnings("checkstyle:MissingJavadocType")
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;
  private final StatusService statusService;

  @SuppressWarnings({"checkstyle:MissingJavadocMethod", "checkstyle:Indentation"})
  public UserController(UserService userService, StatusService statusService) {
        this.userService = userService;
        this.statusService = statusService;
    }

  @SuppressWarnings({"checkstyle:MissingJavadocMethod", "checkstyle:Indentation"})
  @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (!statusService.isServerAvailable()) {
            return ResponseEntity.status(503).body("Сервис временно недоступен. Пожалуйста, попробуйте позже.");
        }
        return ResponseEntity.ok(userService.createUser(user));
    }

  @SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod"})
  @GetMapping
    public ResponseEntity<?> getAllUsers() {
        if (!statusService.isServerAvailable()) {
            return ResponseEntity.status(503).body("Сервис временно недоступен. Пожалуйста, попробуйте позже.");
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

  @SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod"})
  @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        if (!statusService.isServerAvailable()) {
            return ResponseEntity.status(503).body("Сервис временно недоступен. Пожалуйста, попробуйте позже.");
        }
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

  @SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod"})
  @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        if (!statusService.isServerAvailable()) {
            return ResponseEntity.status(503).body("Сервис временно недоступен. Пожалуйста, попробуйте позже.");
        }
        return ResponseEntity.ok(userService.updateUser(id, userDetails));
    }

  @SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod"})
  @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!statusService.isServerAvailable()) {
            return ResponseEntity.status(503).body("Сервис временно недоступен. Пожалуйста, попробуйте позже.");
        }
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}