package com.example.e_commerce.controller;

import com.example.e_commerce.dto.JWTResponse;
import com.example.e_commerce.dto.UserLogInRequest;
import com.example.e_commerce.dto.UserRegisterRequest;
import com.example.e_commerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequest request){
        JWTResponse response = userService.register(request);

        return ResponseEntity .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping ("/log-in")
    public ResponseEntity<?> logIn (@Valid @RequestBody UserLogInRequest userLogInRequest){
        try {
            JWTResponse jwtResponse;
            jwtResponse= userService.logInRequest(userLogInRequest);
            return ResponseEntity.ok(jwtResponse);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
      //  JWTResponse response = userService.logInRequest(userLogInRequest);
        //return ResponseEntity.ok(response);
    }



}
