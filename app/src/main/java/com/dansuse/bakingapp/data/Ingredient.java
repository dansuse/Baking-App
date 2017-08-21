package com.dansuse.bakingapp.data;

import java.util.List;

/**
 * Created by LENOVO on 20/08/2017.
 */

public class Ingredient {
    Float quantity;
    String measure;
    String ingredient;

    public Ingredient(Float quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }
//    class IngredientList{
//        List<Ingredient> mIngredientList;
//    }


    public String getIngredient() {
        return ingredient;
    }
}
