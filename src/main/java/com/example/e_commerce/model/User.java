package com.example.e_commerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

   //@Column(name= "SURNAME")
    //private String surname;

    @Column(name="USERNAME")
    private  String username;

    @Column(name = "EMAIL",nullable = false, unique = true)
    private String email;

    @Column (name = "PASSWORD")
    private String password;


    @OneToMany(mappedBy = "productOwner")
    @JsonIgnore
    private List<Product> product = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
            (name = "USER_ROLES", joinColumns = @JoinColumn(name="USER_ID", referencedColumnName = "ID"),
                    inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"))

    private List<Roles> roles = new ArrayList<>();


   /* @OneToMany(mappedBy = "productPurchaser")
    @JsonIgnore
    private Product orderedProduct= new Product();

    */

    @OneToMany(mappedBy =  "buyer")
    @JsonIgnore
    private  List<Orders> ordersList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }
}
