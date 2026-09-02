package com.example.e_commerce.repository;

import com.example.e_commerce.model.Roles;
import com.example.e_commerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> findByRoleName(String roleName);

}
