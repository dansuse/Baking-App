package com.dansuse.bakingapp.common;


import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.dansuse.bakingapp.di.ActivityScoped;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by LENOVO on 21/08/2017.
 */

/**
 * Provides base activity dependencies. This must be included in all activity modules, which must
 * provide a concrete implementation of {@link Activity}.
 */
@Module
public abstract class BaseActivityModule {
    static final String ACTIVITY_FRAGMENT_MANAGER = "BaseActivityModule.activityFragmentManager";
    static final String ACTIVITY_CONTEXT = "BaseActivityModule.activityContext";

    @Binds
    @Named(ACTIVITY_CONTEXT)
    @ActivityScoped
    abstract Context activityContext(AppCompatActivity activity);

    @Provides
    @Named(ACTIVITY_FRAGMENT_MANAGER)
    @ActivityScoped
    static android.support.v4.app.FragmentManager activityFragmentManager(AppCompatActivity activity) {
        return activity.getSupportFragmentManager();
    }
}
