package com.dansuse.bakingapp.di.module;

import android.app.Activity;

import com.dansuse.bakingapp.recipes.RecipesActivity;
import com.dansuse.bakingapp.recipes.RecipesActivityComponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by LENOVO on 20/08/2017.
 */

@Module
public abstract class ActivityBuilder {
    @Binds
    @IntoMap
    @ActivityKey(RecipesActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindRecipesActivity(RecipesActivityComponent.Builder builder);
}
