package com.example.postproject.services;

import com.example.postproject.cache.SimpleCache;
import com.example.postproject.models.Post;
import com.example.postproject.models.User;
import com.example.postproject.repository.PostRepository;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private SimpleCache cache;

    @InjectMocks
    private PostService postService;

    private Post validPost;
    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setId(1L);
        validUser.setUsername("testuser");

        validPost = new Post();
        validPost.setId(1L);
        validPost.setTitle("Test Title");
        validPost.setText("Test Content");
        validPost.setUser(validUser);
    }

    @Test
    void createPost_WithValidData_ShouldReturnPost() {
        when(postRepository.save(any(Post.class))).thenReturn(validPost);

        Post result = postService.createPost(validPost, validUser);

        assertNotNull(result);
        assertEquals(validPost.getId(), result.getId());
        verify(postRepository).save(validPost);
        verify(cache).remove("user_posts_testuser");
    }

    @Test
    void bulkCreatePosts_WithValidData_ShouldReturnPosts() {
        List<Post> posts = Arrays.asList(validPost);
        when(postRepository.save(any(Post.class))).thenReturn(validPost);

        List<Post> result = postService.bulkCreatePosts(posts, validUser);

        assertEquals(1, result.size());
        verify(postRepository).save(validPost);
        verify(cache).remove("user_posts_testuser");
    }

    @Test
    void getPostById_WithValidId_ShouldReturnPost() {
        when(cache.get("post_1")).thenReturn(Optional.empty());
        when(postRepository.findById(1L)).thenReturn(Optional.of(validPost));

        Optional<Post> result = postService.getPostById(1L);

        assertTrue(result.isPresent());
        assertEquals(validPost.getId(), result.get().getId());
        verify(cache).put("post_1", validPost);
    }

    @Test
    void getPostsByUsername_ShouldReturnPostsFromCache() {
        List<Post> cachedPosts = Arrays.asList(validPost);
        when(cache.get("user_posts_testuser")).thenReturn(Optional.of(cachedPosts));

        List<Post> result = postService.getPostsByUsername("testuser");

        assertEquals(1, result.size());
        verify(cache).get("user_posts_testuser");
        verify(postRepository, never()).findPostsByUsername(anyString());
    }

    @Test
    void updatePost_WithValidData_ShouldUpdatePost() {
        Post updatedData = new Post();
        updatedData.setTitle("Updated Title");
        updatedData.setText("Updated Content");

        when(postRepository.findById(1L)).thenReturn(Optional.of(validPost));
        when(postRepository.save(any(Post.class))).thenReturn(validPost);

        Post result = postService.updatePost(1L, updatedData);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Content", result.getText());
        verify(cache).put("post_1", validPost);
        verify(cache).remove("user_posts_testuser");
    }

    @Test
    void deletePost_ShouldInvalidateCache() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(validPost));

        postService.deletePost(1L);

        verify(postRepository).deleteById(1L);
        verify(cache).remove("post_1");
        verify(cache).remove("user_posts_testuser");
    }
}