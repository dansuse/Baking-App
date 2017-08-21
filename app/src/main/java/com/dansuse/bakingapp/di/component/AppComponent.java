package com.dansuse.bakingapp.di.component;

import android.app.Application;

import com.dansuse.bakingapp.BakingApplication;
import com.dansuse.bakingapp.di.module.ActivityBuilder;
import com.dansuse.bakingapp.di.module.ApplicationModule;
import com.dansuse.bakingapp.di.module.NetModule;
import com.dansuse.bakingapp.di.module.RecipesRepositoryModule;
import com.dansuse.bakingapp.di.module.TestModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Daniel on 20/08/2017.
 */
@Singleton
@Component(modules = {NetModule.class,
        ApplicationModule.class,
        ActivityBuilder.class,
        AndroidSupportInjectionModule.class,
        RecipesRepositoryModule.class
//        TestModule.class
})
public interface AppComponent  {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();//ini wajib ditambahkan pada suatu component
        //nanti pada subcomponent tidak perlu ditambahkan method build lagi karena sudah inherit dari sini
    }
    //ini untuk inject DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
    void inject(BakingApplication app);
}
