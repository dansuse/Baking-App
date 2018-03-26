package com.dansuse.bakingapp.data.source;

import android.content.Context;

import com.dansuse.bakingapp.data.Recipe;
import com.dansuse.bakingapp.di.module.ApplicationModule;
import com.google.common.collect.Lists;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Daniel on 20/08/2017.
 */
//ini ditambahkan agar instance ini hanya ada satu
//mengapa ditambahkan disini dan bukan di modul, karena tidak ada method untuk provide atau bind instance ini
//instance ini masuk ke graph lewat annotation inject di constructor, BACA KOMEN DI BAWAH
@Singleton
public class RecipesRepositoryImpl implements RecipesRepository {
    private final RecipesDataSource mRecipesRemoteDataSource;
    private final RecipesDataSource mRecipesLocalDataSource;

    Map<Integer, Recipe> mCachedRecipes;
    boolean mCacheIsDirty = true;

    Context mContext;

    //karena suatu dependency bisa dibuat dari dependency lainnya,
    //seperti contoh dibawah dimana RecipesRepositoryImpl bisa dibuat dari
    //LocalDataSource dan RemoteDataSource,
    //maka tidak perlu kita tulis secara eksplisit berupa method @Provides / @Binds di module
    //, cukup hanya dengan menambahkan annotation @Inject, maka akan tersedia di graph dependency kita
    //NB : hanya berlaku untuk injeksi constructor
    @Inject
    RecipesRepositoryImpl(@Remote RecipesDataSource recipesRemoteDataSource,
                          @Local RecipesDataSource recipesLocalDataSource,
                          @Named(ApplicationModule.APPLICATION_CONTEXT) Context context) {
        mRecipesRemoteDataSource = recipesRemoteDataSource;
        mRecipesLocalDataSource = recipesLocalDataSource;
        mContext = context;
        mCachedRecipes = new LinkedHashMap<>();
    }

    private List<Recipe> getRecipeList(){
        return Lists.newArrayList(mCachedRecipes.values());
    }

    @Override
    public Single<List<Recipe>> getRecipes(boolean fromCacheOnly) {
        if ((!mCacheIsDirty && mCachedRecipes.size() > 0) || fromCacheOnly) {
            //baris dibawah nda bisa karena Lists.newArrayList(mCachedRecipes.values()) tipenya Single<ArrayList<Recipe>>
            //padahal return type dari method ini adalah Single<List<Recipe>>
            //return Single.just(Lists.newArrayList(mCachedRecipes.values()));

            //oleh karena itu diakali seperti di bawah ini
//            return Single.fromCallable(new Callable<List<Recipe>>() {
//                @Override
//                public List<Recipe> call() throws Exception {
//                    return getRecipeList();
//                }
//            });
            //coba lihat penjelasan di RecipesPresenter.java tentang mengapa return statement diatas di comment
            return Single.just(getRecipeList());
            //jangan memanggil Single.just, karena dengan Single.just kita tidak akan memiliki kesempatan untuk menentukan
            //suatu operasi akan berjalan di thread mana
            //return Single.just(getRecipeList());
        }
        Single<List<Recipe>> remoteRecipes = getAndSaveRemoteRecipes();
        return remoteRecipes.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private Single<List<Recipe>> getAndCacheLocalRecipes() {
//        return mRecipesLocalDataSource.getTasks()
//                .flatMap(new Func1<List<Task>, Observable<List<Task>>>() {
//                    @Override
//                    public Observable<List<Task>> call(List<Task> tasks) {
//                        return Observable.from(tasks)
//                                .doOnNext(task -> mCachedTasks.put(task.getId(), task))
//                                .toList();
//                    }
//                });
        return null;
    }

    private Single<List<Recipe>> getAndSaveRemoteRecipes() {
//        Single<List<Recipe>> singleListRecipe = mRecipesRemoteDataSource.getRecipes();
//        singleListRecipe.subscribe(new Consumer<List<Recipe>>() {
//            @Override
//            public void accept(List<Recipe> recipes) throws Exception {
//                Observable.fromIterable(recipes).doOnNext(new Consumer<Recipe>() {
//                    @Override
//                    public void accept(Recipe recipe) throws Exception {
//                        mCachedRecipes.put(recipe.getId(), recipe);
//                    }
//                });
//            }
//        });
//        mCacheIsDirty = false;
//        return singleListRecipe;

        return mRecipesRemoteDataSource.getRecipes().map(new Function<List<Recipe>, List<Recipe>>() {
            @Override
            public List<Recipe> apply(@NonNull List<Recipe> recipes) throws Exception {
                //coba tanyakan di one on one, apakah foreach seperti ini sudah memenuhi prinsip reactive
                for(Recipe recipe : recipes){
                    mCachedRecipes.put(recipe.getId(), recipe);
                }
                //===untuk ngetes apakah aplikasi sudah mempertahankan posisi scroll===
//                for(Recipe recipe : recipes){
//                    mCachedRecipes.put(recipe.getId() + 4, recipe);
//                }
//                for(Recipe recipe : recipes){
//                    mCachedRecipes.put(recipe.getId() + 8, recipe);
//                }
//                List<Recipe>temp = Lists.newArrayList(recipes);
//                recipes.addAll(temp);
//                recipes.addAll(temp);
                //============
                mCacheIsDirty = false;
                return recipes;
            }
        });
    }

    @Override
    public void refreshRecipes() {
        mCacheIsDirty = true;
        mCachedRecipes.clear();
    }
}
