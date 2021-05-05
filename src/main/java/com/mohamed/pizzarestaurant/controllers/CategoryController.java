package com.mohamed.pizzarestaurant.controllers;

import com.mohamed.pizzarestaurant.entities.Category;
import com.mohamed.pizzarestaurant.exceptions.ResourceNotFoundException;
import com.mohamed.pizzarestaurant.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping(value = "/category",produces = MediaType.APPLICATION_JSON_VALUE)
    public Category saveCategory(@RequestBody Category category){
        List<Category>categories=categoryRepository.findAll();
        categories.stream().forEach(category1 -> {
            if (category1.getCategoryName().equalsIgnoreCase(category.getCategoryName())){
                System.out.println("Category is already stored");
            }
        });
        return categoryRepository.save(category);

    }
    @GetMapping(value = "/categories",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> showAllCategories() throws ResourceNotFoundException{
        List<Category>categories=categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new ResourceNotFoundException("List is Empty !");
        }
        return categories;
    }
}
