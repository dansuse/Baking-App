package com.dansuse.bakingapp.recipes;

import com.dansuse.bakingapp.data.source.RecipesDataSource;
import com.dansuse.bakingapp.data.source.remote.BakingApi;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by LENOVO on 20/08/2017.
 */

@Module
public abstract class RecipesModule {

    //apabila menggunakan method abstract, maka kita bisa memilih menggunakan annotation @Provides atau @Binds

    //@Binds artinya jika ada yang meminta instance dari interface RecipesContract.View
    //maka berikan implementasi RecipesActivity
    //disini RecipesActivity sudah otomatis masuk ke dalam graph pada framework dagger yang baru
    @Binds
    abstract RecipesContract.View provideView(RecipesActivity recipesActivity);

    @Binds
    abstract RecipesContract.Presenter providePresenter(RecipesPresenter recipesPresenter);
}
