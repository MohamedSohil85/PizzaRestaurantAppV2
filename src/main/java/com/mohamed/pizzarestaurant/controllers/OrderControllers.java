package com.mohamed.pizzarestaurant.controllers;

import com.mohamed.pizzarestaurant.entities.Customer;
import com.mohamed.pizzarestaurant.entities.Orders;
import com.mohamed.pizzarestaurant.entities.ShoppingCart;
import com.mohamed.pizzarestaurant.exceptions.ResourceNotFoundException;
import com.mohamed.pizzarestaurant.repositories.CustomerRepository;
import com.mohamed.pizzarestaurant.repositories.OrderRepository;
import com.mohamed.pizzarestaurant.repositories.ProductRepository;
import com.mohamed.pizzarestaurant.repositories.ShoppingCartRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class OrderControllers {

    final OrderRepository orderRepository;
    final CustomerRepository customerRepository;
    final ProductRepository productRepository;
    final ShoppingCartRepository shoppingCartRepository;

    public OrderControllers(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository, ShoppingCartRepository shoppingCartRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }
    @PostMapping(value = "/orderByProductName/{productName}/Order",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity makeOrder(@PathVariable("productName")String productName,@RequestBody Orders order){
        return productRepository.findByName(productName).map(product -> {

            order.getProduct().add(product);
            order.setOrderDate(new Date());
            order.setTotal(product.getPrice()* order.getQuantity());
            return new ResponseEntity(orderRepository.save(order),HttpStatus.CREATED);
        }).orElse(new ResponseEntity(HttpStatus.NO_CONTENT));
    }

@DeleteMapping(value = "/deleteOrderById/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity deleteOrder(@PathVariable("id")Long id){
        return orderRepository.findById(id).map(order -> {
            orderRepository.delete(order);
            return new ResponseEntity("Order with id :"+id+" has been deleted",HttpStatus.OK);
        }).orElse(new ResponseEntity(HttpStatus.NO_CONTENT));
}
    @GetMapping(value = "/orders",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity loadAllOrders(){
        List<Orders> orderList=orderRepository.findAll(Sort.by(Sort.Direction.ASC,"OrderDate"));
        return new ResponseEntity(orderList,HttpStatus.FOUND);
    }
    @PostMapping(value = "Cart/{customerId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ShoppingCart createShoppingCart(@PathVariable("customerId")Long id,@RequestBody ShoppingCart shoppingCart)throws ResourceNotFoundException{
        Optional<Customer>customerOptional=customerRepository.findById(id);
        Customer customer=customerOptional.orElseThrow(()->new ResourceNotFoundException("Customer not found"));
        shoppingCart.setCustomer(customer);
    return  shoppingCartRepository.save(shoppingCart);
    }
    @PostMapping(value = "/Cart/{cartId}/order/{orderId}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity saveToShoppingCard(@PathVariable("orderId")Long orderId,@PathVariable("orderId")Long id){
        return orderRepository.findById(orderId).map(order -> {
            Optional<ShoppingCart>shoppingCartOptional=shoppingCartRepository.findById(id);
            ShoppingCart shoppingCart=shoppingCartOptional.get();
            shoppingCart.getOrders().add(order);
            order.setShoppingCart(shoppingCart);
            double sum= orderRepository.findAll().stream().mapToDouble(Orders::getTotal).sum();
            shoppingCart.setTotal(sum);
            return new ResponseEntity<>(orderRepository.save(order),HttpStatus.CREATED);
        }).orElse(new ResponseEntity(HttpStatus.NO_CONTENT));
    }
}
