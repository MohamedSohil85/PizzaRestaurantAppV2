package com.mohamed.pizzarestaurant.controllers;

import com.mohamed.pizzarestaurant.entities.Customer;
import com.mohamed.pizzarestaurant.repositories.CustomerRepository;
import com.mohamed.pizzarestaurant.repositories.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
public class CustomerController {

    final CustomerRepository customerRepository;
    final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository, ShoppingCartRepository shoppingCartRepository) {
        this.customerRepository = customerRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }




    @PostMapping(value = "/Customer",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> saveNewCustomer(@RequestBody Customer customer){
        String lastName=customer.getLastName();
        String address= customer.getAddress();
        String phoneNumber= customer.getPhoneNumber();
        Optional<Customer>customerOptional=customerRepository.findCustomerByAddressAndLastNameAndPhoneNumber(address,lastName,phoneNumber).stream().findFirst();
        if(customerOptional.isPresent()){

            return new  ResponseEntity("the Object is Already stored",HttpStatus.FOUND);
        }
        String customerToken= UUID.randomUUID().toString();
        customer.setToken(customerToken);
        return new ResponseEntity(customerRepository.save(customer),HttpStatus.CREATED);
    }
    @GetMapping(value = "/Customers",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> loadCustomers(){
        List<Customer> customerList=(List)customerRepository
                .findAll(Sort.by(Sort.Direction.ASC,"lastName"));
        if(customerList.isEmpty()){
            return  ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(customerList,HttpStatus.FOUND);
    }
    @PutMapping(value = "/updateById/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity editeCustomerById(@PathVariable("id")Long id,Customer newcCustomer){
        return customerRepository.findById(id).map(customer -> {
            customer.setPhoneNumber(newcCustomer.getPhoneNumber());
            customer.setAddress(newcCustomer.getAddress());
            return new ResponseEntity<>(customerRepository.save(customer),HttpStatus.CREATED);
        }).orElse(ResponseEntity.noContent().build());
    }
    @GetMapping(value = "/customer/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    Customer getCustomerById(@PathVariable("id")Long id){
        List<Customer>customers=customerRepository.findAll();
       Customer customer= customers.stream().filter(customer1 -> customer1.getId().equals(id)).findAny().get();
       return customer;

    }
}
