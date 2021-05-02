package com.mohamed.pizzarestaurant.controllers;
import com.mohamed.pizzarestaurant.entities.Product;
import com.mohamed.pizzarestaurant.repositories.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    final ProductRepository productRepository;


    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @PostMapping(value = "/product",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity saveNewProduct(@RequestBody Product product){
        Optional<Product>productOptional=productRepository.findByProductName(product.getPizzaName());
        if(productOptional.isPresent()){
            return new ResponseEntity(HttpStatus.FOUND);
        }
        return new ResponseEntity(productRepository.save(product),HttpStatus.CREATED);
    }
    @GetMapping(value = "/products",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity loadAllProducts(){
        List<Product>products=(List)productRepository.findAll(Sort.by(Sort.Direction.ASC,"ProductName"));
        if(products.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(products);
    }
    @PutMapping(value = "/sale/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity setNewPrice(@PathVariable("name")String productName,@RequestBody Product newProduct){
        return productRepository.findByProductName(productName).map(product -> {
            product.setPrice(newProduct.getPrice());
            return new ResponseEntity(productRepository.save(product),HttpStatus.CREATED);
        }).orElse(ResponseEntity.noContent().build());
    }


}
