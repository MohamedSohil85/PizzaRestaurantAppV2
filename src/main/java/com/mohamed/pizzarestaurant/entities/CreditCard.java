package com.mohamed.pizzarestaurant.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Setter
@Getter
@ToString
@Entity
@DiscriminatorValue( value="credit_Card" )
public class CreditCard extends Payment {
private String accountNumber;
private String cardPassNumber;
private String cardholderName;
private String bankName;
private String cardType;

    public CreditCard() {
    }
}
