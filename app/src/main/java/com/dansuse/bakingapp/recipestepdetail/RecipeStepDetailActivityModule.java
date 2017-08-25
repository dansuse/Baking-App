package com.dansuse.bakingapp.recipestepdetail;

import android.support.v7.app.AppCompatActivity;

import com.dansuse.bakingapp.common.BaseActivityModule;
import com.dansuse.bakingapp.di.ActivityScoped;
import com.dansuse.bakingapp.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by LENOVO on 23/08/2017.
 */

@Module(includes = BaseActivityModule.class)
public abstract class RecipeStepDetailActivityModule {
    @Binds
    @ActivityScoped
    abstract AppCompatActivity provideAppCompatActivity(RecipeStepDetailActivity recipeStepDetailActivity);

    @FragmentScoped
    @ContributesAndroidInjector(modules = RecipeStepDetailFragmentModule.class)
    abstract RecipeStepDetailFragment recipeStepDetailFragmentInjector();
}
