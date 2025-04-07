package com.example.postproject.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;



@SuppressWarnings("checkstyle:MissingJavadocType")
@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
    private User user;

  @Column(nullable = false)
    private String title;

  @Column(nullable = false)
    private String text;

  @Column(nullable = false)
    private LocalDateTime publishingDate = LocalDateTime.now();


}