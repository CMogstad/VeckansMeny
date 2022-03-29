package com.example.veckansmeny.service;

import com.example.veckansmeny.dao.DishDao;
import com.example.veckansmeny.dao.IngredientDao;
import com.example.veckansmeny.model.Dish;
import com.example.veckansmeny.model.Ingredient;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DishService {

    @Autowired
    DishDao dishDao;

    public void saveDish(Dish dish) {
        dishDao.save(dish);
    }

    public List<Dish> findAllDishes() {
        return dishDao.findAll();
    }

    public void deleteDish(Integer dishId) {
        Dish dish = dishDao.findById(dishId).get();
        dishDao.delete(dish);
    }

    public Dish findDishBasedOnId(Integer dishId) {
        return dishDao.findById(dishId).get();
    }

    public List<Dish> generateRandomDishes() {
        List<Dish> dishes = findAllDishes();

        List<Dish> selectDishes = new ArrayList<>();

        ThreadLocalRandom.current().ints(0, dishes.size()).distinct().limit(7)
                .forEach(d -> selectDishes.add(dishes.get(d)));

        return selectDishes;


    }

    public List<Ingredient> getShoppingList(List<Dish> dishList) {
        List<Ingredient> shoppingList = new ArrayList<>();
        for (Dish dish : dishList) {
            for (Ingredient ingredient : dish.getIngredients()) {
                shoppingList.add(ingredient);
            }
        }
        return shoppingList;
    }

}
