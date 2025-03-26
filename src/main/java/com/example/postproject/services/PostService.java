package com.example.postproject.services;

import com.example.postproject.cache.SimpleCache;
import com.example.postproject.models.Post;
import com.example.postproject.models.User;
import com.example.postproject.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final SimpleCache cache;

    public PostService(PostRepository postRepository, SimpleCache cache) {
        this.postRepository = postRepository;
        this.cache = cache;
    }

    private String getPostCacheKey(Long id) {
        return "post_" + id;
    }

    private String getUserPostsCacheKey(String username) {
        return "user_posts_" + username;
    }

    public Post createPost(Post post, User user) {
        post.setUser(user);
        Post createdPost = postRepository.save(post);
        cache.remove(getUserPostsCacheKey(user.getUsername()));
        return createdPost;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        String cacheKey = getPostCacheKey(id);
        Optional<Object> cachedPost = cache.get(cacheKey);
        if (cachedPost.isPresent()) {
            return Optional.of((Post) cachedPost.get());
        }

        Optional<Post> post = postRepository.findById(id);
        post.ifPresent(p -> cache.put(cacheKey, p));
        return post;
    }

    public Post updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        post.setTitle(postDetails.getTitle());
        post.setText(postDetails.getText());
        post.setUser(postDetails.getUser());

        Post updatedPost = postRepository.save(post);
        cache.put(getPostCacheKey(id), updatedPost);
        if (post.getUser() != null) {
            cache.remove(getUserPostsCacheKey(post.getUser().getUsername()));
        }
        return updatedPost;
    }

    public void deletePost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        postRepository.deleteById(id);
        cache.remove(getPostCacheKey(id));
        post.ifPresent(p -> {
            if (p.getUser() != null) {
                cache.remove(getUserPostsCacheKey(p.getUser().getUsername()));
            }
        });
    }

    public List<Post> getPostsByUsername(String username) {
        String cacheKey = getUserPostsCacheKey(username);
        Optional<Object> cachedPosts = cache.get(cacheKey);
        if (cachedPosts.isPresent()) {
            return (List<Post>) cachedPosts.get();
        }

        List<Post> posts = postRepository.findPostsByUsername(username);
        cache.put(cacheKey, posts);
        return posts;
    }
}