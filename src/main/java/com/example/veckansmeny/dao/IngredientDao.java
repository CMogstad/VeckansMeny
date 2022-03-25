package com.example.veckansmeny.dao;

import com.example.veckansmeny.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientDao extends JpaRepository<Ingredient, Integer> {
}
