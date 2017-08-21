package com.dansuse.bakingapp.recipes;

import android.os.Bundle;

import com.dansuse.bakingapp.R;
import com.dansuse.bakingapp.common.BaseActivity;

public class RecipesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        addFragment(R.id.recipes_fragment_container, RecipesFragment.newInstance());
    }
}
