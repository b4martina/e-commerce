/*package com.example.e_commerce.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ADMINS")

//, uniqueConstraints = @UniqueConstraint(columnNames= "EMAIL"))

public class Admin {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    @Column(name= "Id")
    private Long id;

    @Column (name= "NAME")
    private String name;

    @Column(name= "SURNAME")
    private String surname;

    @Column(name = "EMAIL",nullable = false, unique = true)
    private String email;

    @Column (name = "PASSWORD")
    private String password;

    @Column (name = "UNIQUE_ADMIN_ID")
    private String uniqueId;
}*/
