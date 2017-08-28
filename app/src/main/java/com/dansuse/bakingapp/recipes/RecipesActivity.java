package com.dansuse.bakingapp.recipes;

import android.os.Bundle;

import com.dansuse.bakingapp.R;
import com.dansuse.bakingapp.SimpleIdlingResource;
import com.dansuse.bakingapp.common.BaseActivity;

import javax.inject.Inject;

public class RecipesActivity extends BaseActivity {

    @Inject
    SimpleIdlingResource mSimpleIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        if(savedInstanceState == null){
            addFragment(R.id.recipes_fragment_container, RecipesFragment.newInstance());
        }
    }

    public SimpleIdlingResource getSimpleIdlingResource() {
        return mSimpleIdlingResource;
    }
}
