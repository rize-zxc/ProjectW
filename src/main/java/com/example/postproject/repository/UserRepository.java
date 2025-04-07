package com.example.postproject.repository;

import com.example.postproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("checkstyle:MissingJavadocType")
public interface UserRepository extends JpaRepository<User,Long> {
}
