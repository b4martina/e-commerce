package com.example.e_commerce.controller;

import com.example.e_commerce.dto.UserRegisterRequest;
import com.example.e_commerce.model.User;
import com.example.e_commerce.service.AdminService;
import com.example.e_commerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final AdminService adminService ;

    public AdminController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @PreAuthorize("hasRole('SuperAdmin')")
    @PostMapping("/super/create-admin")
    public ResponseEntity<?> createeAdmin (@Valid @RequestBody UserRegisterRequest userRegisterRequest){
        try {
            User admin = adminService.createAdmin(userRegisterRequest);
            return ResponseEntity.ok("Admin created");}
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest()
                    .body(e.getMessage());}

    }


}
