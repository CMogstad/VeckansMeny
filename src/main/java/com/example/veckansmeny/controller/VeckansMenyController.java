package com.example.veckansmeny.controller;

import com.example.veckansmeny.model.Dish;
import com.example.veckansmeny.model.Ingredient;
import com.example.veckansmeny.service.DishService;
import com.example.veckansmeny.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class VeckansMenyController {

    @Autowired
    private DishService dishService;

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/")
    public String showHomePage(Model model) {
        List<Dish> listOfDishes = dishService.findAllDishes();
        List<Ingredient> listOfIngredients = ingredientService.getAllIngredients();

        model.addAttribute("dishList", listOfDishes);
        model.addAttribute("ingredientList", listOfIngredients);
        return "index";
    }


    @GetMapping("/dishes/new")
    public String showAddDish(Model model) {
        model.addAttribute("dish", new Dish());
        return "add_dish";
    }

    @PostMapping("/dish/save")
    public String saveDish(Dish dish) {
        dishService.saveDish(dish);
        return "redirect:/";
    }

    @GetMapping("/addNewIngredient")
    public String showAddNewIngredientPage(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "addNewIngredient";
    }

    @GetMapping("/updateDish")
    public String showUpdateDishPage(Model model, Integer dishId) {
        model.addAttribute("id", dishId);
        return "updateDish";
    }

    @GetMapping("/updateIngredient")
    public String showUpdateIngredientPage(Model model, Integer ingredientId) {
        model.addAttribute("id", ingredientId);
        return "updateIngredient";
    }

    @GetMapping("/deleteDish")
    public String deleteDish(Integer dishId) {
        deleteDish(dishId);
        return "redirect:/";
    }

    @GetMapping("/deleteIngredient")
    public String deleteIngredient(Integer ingredientId) {
        deleteIngredient(ingredientId);
        return "redirect:/";
    }
}
