package com.dansuse.bakingapp.recipestepdetail;

import com.dansuse.bakingapp.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;

/**
 * Created by LENOVO on 23/08/2017.
 */

@Module
public abstract class RecipeStepDetailFragmentModule {
    @Binds
    @FragmentScoped
    abstract RecipeStepDetailContract.Presenter providePresenter(RecipeStepDetailPresenter recipeStepDetailPresenter);

    @Binds
    @FragmentScoped
    abstract RecipeStepDetailContract.View provideView(RecipeStepDetailFragment recipeStepDetailFragment);
}
