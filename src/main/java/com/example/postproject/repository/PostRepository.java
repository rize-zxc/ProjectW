package com.example.postproject.repository;

import com.example.postproject.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {


    @Query("SELECT p FROM Post p JOIN FETCH p.user u WHERE u.username = :username")
    List<Post> findPostsByUsername(@Param("username") String username);

}