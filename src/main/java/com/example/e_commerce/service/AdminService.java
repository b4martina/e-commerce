package com.example.e_commerce.service;

import com.example.e_commerce.dto.UserRegisterRequest;
import com.example.e_commerce.model.Roles;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.RoleRepository;
import com.example.e_commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Collections;

@Service

public class AdminService {

    @Autowired
    public UserRepository userRepository;
    @Autowired
    public RoleRepository roleRepository;
    @Autowired
    public PasswordEncoder passwordEncoder;
    //method to create admins


    public AdminService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createAdmin (UserRegisterRequest urr) throws AccessDeniedException {
        User newAdmin = new User();
        newAdmin.setUsername(urr.getUsername());
        newAdmin.setName(urr.getName());
        newAdmin.setPassword(passwordEncoder.encode(urr.getPassword()));
        newAdmin.setEmail(urr.getEmail());

        Roles roles;
        roles= roleRepository.findByRoleName("Admin").orElseThrow(()->
        new RuntimeException("Admin role does not exist"));

        newAdmin.setRoles(Collections.singletonList(roles));

        return userRepository.save(newAdmin);
    }


}
