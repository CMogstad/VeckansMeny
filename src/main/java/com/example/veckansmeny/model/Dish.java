package com.example.veckansmeny.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String type;
    private int likes;

    @ManyToMany
    List<Ingredient> ingredients;

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Ingredient> getIngredients() {
        if (ingredients == null) {
            ingredients = new ArrayList<>();
        }
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        getIngredients().add(ingredient);
        ingredient.getDishes().add(this);
    }

    public void removeIngredient(Ingredient ingredient) {
        getIngredients().remove(ingredient);
        ingredient.getDishes().remove(this);
    }

    public String getIngredientsFormatted() {
        String formattedIngredients = "";
        for(Ingredient ingredient : ingredients){
            if(ingredients.indexOf(ingredient) == ingredients.size()-1){
                formattedIngredients += (ingredient.getName());
            } else {
                formattedIngredients += (ingredient.getName() + ", ");
            }
        }
        return formattedIngredients;
    }

}
