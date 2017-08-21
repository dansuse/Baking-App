package com.dansuse.bakingapp.di.module;

import com.dansuse.bakingapp.data.source.Local;
import com.dansuse.bakingapp.data.source.RecipesDataSource;
import com.dansuse.bakingapp.data.source.local.RecipesLocalDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LENOVO on 20/08/2017.
 */

@Module
public class TestModule {
    @Singleton
    @Local
    @Provides
    RecipesDataSource provideLocalDataSource(){
        return new RecipesLocalDataSource();
    }
}
