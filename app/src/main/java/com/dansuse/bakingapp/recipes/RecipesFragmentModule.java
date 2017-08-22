package com.dansuse.bakingapp.recipes;

import android.content.Context;

import com.dansuse.bakingapp.common.BaseActivityModule;
import com.dansuse.bakingapp.di.FragmentScoped;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by LENOVO on 21/08/2017.
 */

@Module
public abstract class RecipesFragmentModule {

    //dependency dibawah ini tidak apa2 dikomen karena tidak ada yang memakai kelas Fragment.
    //Berbeda dengan di RecipesModule dimana AppCompatActivity dipakai di BaseActivityModule
//    @Binds
//    @FragmentScoped
//    abstract Fragment fragment(RecipesFragment recipesFragment);

    @Binds
    @FragmentScoped
    abstract RecipesContract.Presenter providePresenter(RecipesPresenter recipesPresenter);

    @Binds
    @FragmentScoped
    abstract RecipesContract.View provideView(RecipesFragment recipesFragment);

    @Provides
    @FragmentScoped
    static RecipeCardAdapter provideRecipeCardAdapter(@Named(BaseActivityModule.ACTIVITY_CONTEXT) Context context){
        return new RecipeCardAdapter(context);
    }
}
