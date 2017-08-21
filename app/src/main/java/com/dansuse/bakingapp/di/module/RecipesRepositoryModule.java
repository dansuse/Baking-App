package com.dansuse.bakingapp.di.module;

import com.dansuse.bakingapp.data.source.Local;
import com.dansuse.bakingapp.data.source.RecipesDataSource;
import com.dansuse.bakingapp.data.source.Remote;
import com.dansuse.bakingapp.data.source.local.RecipesLocalDataSource;
import com.dansuse.bakingapp.data.source.remote.RecipesRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by LENOVO on 20/08/2017.
 */

@Module
public abstract class RecipesRepositoryModule {
    @Singleton
    @Local
    @Binds
    abstract RecipesDataSource provideLocalDataSource(RecipesLocalDataSource recipesLocalDataSource);

    //@Module dengan modifier abstract tidak boleh mengandung both @Provides and @Binds. Harus salah satu
//    @Singleton
//    @Local
//    @Provides
//    RecipesDataSource provideLocalDataSource(){
//        return new RecipesLocalDataSource();
//    }

    @Singleton
    @Remote
    @Binds
    abstract RecipesDataSource provideRemoteDataSource(RecipesRemoteDataSource recipesRemoteDataSource);
}
