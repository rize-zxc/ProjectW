package com.example.postproject.repository;

import com.example.postproject.models.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@SuppressWarnings("checkstyle:MissingJavadocType")
public interface PostRepository extends JpaRepository<Post, Long> {


  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @Query("SELECT p FROM Post p JOIN FETCH p.user u WHERE u.username = :username")
    List<Post> findPostsByUsername(@Param("username") String username);

}