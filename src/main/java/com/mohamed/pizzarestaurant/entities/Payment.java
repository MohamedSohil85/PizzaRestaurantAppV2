package com.mohamed.pizzarestaurant.entities;

import com.sun.source.doctree.SerialDataTree;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;
    @OneToOne
    private Customer customer;
    @OneToOne
    private ShoppingCart shoppingCart;

    public Payment() {
    }
}
