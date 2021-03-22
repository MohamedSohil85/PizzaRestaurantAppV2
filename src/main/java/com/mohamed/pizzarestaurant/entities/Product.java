package com.mohamed.pizzarestaurant.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ProductID;
    private String productName;
    private String ingredients;
    private float price;
    private int rating;
    @ManyToOne
    private Orders order;

    public Product() {
    }
}
