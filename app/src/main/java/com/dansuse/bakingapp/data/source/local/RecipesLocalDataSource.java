package com.dansuse.bakingapp.data.source.local;

import com.dansuse.bakingapp.data.Recipe;
import com.dansuse.bakingapp.data.source.RecipesDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by LENOVO on 20/08/2017.
 */

public class RecipesLocalDataSource implements RecipesDataSource {
    @Override
    public Single<List<Recipe>> getRecipes() {
        return null;
    }

    //harus ada constructor dengan annotation @Inject apabila menggunakan abstract method @Binds di abstract @Module
    //namun jika menggunakan @Provides, tidak diperlukan annotation @Inject
    @Inject
    public RecipesLocalDataSource() {
    }
}
