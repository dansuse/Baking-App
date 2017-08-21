package com.dansuse.bakingapp.data.source;

import com.dansuse.bakingapp.data.Recipe;
import com.google.common.collect.Lists;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Daniel on 20/08/2017.
 */
//ini ditambahkan agar instance ini hanya ada satu
//mengapa ditambahkan disini dan bukan di modul, karena tidak ada method untuk provide atau bind instance ini
//instance ini masuk ke graph lewat annotation inject di constructor, BACA KOMEN DI BAWAH
@Singleton
public class RecipesRepository implements RecipesDataSource {
    private final RecipesDataSource mRecipesRemoteDataSource;
    private final RecipesDataSource mRecipesLocalDataSource;

    Map<Integer, Recipe> mCachedRecipes;
    boolean mCacheIsDirty = false;

    //karena suatu dependency bisa dibuat dari dependency lainnya,
    //seperti contoh dibawah dimana RecipesRepository bisa dibuat dari
    //LocalDataSource dan RemoteDataSource,
    //maka tidak perlu kita tulis secara eksplisit berupa method @Provides / @Binds di module
    //, cukup hanya dengan menambahkan annotation @Inject, maka akan tersedia di graph dependency kita
    //NB : hanya berlaku untuk injeksi constructor
    @Inject
    RecipesRepository(@Remote RecipesDataSource recipesRemoteDataSource,
                      @Local RecipesDataSource recipesLocalDataSource) {
        mRecipesRemoteDataSource = recipesRemoteDataSource;
        mRecipesLocalDataSource = recipesLocalDataSource;
    }

    private List<Recipe> getRecipeList(){
        return Lists.newArrayList(mCachedRecipes.values());
    }

    @Override
    public Single<List<Recipe>> getRecipes() {
        if (mCachedRecipes != null && !mCacheIsDirty) {
            //baris dibawah nda bisa karena Lists.newArrayList(mCachedRecipes.values()) tipenya Single<ArrayList<Recipe>>
            //padahal return type dari method ini adalah Single<List<Recipe>>
            //return Single.just(Lists.newArrayList(mCachedRecipes.values()));

            //oleh karena itu diakali seperti di bawah ini
            return Single.fromCallable(new Callable<List<Recipe>>() {
                @Override
                public List<Recipe> call() throws Exception {
                    return getRecipeList();
                }
            });
            //jangan memanggil Single.just, karena dengan Single.just kita tidak akan memiliki kesempatan untuk menentukan
            //suatu operasi akan berjalan di thread mana
            //return Single.just(getRecipeList());
        } else if (mCachedRecipes == null) {
            mCachedRecipes = new LinkedHashMap<>();
        }
        Single<List<Recipe>> remoteRecipes = getAndSaveRemoteRecipes();
        return remoteRecipes;
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
                mCacheIsDirty = false;
                return recipes;
            }
        });
    }
}
