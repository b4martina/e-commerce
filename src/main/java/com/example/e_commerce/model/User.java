package com.example.e_commerce.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "USERS")

//, uniqueConstraints = @UniqueConstraint(columnNames= "EMAIL"))

public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    @Column(name= "Id")
    private Long id;

    @Column (name= "NAME")
    private String name;

    @Column(name= "SURNAME")
    private String surname;

    @Column(name="USERNAME")
    private  String username;

    @Column(name = "EMAIL",nullable = false, unique = true)
    private String email;

    @Column (name = "PASSWORD")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
            (name = "USER_ROLES", joinColumns = @JoinColumn(name="USER_ID", referencedColumnName = "ID"),
                    inverseJoinColumns = @JoinColumn(name = " ROLE_ID", referencedColumnName = "ID"))

    private List<Roles> roles = new ArrayList<>();

}
