package com.dansuse.bakingapp.recipes;

import android.support.v4.app.Fragment;

import com.dansuse.bakingapp.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;

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
}
