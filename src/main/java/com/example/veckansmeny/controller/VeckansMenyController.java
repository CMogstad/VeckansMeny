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
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/en")
    public String showIndex() {
        return "index";
    }

    @GetMapping("/dishes/new")
    public String showAddDish(Model model) {
        model.addAttribute("dish", new Dish());
        return "add_dish";
    }

    @PostMapping("/dish/save")
    public String saveDish(Model model, Dish dish) {
        dishService.saveDish(dish);
        model.addAttribute("dish", dish);
        model.addAttribute("ingredientList", dish.getIngredients());
        return "update_dish";
    }

    @GetMapping("/addNewIngredient")
    public String showAddNewIngredientPage(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "add_ingredient";
    }

    @GetMapping("/updateDish")
    public String showUpdateDishPage(Model model, Integer dishId) {
        Dish dish = dishService.findDishBasedOnId(dishId);
        model.addAttribute("dish", dish);
        model.addAttribute("ingredientList", dish.getIngredients());
        return "update_dish";
    }

    @GetMapping("/updateIngredient")
    public String showUpdateIngredientPage(Model model, Integer ingredientId) {
        Ingredient ingredient = ingredientService.findIngredientById(ingredientId);
        model.addAttribute("ingredient", ingredient);
        return "update_ingredient";
    }

    @GetMapping("/deleteDish")
    public String deleteDish(Integer dishId) {
        dishService.deleteDish(dishId);
        return "redirect:/";
    }

    @GetMapping("/deleteIngredient")
    public String deleteIngredient(Integer ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
        return "redirect:/";
    }

    @PostMapping("/ingredient/save")
    public String saveIngredient(Ingredient ingredient) {
        ingredientService.saveIngredient(ingredient);
        return "redirect:/";
    }

    @GetMapping("/ingredient/new")
    public String showAddIngredient(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "add_ingredient";
    }

    @GetMapping("/updateDish/addIngredient")
    public String showAddIngredientToDishPage(Model model, Integer dishID) {
        List<Ingredient> ingredientList = ingredientService.getAllIngredients();
        Dish dish = dishService.findDishBasedOnId(dishID);

        model.addAttribute("ingredientList", ingredientList);
        model.addAttribute("dish", dish);
        return "add_ingredient_to_dish";
    }

    @RequestMapping("/updateDish/addIngredient/save")
    public String addIngredientToDish(Model model, Integer ingredientID, Integer dishID) {
        Dish dish = dishService.findDishBasedOnId(dishID);
        Ingredient ingredient = ingredientService.findIngredientById(ingredientID);

        dish.addIngredient(ingredient);
        dishService.saveDish(dish);

        model.addAttribute("dish", dish);
        model.addAttribute("ingredientList", dish.getIngredients());

        return "update_dish";
    }

    @GetMapping("/updateDish/removeIngredient")
    public String removeIngredientFromDish(Model model, Integer dishID, Integer ingredientId) {
        Dish dish = dishService.findDishBasedOnId(dishID);
        Ingredient ingredient = ingredientService.findIngredientById(ingredientId);

        dish.removeIngredient(ingredient);
        dishService.saveDish(dish);

        model.addAttribute("dish", dish);
        model.addAttribute("ingredientList", dish.getIngredients());

        return "update_dish";
    }
}
