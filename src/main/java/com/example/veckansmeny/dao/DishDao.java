package com.example.veckansmeny.dao;

import com.example.veckansmeny.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishDao extends JpaRepository<Dish, Integer> {
}
