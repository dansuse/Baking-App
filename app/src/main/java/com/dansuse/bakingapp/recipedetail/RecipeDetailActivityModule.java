package com.dansuse.bakingapp.recipedetail;

import android.support.v7.app.AppCompatActivity;

import com.dansuse.bakingapp.common.BaseActivityModule;
import com.dansuse.bakingapp.di.ActivityScoped;
import com.dansuse.bakingapp.di.FragmentScoped;
import com.dansuse.bakingapp.recipestepdetail.RecipeStepDetailFragment;
import com.dansuse.bakingapp.recipestepdetail.RecipeStepDetailFragmentModule;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by LENOVO on 23/08/2017.
 */

@Module(includes = BaseActivityModule.class)
public abstract class RecipeDetailActivityModule {
    @Binds
    @ActivityScoped
    abstract AppCompatActivity provideAppCompatActivity(RecipeDetailActivity recipeDetailActivity);

    @FragmentScoped
    @ContributesAndroidInjector(modules = RecipeDetailFragmentModule.class)
    abstract RecipeDetailFragment recipeDetailFragmentInjector();

    @FragmentScoped
    @ContributesAndroidInjector(modules = RecipeStepDetailFragmentModule.class)
    abstract RecipeStepDetailFragment twoPaneRecipeStepDetailFragmentInjector();
}
