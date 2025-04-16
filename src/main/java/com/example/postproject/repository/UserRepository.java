package com.example.postproject.repository;

import com.example.postproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**interface of UserRepository.*/
public interface UserRepository extends JpaRepository<User, Long> {
}
