package com.example.veckansmeny.domain;

import com.example.veckansmeny.model.Dish;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VeckansMenyDomain {

    private List<Dish> menuDishes = new ArrayList<>();

    public List<Dish> getMenuDishes(){
        return menuDishes;
    }

    public void setMenuDishes(List<Dish> menuDishes) {
        this.menuDishes = menuDishes;
    }

    public List<String> getWeekdays(){
        return Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
    }


}
