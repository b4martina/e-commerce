package com.example.e_commerce.dto;

import jakarta.validation.constraints.NotBlank;

public class UserRegisterRequest {

    @NotBlank(message= "name can not be blank ")
    private String name;

    @NotBlank(message= "Surname can not be blank ")
    private String surname;

    @NotBlank(message = "username can not be empty")
    private String username;

    @NotBlank(message = "E-Mail cannot be blank")
    private String email;

    @NotBlank(message = "password cannot be empty") // Validation
    private String password;


    public UserRegisterRequest(){}

    public UserRegisterRequest(String name, String surname, String username, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
