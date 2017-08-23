package com.dansuse.bakingapp.recipes;

import com.dansuse.bakingapp.data.Ingredient;
import com.dansuse.bakingapp.data.Step;

import java.util.List;

/**
 * Created by LENOVO on 23/08/2017.
 */

public interface RecipeCardClickListener {
    void onRecipeCardClick(List<Ingredient> ingredientList, List<Step> stepList);
}
