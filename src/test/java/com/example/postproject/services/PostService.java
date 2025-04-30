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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private SimpleCache cache;

    @Mock
    private RequestCounter requestCounter;

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

        when(requestCounter.increment()).thenReturn(1);
    }

    @Test
    void createPost() {
        when(postRepository.save(any(Post.class))).thenReturn(validPost);
        Post result = postService.createPost(validPost, validUser);
        assertNotNull(result);
        verify(cache).remove("user_posts_testuser");
    }

    @Test
    void bulkCreatePosts() {
        List<Post> posts = List.of(validPost);
        when(postRepository.save(any(Post.class))).thenReturn(validPost);
        List<Post> result = postService.bulkCreatePosts(posts, validUser);
        assertEquals(1, result.size());
    }

    @Test
    void getPostById() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(validPost));
        Optional<Post> result = postService.getPostById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void getPostsByUsername() {
        when(cache.get("user_posts_testuser")).thenReturn(Optional.of(List.of(validPost)));
        List<Post> result = postService.getPostsByUsername("testuser");
        assertEquals(1, result.size());
    }

    @Test
    void updatePost() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(validPost));
        Post updatedPost = postService.updatePost(1L, validPost);
        assertEquals("Test Title", updatedPost.getTitle());
    }

    @Test
    void deletePost() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(validPost));
        postService.deletePost(1L);
        verify(postRepository).deleteById(1L);
    }

    @Test
    void getAllPosts() {
        when(postRepository.findAll()).thenReturn(List.of(validPost));
        List<Post> result = postService.getAllPosts();
        assertEquals(1, result.size());
    }
}
