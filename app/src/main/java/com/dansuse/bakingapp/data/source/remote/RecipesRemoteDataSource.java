package com.dansuse.bakingapp.data.source.remote;

import com.dansuse.bakingapp.data.Recipe;
import com.dansuse.bakingapp.data.source.RecipesDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

/**
 * Created by LENOVO on 20/08/2017.
 */

public class RecipesRemoteDataSource implements RecipesDataSource {

    //DUGAAN, kalau field @Inject gini, nda bisa langsung diinject, harus panggil AndroidInjection.inject()
    //@Inject
    @NonNull
    BakingApi mBakingApi;

    //oleh karena itu kita pindah annotation @Inject di constructor
    @Inject
    public RecipesRemoteDataSource(BakingApi bakingApi) {
        mBakingApi = bakingApi;
    }

    @Override
    public Single<List<Recipe>> getRecipes() {
        return mBakingApi.getRecipes();
    }

}
