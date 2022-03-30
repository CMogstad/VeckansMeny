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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.HashSet;
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
        model.addAttribute("dishList", ingredient.getDishes());
        return "update_ingredient";
    }

    @GetMapping("/deleteDish")
    public String deleteDish(Integer dishId, RedirectAttributes redirectAttributes) {
        dishService.deleteDish(dishId);
        redirectAttributes.addFlashAttribute("message", "Dish with id " + dishId + " deleted");
        return "redirect:/";
    }

    @GetMapping("/deleteIngredient")
    public String deleteIngredient(Integer ingredientId) {
        Ingredient ingredient = ingredientService.findIngredientById(ingredientId);
        ingredient.removeIngredientFromDishes();
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

    @GetMapping("/updateIngredient/addDish")
    public String showAddDishToIngredientPage(Model model, Integer ingredientID) {
        List<Dish> dishList = dishService.findAllDishes();
        Ingredient ingredient = ingredientService.findIngredientById(ingredientID);
        model.addAttribute("dishList", dishList);
        model.addAttribute("ingredient", ingredient);
        return "add_dish_to_ingredient";
    }

    @RequestMapping("/updateIngredient/addDish/save")
    public String addDishToIngredient(Model model, Integer ingredientId, Integer dishId) {
        Ingredient ingredient = ingredientService.findIngredientById(ingredientId);
        Dish dish = dishService.findDishBasedOnId(dishId);
        dish.addIngredient(ingredient);
        ingredientService.saveIngredient(ingredient);
        model.addAttribute("ingredient", ingredient);
        model.addAttribute("dishList", ingredient.getDishes());
        return "update_ingredient";
    }

    @GetMapping("/updateIngredient/removeDish")
    public String removeDishFromIngredient(Model model, Integer dishId, Integer ingredientId) {
        Ingredient ingredient = ingredientService.findIngredientById(ingredientId);
        Dish dish = dishService.findDishBasedOnId(dishId);

        ingredient.removeDish(dish);
        ingredientService.saveIngredient(ingredient);

        model.addAttribute("ingredient", ingredient);
        model.addAttribute("dishList", ingredient.getDishes());

        return "update_ingredient";
    }

    @GetMapping("/popularDishes")
    public String showPopularDishesPage(Model model, RedirectAttributes redirectAttributes) {
        if (dishService.findAllDishes().size() < 7) {
            redirectAttributes.addFlashAttribute("message", "At least 7 dishes needed to generate weekly menu");
            return "redirect:/";
        }

        List<Dish> sevenMostPopularDishes = dishService.findSevenMostPopularDishes();
        model.addAttribute("sevenMostPopularDishes", sevenMostPopularDishes);

        List<String> weekdays = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        model.addAttribute("weekdays", weekdays);

        return "popular_weekly_menu";
    }

    @GetMapping("/likeDish")
    public String likeDish(Model model, Integer dishId) {
        Dish dish = dishService.findDishBasedOnId(dishId);
        dish.setLikes(dish.getLikes() + 1);
        dishService.saveDish(dish);

        return "redirect:/";
    }

    List<Dish> menuDishes; //TODO: Is this okay?

    @GetMapping("/generateRandomWeeklyMenu")
    public String showRandomWeeklyMenuPage(Model model, RedirectAttributes redirectAttributes) {
        if (dishService.findAllDishes().size() < 7) {
            redirectAttributes.addFlashAttribute("message", "At least 7 dishes needed to generate weekly menu");
            return "redirect:/";
        }

        menuDishes = dishService.generateRandomDishes();;
        model.addAttribute("dishList", menuDishes);

        List<String> weekdays = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        model.addAttribute("weekdays", weekdays);
        return "random_weekly_menu";
    }

    @GetMapping("/showShoppingList")
    public String showShoppingList(Model model) {
        List<Ingredient> shoppingList = dishService.getShoppingList(menuDishes);
        HashSet<Ingredient> uniqueShoppingList = new HashSet<>(shoppingList);
        model.addAttribute("shoppingList", uniqueShoppingList);
        return "shopping_list";
    }

    @GetMapping("/getBackToMenu")
    public String getBackToMenuFromShoppingList(Model model){
        model.addAttribute("dishList", menuDishes);
        List<String> weekdays = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        model.addAttribute("weekdays", weekdays);
        return "random_weekly_menu";
    }

    @GetMapping("/showShoppingListPopular")
    public String showShoppingListPopular(Model model){
        List<Ingredient> shoppingList = dishService.getShoppingList(dishService.findSevenMostPopularDishes());
        model.addAttribute("shoppingList", shoppingList);
        return "shopping_list";
    }

}
