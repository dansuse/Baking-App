package com.dansuse.bakingapp.data.source;

import com.dansuse.bakingapp.data.Recipe;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by LENOVO on 23/08/2017.
 */

public interface RecipesRepository {
    Single<List<Recipe>> getRecipes(boolean fromCacheOnly);
    void refreshRecipes();
}
