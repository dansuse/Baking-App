package com.dansuse.bakingapp.common;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by Daniel on 21/08/2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    @Named(BaseActivityModule.ACTIVITY_FRAGMENT_MANAGER)
    protected FragmentManager fragmentManager;

    //HasSupportFragmentInjector indicates that fragments are to participate in dagger.android injection
    //bisa diinject tanpa provide karena implements HasSupportFragmentInjector
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    //final method tidak dapat di-override by subclasses
    @Override
    public final AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    protected final void addFragment(@IdRes int containerViewId, Fragment fragment) {
        if(fragmentManager.findFragmentById(containerViewId) != null){
            fragmentManager.beginTransaction()
                    .replace(containerViewId, fragment)
                    .commit();
        }else{
            fragmentManager.beginTransaction()
                    .add(containerViewId, fragment)
                    .commit();
        }
    }
}
