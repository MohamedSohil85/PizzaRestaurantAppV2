package com.mohamed.pizzarestaurant.repositories;

import com.mohamed.pizzarestaurant.entities.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews,Long> {

    List<Reviews> findByProduct_ProductName(String name);
}
