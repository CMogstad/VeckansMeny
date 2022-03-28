package com.example.veckansmeny.service;

import com.example.veckansmeny.dao.IngredientDao;
import com.example.veckansmeny.model.Dish;
import com.example.veckansmeny.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientService {

    @Autowired
    IngredientDao ingredientDao;

    public void saveIngredient(Ingredient ingredient) {
        ingredientDao.save(ingredient);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientDao.findAll();
    }

    public void deleteIngredient(Integer ingredientId) {
        Ingredient ingredient = ingredientDao.findById(ingredientId).get();
        ingredientDao.delete(ingredient);
    }

    public void removeDishFromIngredients(List<Ingredient> ingredients, Dish dish) {
        for (Ingredient ingredient : ingredients) {
            ingredient.getDishes().remove(dish);
            saveIngredient(ingredient);
        }
    }

    public Ingredient findIngredientById(Integer ingredientId) {
        return ingredientDao.findById(ingredientId).get();
    }
}
