package com.dansuse.bakingapp.recipedetail;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dansuse.bakingapp.R;
import com.dansuse.bakingapp.RecipeWidgetService;
import com.dansuse.bakingapp.common.BaseActivity;
import com.dansuse.bakingapp.data.Ingredient;
import com.dansuse.bakingapp.data.Recipe;
import com.dansuse.bakingapp.data.Step;
import com.dansuse.bakingapp.recipestepdetail.RecipeStepDetailActivity;
import com.dansuse.bakingapp.recipestepdetail.RecipeStepDetailFragment;
import com.google.common.collect.Lists;

import java.util.List;

import static com.dansuse.bakingapp.data.source.local.RecipeDbContract.IngredientEntry;
import static com.dansuse.bakingapp.data.source.local.RecipeDbContract.RecipeEntry;

public class RecipeDetailActivity extends BaseActivity implements RecipeDetailActivityFragmentInteraction{
    //public static final String EXTRA_LIST_INGREDIENT = "RecipeDetailActivity.extra_list_ingredient";
    //public static final String EXTRA_LIST_STEP = "RecipeDetailActivity.extra_list_step";
    public static final String EXTRA_RECIPE = "RecipeDetailActivity.extra_recipe";
    public static final String SAVED_RECIPE = "RecipeDetailActivity.saved_recipe";
    private boolean mTwoPane = false;

    private List<Step>mStepList;
    private Recipe mRecipe;

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
//            if(intent != null && intent.hasExtra(EXTRA_LIST_INGREDIENT) && intent.hasExtra(EXTRA_LIST_STEP)){
//                List<Ingredient> ingredientList = intent.getParcelableArrayListExtra(EXTRA_LIST_INGREDIENT);
//                mStepList = intent.getParcelableArrayListExtra(EXTRA_LIST_STEP);
//                addFragment(R.id.recipe_detail_fragment_container, RecipeDetailFragment.newInstance(ingredientList, mStepList));
//                if(mTwoPane){
//                    addFragment(R.id.recipe_step_detail_frag_container, RecipeStepDetailFragment.newInstance(mStepList.get(0)));
//                }
//            }
            //Log.d("tes123", String.valueOf(intent == null));
            if(intent != null && intent.hasExtra(EXTRA_RECIPE)){
                mRecipe = intent.getParcelableExtra(EXTRA_RECIPE);
                mStepList = mRecipe.getSteps();
                addFragment(R.id.recipe_detail_fragment_container, RecipeDetailFragment.newInstance(mRecipe.getIngredients(), mStepList));
                if(mTwoPane){
                    addFragment(R.id.recipe_step_detail_frag_container, RecipeStepDetailFragment.newInstance(mStepList.get(0)));
                }
            }
        }else{
            if(savedInstanceState.containsKey(SAVED_RECIPE)){
                mRecipe = savedInstanceState.getParcelable(SAVED_RECIPE);
                mStepList = mRecipe.getSteps();
            }
        }
        if(getSupportActionBar() != null && mRecipe != null){
            getSupportActionBar().setTitle(mRecipe.getName());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SAVED_RECIPE, mRecipe);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_pin_to_widget){
            new UpdateFavRecipeInDB().execute();
        }
        return super.onOptionsItemSelected(item);
    }
    Toast mToast;
    class UpdateFavRecipeInDB extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(mToast != null){
                mToast.cancel();
            }
            mToast = Toast.makeText(RecipeDetailActivity.this, RecipeDetailActivity.this.getString(R.string.wait), Toast.LENGTH_SHORT);
            mToast.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Uri ingreUri = IngredientEntry.CONTENT_URI;
            getContentResolver().delete(ingreUri, null, null);
            //Uri stepUri = StepEntry.CONTENT_URI;
            //getContentResolver().delete(stepUri, null, null);
            Uri recipeUri = RecipeEntry.CONTENT_URI;
            getContentResolver().delete(recipeUri, null, null);

            ContentValues recipeValues = new ContentValues();
            recipeValues.put(RecipeEntry._ID, mRecipe.getId());
            recipeValues.put(RecipeEntry.COLUMN_NAME, mRecipe.getName());
            recipeValues.put(RecipeEntry.COLUMN_IMAGE, mRecipe.getImage());
            recipeValues.put(RecipeEntry.COLUMN_SERVINGS, mRecipe.getServings());
            recipeValues.put(RecipeEntry.COLUMN_IS_FAVORITE, 1);
            getContentResolver().insert(RecipeEntry.CONTENT_URI, recipeValues);

            ContentValues[] ingreValues = new ContentValues[mRecipe.getIngredients().size()];
            List<Ingredient> tempIngreList = mRecipe.getIngredients();
            for(int i=0 ; i<tempIngreList.size() ; i++){
                ingreValues[i] = new ContentValues();
                ingreValues[i].put(IngredientEntry.COLUMN_INGREDIENT, tempIngreList.get(i).getIngredient());
                ingreValues[i].put(IngredientEntry.COLUMN_MEASURE, tempIngreList.get(i).getMeasure());
                ingreValues[i].put(IngredientEntry.COLUMN_QUANTITY, tempIngreList.get(i).getQuantity());
                ingreValues[i].put(IngredientEntry.COLUMN_RECIPE_ID, mRecipe.getId());
            }
            Uri uriForInsertIngre = IngredientEntry.CONTENT_URI.buildUpon()
                    .appendPath(String.valueOf(mRecipe.getId())).build();
            getContentResolver().bulkInsert(uriForInsertIngre, ingreValues);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(mToast != null){
                mToast.cancel();
            }
            mToast = Toast.makeText(RecipeDetailActivity.this, RecipeDetailActivity.this.getString(R.string.done), Toast.LENGTH_SHORT);
            mToast.show();
            RecipeWidgetService.startActionQueryFavRecipeIngredient(RecipeDetailActivity.this);
        }
    }
}
