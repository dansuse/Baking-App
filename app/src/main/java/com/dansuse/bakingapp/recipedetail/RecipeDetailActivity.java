package com.dansuse.bakingapp.recipedetail;

import android.content.Intent;
import android.os.Bundle;

import com.dansuse.bakingapp.R;
import com.dansuse.bakingapp.common.BaseActivity;
import com.dansuse.bakingapp.data.Ingredient;
import com.dansuse.bakingapp.data.Step;
import com.google.common.collect.Lists;

import java.util.List;

public class RecipeDetailActivity extends BaseActivity {
    public static final String EXTRA_LIST_INGREDIENT = "RecipeDetailActivity.extra_list_ingredient";
    public static final String EXTRA_LIST_STEP = "RecipeDetailActivity.extra_list_step";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        if(savedInstanceState == null){
            Intent intent = getIntent();
            if(intent != null && intent.hasExtra(EXTRA_LIST_INGREDIENT) && intent.hasExtra(EXTRA_LIST_STEP)){
                List<Ingredient> ingredientList = intent.getParcelableArrayListExtra(EXTRA_LIST_INGREDIENT);
                List<Step> stepList = intent.getParcelableArrayListExtra(EXTRA_LIST_STEP);
                addFragment(R.id.recipe_detail_fragment_container, RecipeDetailFragment.newInstance(ingredientList, stepList));
            }
        }
    }
}
