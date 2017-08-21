package com.dansuse.bakingapp.data.source.remote;

import com.dansuse.bakingapp.data.Recipe;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by LENOVO on 20/08/2017.
 */

public interface BakingApi {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Single<List<Recipe>> getRecipes();
}
