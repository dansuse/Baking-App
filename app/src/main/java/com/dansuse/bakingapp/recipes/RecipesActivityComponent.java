package com.dansuse.bakingapp.recipes;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by LENOVO on 20/08/2017.
 */

@Subcomponent(modules = RecipesModule.class)
public interface RecipesActivityComponent extends AndroidInjector<RecipesActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<RecipesActivity>{}
}
