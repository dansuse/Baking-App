package com.dansuse.bakingapp.di.module;

import android.app.Application;
import android.content.Context;

import com.dansuse.bakingapp.recipes.RecipesActivityComponent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Daniel on 20/08/2017.
 */

@Module(subcomponents = {
        RecipesActivityComponent.class})
public class ApplicationModule {
    //dapat application dari interface builder di AppComponent
    //@BindsInstance
    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }
}
