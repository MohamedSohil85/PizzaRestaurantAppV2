package com.mohamed.pizzarestaurant.controllers;

import com.mohamed.pizzarestaurant.entities.Reviews;
import com.mohamed.pizzarestaurant.repositories.ProductRepository;
import com.mohamed.pizzarestaurant.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class ReviewsController {

    final ProductRepository productRepository;
    final ReviewRepository reviewRepository;


    public ReviewsController(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }
    @PostMapping(value = "/Reviews/{productId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Reviews createReviews(@RequestBody Reviews reviews, @PathVariable("productId")Long id){
        return productRepository.findById(id).map(product -> {
            product.getReviewsList().add(reviews);
            reviews.setProduct(product);
            return reviewRepository.save(reviews);
        }).get();
    }
    @GetMapping(value = "/loadReviews",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reviews>loadReviews(){
        List<Reviews>reviews=reviewRepository.findAll();
        return reviews;
    }
    @GetMapping(value = "ReviewsByProductName/{productName}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reviews>getReviewsByProductName(@PathVariable("productName")String name){

        List<Reviews>reviewsList=reviewRepository.findByProduct_ProductName(name)
                .stream()
                .sorted(Comparator.comparingInt(Reviews::getRating))
                .collect(Collectors.toList());
        return reviewsList;

    }
}
