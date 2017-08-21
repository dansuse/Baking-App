package com.dansuse.bakingapp.di.component;

import com.dansuse.bakingapp.BakingApplication;
import com.dansuse.bakingapp.di.module.ApplicationModule;
import com.dansuse.bakingapp.di.module.NetModule;
import com.dansuse.bakingapp.di.module.RecipesRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

/**
 * Created by Daniel on 20/08/2017.
 */
@Singleton
@Component(modules = {
        NetModule.class,
        ApplicationModule.class,
        RecipesRepositoryModule.class
})
public interface AppComponent extends AndroidInjector<BakingApplication> {
    //kalau interface cuma bisa extends sesama interface
    //DUGAAN, kalau class harusnya juga hanya bisa extends sesama class, dalam artian tidak bisa extends interface.
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<BakingApplication> {
    }
}
