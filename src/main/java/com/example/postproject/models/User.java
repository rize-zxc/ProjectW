package com.example.postproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@SuppressWarnings("checkstyle:MissingJavadocType")
@Entity
@Table(name ="users")
@Getter
@Setter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  @Column(nullable = false, unique = true)
    private String email;

  @Column(nullable = false)
    private String password;

  @Column(nullable = false)
    private String username;


  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore // Игнорирует поле при сериализации
private List<Post> posts;
}