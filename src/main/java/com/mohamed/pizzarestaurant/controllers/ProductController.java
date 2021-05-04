package com.mohamed.pizzarestaurant.controllers;
import com.mohamed.pizzarestaurant.entities.Product;
import com.mohamed.pizzarestaurant.exceptions.ResourceNotFoundException;
import com.mohamed.pizzarestaurant.repositories.ProductRepository;
import org.hibernate.ResourceClosedException;
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
        Optional<Product>productOptional=productRepository.findByPizzaName(product.getPizzaName());
        if(productOptional.isPresent()){
            return new ResponseEntity(HttpStatus.FOUND);
        }
        return new ResponseEntity(productRepository.save(product),HttpStatus.CREATED);
    }
    @GetMapping(value = "/products",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity loadAllProducts() throws ResourceNotFoundException {
        List<Product>products=(List)productRepository.findAll(Sort.by(Sort.Direction.ASC,"ProductName"));
        if(products.isEmpty()){
            throw new ResourceNotFoundException("List is Empty");
        }
        return ResponseEntity.ok().body(products);
    }
    @PutMapping(value = "/sale/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Product setNewPrice(@PathVariable("name")String productName,@RequestBody Product newProduct) throws ResourceNotFoundException{
        return productRepository.findByPizzaName(productName).map(product -> {
            product.setPrice(newProduct.getPrice());
            return productRepository.save(product);
        }).orElseThrow(()->new ResourceNotFoundException("Product not found"));
    }
   @GetMapping(value = "product/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Product getProductById(@PathVariable("id")Long id)throws ResourceNotFoundException{
       return productRepository
               .findById(id)
               .orElseThrow(()->new ResourceClosedException("Product with Id"+id+"not found"));
   }

}
