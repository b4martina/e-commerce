package com.example.e_commerce.repository;

import com.example.e_commerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long Id);
    Optional<User> findByName(String name);

}
