package com.example.postproject.controllers;

import com.example.postproject.models.Post;
import com.example.postproject.models.User;
import com.example.postproject.services.PostService;
import com.example.postproject.services.StatusService;
import com.example.postproject.services.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SuppressWarnings("checkstyle:MissingJavadocType")
@RestController
@RequestMapping("/posts")
public class PostController {
  private final PostService postService;
  private final UserService userService;
  private final StatusService statusService;

    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod"})
    public PostController(PostService postService, UserService userService,
                          StatusService statusService) {
        this.postService = postService;
        this.userService = userService;
        this.statusService = statusService;
    }

    @SuppressWarnings({"checkstyle:Indentation",
            "checkstyle:MissingJavadocMethod", "checkstyle:OperatorWrap"})
    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody Post post, @RequestParam Long userId) {
        if (!statusService.isServerAvailable()) {
            return ResponseEntity.status(503).body("Сервис временно недоступен. " +
                    "Пожалуйста, попробуйте позже.");
        }
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return ResponseEntity.ok(postService.createPost(post, user));
    }

    @SuppressWarnings({"checkstyle:Indentation",
            "checkstyle:MissingJavadocMethod", "checkstyle:OperatorWrap"})
    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        if (!statusService.isServerAvailable()) {
            return ResponseEntity.status(503).body("Сервис временно недоступен." +
                    " Пожалуйста, попробуйте позже.");
        }
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod",
            "checkstyle:OperatorWrap"})
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        if (!statusService.isServerAvailable()) {
            return ResponseEntity.status(503).body("Сервис временно недоступен. " +
                    "Пожалуйста, попробуйте позже.");
        }
        Optional<Post> post = postService.getPostById(id);
        return post.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @SuppressWarnings({"checkstyle:Indentation",
            "checkstyle:MissingJavadocMethod", "checkstyle:OperatorWrap"})
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        if (!statusService.isServerAvailable()) {
            return ResponseEntity.status(503).body("Сервис временно недоступен." +
                    " Пожалуйста, попробуйте позже.");
        }
        return ResponseEntity.ok(postService.updatePost(id, postDetails));
    }

    @SuppressWarnings({"checkstyle:Indentation",
            "checkstyle:MissingJavadocMethod", "checkstyle:OperatorWrap"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        if (!statusService.isServerAvailable()) {
            return ResponseEntity.status(503).body("Сервис временно недоступен." +
                    " Пожалуйста, попробуйте позже.");
        }
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod"})
    @GetMapping("/by-user/{username}")
    public ResponseEntity<?> getPostsByUser(@PathVariable String username) {
        if (!statusService.isServerAvailable()) {
            return ResponseEntity.status(503).body("Сервис временно недоступен");
        }
        List<Post> posts = postService.getPostsByUsername(username);
        return ResponseEntity.ok(posts);
    }



}