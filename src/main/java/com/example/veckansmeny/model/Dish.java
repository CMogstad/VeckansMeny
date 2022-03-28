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

    @ManyToMany
    List<Ingredient> ingredients;

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
        ingredients.add(ingredient);
        ingredient.getDishes().add(this);
    }

    public void removeIngredient(Ingredient ingredient) {
        getIngredients().remove(ingredient);
        ingredient.getDishes().remove(this);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
