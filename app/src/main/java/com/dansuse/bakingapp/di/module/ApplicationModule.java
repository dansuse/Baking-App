package com.dansuse.bakingapp.di.module;

import android.app.Application;
import android.content.Context;

import com.dansuse.bakingapp.BakingApplication;
import com.dansuse.bakingapp.di.ActivityScoped;
import com.dansuse.bakingapp.recipedetail.RecipeDetailActivity;
import com.dansuse.bakingapp.recipedetail.RecipeDetailActivityModule;
import com.dansuse.bakingapp.recipes.RecipesActivity;
import com.dansuse.bakingapp.recipes.RecipesModule;
import com.dansuse.bakingapp.recipestepdetail.RecipeStepDetailActivity;
import com.dansuse.bakingapp.recipestepdetail.RecipeStepDetailActivityModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Daniel on 20/08/2017.
 */

@Module(includes = {
        AndroidSupportInjectionModule.class})
public abstract class ApplicationModule {

    public static final String APPLICATION_CONTEXT = "ApplicationModule.applicationContext";

    @Binds
    @Singleton
    // Singleton annotation isn't necessary (in this case since Application instance is unique)
    // but is here for convention.
    abstract Application application(BakingApplication bakingApplication);

    @Provides
    @Named(APPLICATION_CONTEXT)
    @Singleton
    static Context provideAppLevelContext(Application application) {
        return application;
    }

    /**
     * Provides the injector for the {@link com.dansuse.bakingapp.recipes.RecipesActivity}, which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @ActivityScoped
    @ContributesAndroidInjector(modules = RecipesModule.class)
    abstract RecipesActivity recipesActivityInjector();

    @ActivityScoped
    @ContributesAndroidInjector(modules = RecipeDetailActivityModule.class)
    abstract RecipeDetailActivity recipeDetailActivityInjector();

    @ActivityScoped
    @ContributesAndroidInjector(modules = RecipeStepDetailActivityModule.class)
    abstract RecipeStepDetailActivity recipeStepDetailActivityInjector();
}
