package com.mohamed.pizzarestaurant.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    @ManyToOne
    private Customer customer;
    @Temporal(value = TemporalType.DATE)
    private Date OrderDate;
    @Temporal(value = TemporalType.DATE)
    private Date deliveryDate;
    @JsonIgnore
    @OneToMany
    private List<Product> product;
    @JsonIgnore
    @ManyToOne
    private ShoppingCart shoppingCart;
    private int quantity;
    private double total;

    public Orders() {
    }
}
