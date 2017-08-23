package com.dansuse.bakingapp.recipes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

import com.dansuse.bakingapp.common.BaseActivityModule;
import com.dansuse.bakingapp.di.ActivityScoped;
import com.dansuse.bakingapp.di.FragmentScoped;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Daniel on 20/08/2017.
 */

@Module(includes = BaseActivityModule.class)
public abstract class RecipesModule {

    //apabila menggunakan method abstract, maka kita bisa memilih menggunakan :
    // static method dengan annotation @Provides atau
    // abstract method dengan annotation @Binds

    //@Binds artinya jika ada yang meminta instance dari interface RecipesContract.View
    //maka berikan implementasi RecipesActivity
    //disini RecipesActivity sudah otomatis masuk ke dalam graph pada framework dagger yang baru
    @Binds
    @ActivityScoped
    abstract AppCompatActivity provideAppCompatActivity(RecipesActivity recipesActivity);

    //========================================================
    //karena RecipesPresenter membutuhkan dependency view di constructor
    //dependency yang dibutuhkan harus terletak di scope yang sama
    //kalau tidak akan muncul error
    /*
    Error:(24, 3) error: com.dansuse.bakingapp.di.module.ApplicationModule_RecipesActivityInjector.RecipesActivitySubcomponent scoped with @com.dansuse.bakingapp.di.ActivityScoped may not reference bindings with different scopes:
@Binds @com.dansuse.bakingapp.di.FragmentScoped com.dansuse.bakingapp.recipes.RecipesContract.View com.dansuse.bakingapp.recipes.RecipesModule.provideView(com.dansuse.bakingapp.recipes.RecipesActivity)
     */
    //selain itu juga harus ada dalam satu class module antara presenter dengan view
    //jika beda module, maka module yang lain harus di-include
//    @Binds
//    @ActivityScoped
//    abstract RecipesContract.Presenter providePresenter(RecipesPresenter recipesPresenter);
//=====================================================================================


    //======================================================================
    //ketika interface dengan concrete implementation tidak cocok pada annotation @Binds, maka akan keluar error
    //Error:(37, 35) error: @Binds methods must have only one parameter whose type is assignable to the return type
//    @Binds
//    @FragmentScoped
//    abstract RecipesContract.View provideView(RecipesActivity recipesActivity);
    //namun anehnya tidak terjadi error walaupun presenter diinject di Fragment, namun view dan presenter di module memiliki scope ActivityScoped,
    //dimana view dan presenter berada pada module activity dan bukan pada module untuk fragment
//======================================================================

    @ActivityScoped
    @Provides
    static ConnectivityManager provideConnectivityManager(@Named(BaseActivityModule.ACTIVITY_CONTEXT)Context context){
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @FragmentScoped
    @ContributesAndroidInjector(modules = RecipesFragmentModule.class)
    abstract RecipesFragment recipesFragmentInjector();
}
