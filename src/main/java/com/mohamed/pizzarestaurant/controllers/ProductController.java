package com.mohamed.pizzarestaurant.controllers;
import com.mohamed.pizzarestaurant.entities.Category;
import com.mohamed.pizzarestaurant.entities.Product;
import com.mohamed.pizzarestaurant.exceptions.ResourceNotFoundException;
import com.mohamed.pizzarestaurant.repositories.CategoryRepository;
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
    final CategoryRepository categoryRepository;

    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @PostMapping(value = "/product/{categoryId}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity saveNewProduct(@RequestBody Product product,@PathVariable("categoryId")Long id)throws ResourceNotFoundException{
        Optional<Product>productOptional=productRepository.findByName(product.getName());
        if(productOptional.isPresent()){
            return new ResponseEntity(HttpStatus.FOUND);
        }
        Optional<Category>categoryOptional=categoryRepository.findById(id);
        Category category=categoryOptional.orElseThrow(()->new ResourceNotFoundException("Category with id :"+id+" not found"));
        category.getProducts().add(product);
        product.setCategory(category);
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
    @PutMapping(value = "/changeprice/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Product setNewPrice(@PathVariable("name")String productName,@RequestBody Product newProduct) throws ResourceNotFoundException{
        return productRepository.findByName(productName).map(product -> {
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
