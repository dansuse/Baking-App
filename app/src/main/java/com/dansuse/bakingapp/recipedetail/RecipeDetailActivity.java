package com.dansuse.bakingapp.recipedetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.dansuse.bakingapp.R;
import com.dansuse.bakingapp.common.BaseActivity;
import com.dansuse.bakingapp.data.Ingredient;
import com.dansuse.bakingapp.data.Step;
import com.dansuse.bakingapp.recipestepdetail.RecipeStepDetailActivity;
import com.dansuse.bakingapp.recipestepdetail.RecipeStepDetailFragment;
import com.google.common.collect.Lists;

import java.util.List;

public class RecipeDetailActivity extends BaseActivity implements RecipeDetailActivityFragmentInteraction{
    public static final String EXTRA_LIST_INGREDIENT = "RecipeDetailActivity.extra_list_ingredient";
    public static final String EXTRA_LIST_STEP = "RecipeDetailActivity.extra_list_step";
    private boolean mTwoPane = false;

    private List<Step>mStepList;

    @Override
    public void onRecipeDetailClickCallActivity(int selectedIndex) {
        if(mTwoPane){
            addFragment(R.id.recipe_step_detail_frag_container, RecipeStepDetailFragment.newInstance(mStepList.get(selectedIndex)));
        }else{
            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            intent.putParcelableArrayListExtra(RecipeStepDetailActivity.EXTRA_STEP_LIST, Lists.<Parcelable>newArrayList(mStepList));
            intent.putExtra(RecipeStepDetailActivity.EXTRA_SELECTED_INDEX, selectedIndex);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        if(findViewById(R.id.recipe_step_detail_frag_container) != null){
            mTwoPane = true;
        }else{
            mTwoPane = false;
        }
        if(savedInstanceState == null){
            Intent intent = getIntent();
            if(intent != null && intent.hasExtra(EXTRA_LIST_INGREDIENT) && intent.hasExtra(EXTRA_LIST_STEP)){
                List<Ingredient> ingredientList = intent.getParcelableArrayListExtra(EXTRA_LIST_INGREDIENT);
                mStepList = intent.getParcelableArrayListExtra(EXTRA_LIST_STEP);
                addFragment(R.id.recipe_detail_fragment_container, RecipeDetailFragment.newInstance(ingredientList, mStepList));
                if(mTwoPane){
                    addFragment(R.id.recipe_step_detail_frag_container, RecipeStepDetailFragment.newInstance(mStepList.get(0)));
                }
            }
        }
    }
}
