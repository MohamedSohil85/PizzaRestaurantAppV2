package com.mohamed.pizzarestaurant.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
@Entity
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String lastName;
    private String firstName;
    private String address;
    private String phoneNumber;
    private String token;
    @OneToMany
    private List<Product> productList;

    public Customer() {
    }
}
