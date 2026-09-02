package com.example.e_commerce.service;

import com.example.e_commerce.dto.JWTResponse;
import com.example.e_commerce.dto.UserLogInRequest;
import com.example.e_commerce.dto.UserRegisterRequest;
import com.example.e_commerce.model.Roles;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.RoleRepository;
import com.example.e_commerce.repository.UserRepository;
import org.springframework.aot.hint.annotation.RegisterReflection;
import org.springframework.context.annotation.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service

public class UserService {
    public UserRepository userRepository;
    public PasswordEncoder passwordEncoder;
    public AuthenticationManager authenticationManager;
    public JWTService jwtService;
    public RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
    }

    //public UserService() {}

    public JWTResponse register (UserRegisterRequest registerRequest){

        User user = new User();

        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));


        Roles roles;
        if (userRepository.count() == 0) {

          roles = roleRepository.findByRoleName("SuperAdmin")
                  .orElseThrow(()-> new IllegalStateException("SUPERADMIN role does not exist"));

        } else {
            roles = roleRepository.findByRoleName("User").orElseThrow(()
                    -> new IllegalStateException("USER role not found"));
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())){
            throw new IllegalStateException("Email is already in use");
        }

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new IllegalArgumentException("Username is already in use.");
        }

        user.setRoles(Collections.singletonList(roles));
       User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser.getUsername());

        return new JWTResponse(token);
    }




    public JWTResponse logInRequest(UserLogInRequest logInRequest) {
       User user;
       user = userRepository.findByUsername(logInRequest.getUsername()).orElseThrow(()
               -> new RuntimeException("username not found"));

       if (!passwordEncoder.matches(logInRequest.getPassword(), user.getPassword() )){
           throw new RuntimeException("The passwoed entered is not correct");
       }

       String token;
       token = jwtService.generateToken(user.getUsername());

       return new JWTResponse(token);


    }


}
