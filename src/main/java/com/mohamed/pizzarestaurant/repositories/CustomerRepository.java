package com.mohamed.pizzarestaurant.repositories;

import com.mohamed.pizzarestaurant.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findCustomerByAddressAndLastNameAndPhoneNumber(String address,String name,String number);
    Optional<Customer> findCustomerByToken(String token);
}
