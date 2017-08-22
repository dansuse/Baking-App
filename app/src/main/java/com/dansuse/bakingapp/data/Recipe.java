package com.dansuse.bakingapp.data;

import java.util.List;

/**
 * Created by LENOVO on 20/08/2017.
 */

public class Recipe {
    Integer id;
    String name;
    Integer servings;
    String image;

    //Ingredient.IngredientList ingredients;
    //Step.StepList steps;
    List<Ingredient> ingredients;
    List<Step> steps;

    public Recipe(Integer id, String name, Integer servings, String image) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

}
