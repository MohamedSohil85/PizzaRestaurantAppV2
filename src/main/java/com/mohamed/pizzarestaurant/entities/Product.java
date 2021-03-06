package com.mohamed.pizzarestaurant.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productID;
    private String Name;
    private String ingredients;
    private float price;
    @OneToMany
    List<Reviews> reviewsList;
    @ManyToOne
    private Orders order;
    @ManyToOne
    private Category category;

}
