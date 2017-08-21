package com.dansuse.bakingapp.di.module;

import com.dansuse.bakingapp.data.source.Local;
import com.dansuse.bakingapp.data.source.RecipesDataSource;
import com.dansuse.bakingapp.data.source.Remote;
import com.dansuse.bakingapp.data.source.local.RecipesLocalDataSource;
import com.dansuse.bakingapp.data.source.remote.RecipesRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Daniel on 20/08/2017.
 */

@Module
public abstract class RecipesRepositoryModule {
    @Singleton
    @Local
    @Binds
    abstract RecipesDataSource provideLocalDataSource(RecipesLocalDataSource recipesLocalDataSource);

    //Error:(25, 17) error: A @Module may not contain both non-static @Provides methods and abstract @Binds or @Multibinds declarations
//    @Module dengan modifier abstract may not contain both non-static @Provides methods and abstract @Binds or @Multibinds declarations
//    @Singleton
//    @Local
//    @Provides
//    RecipesDataSource provideLocalDataSource(){
//        return new RecipesLocalDataSource();
//    }

//    kalau mau pakai annotation @Provides, maka harus memakai static method
//    @Singleton
//    @Local
//    @Provides
//    static RecipesDataSource provideLocalDataSource(){
//        return new RecipesLocalDataSource();
//    }

    @Singleton
    @Remote
    @Binds
    abstract RecipesDataSource provideRemoteDataSource(RecipesRemoteDataSource recipesRemoteDataSource);
}
