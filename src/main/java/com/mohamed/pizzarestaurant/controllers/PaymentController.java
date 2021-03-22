package com.mohamed.pizzarestaurant.controllers;

import com.mohamed.pizzarestaurant.repositories.CustomerRepository;
import com.mohamed.pizzarestaurant.repositories.PaymentRepository;
import com.mohamed.pizzarestaurant.repositories.ShoppingCartRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    final PaymentRepository paymentRepository;
    final ShoppingCartRepository shoppingCartRepository;
    final CustomerRepository customerRepository;

    public PaymentController(PaymentRepository paymentRepository, ShoppingCartRepository shoppingCartRepository, CustomerRepository customerRepository) {
        this.paymentRepository = paymentRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.customerRepository = customerRepository;
    }

}
