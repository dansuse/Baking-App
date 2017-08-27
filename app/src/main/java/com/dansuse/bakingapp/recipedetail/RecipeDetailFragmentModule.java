package com.dansuse.bakingapp.recipedetail;

import android.content.Context;

import com.dansuse.bakingapp.common.BaseActivity;
import com.dansuse.bakingapp.common.BaseActivityModule;
import com.dansuse.bakingapp.di.FragmentScoped;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by LENOVO on 23/08/2017.
 */

@Module()
public abstract class RecipeDetailFragmentModule {
    @Binds
    @FragmentScoped
    abstract RecipeDetailContract.Presenter providePresenter(RecipeDetailPresenter recipeDetailPresenter);

    @Binds
    @FragmentScoped
    abstract RecipeDetailContract.View provideView(RecipeDetailFragment recipeDetailFragment);

    @Binds
    @FragmentScoped
    abstract RecipeDetailClickListener provideRecipeDetailClickListener(RecipeDetailFragment recipeDetailFragment);

    @Provides
    @FragmentScoped
    static RecipeDetailAdapter provideRecipeDetailAdapter(RecipeDetailClickListener recipeDetailClickListener, @Named(BaseActivityModule.ACTIVITY_CONTEXT) Context context){
        return new RecipeDetailAdapter(recipeDetailClickListener, context);
    }

    @Binds
    @FragmentScoped
    abstract RecipeDetailActivityFragmentInteraction provideRecipeDetailInteraction(RecipeDetailActivity recipeDetailActivity);

}
