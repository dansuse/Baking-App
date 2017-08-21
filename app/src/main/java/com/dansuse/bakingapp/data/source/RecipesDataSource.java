package com.dansuse.bakingapp.data.source;

import android.support.annotation.NonNull;

import com.dansuse.bakingapp.data.Recipe;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by LENOVO on 20/08/2017.
 */

public interface RecipesDataSource {
    Single<List<Recipe>> getRecipes();
}
