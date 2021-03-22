package com.mohamed.pizzarestaurant.repositories;

import com.mohamed.pizzarestaurant.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
}
