package com.mohamed.pizzarestaurant.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@Setter
@Getter
public class Reviews implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewsId;
    private int rating;
    private String comment;
    private Date dateOfReview;
    @ManyToOne
    private Product product;

    public Reviews() {

    }


}
