package com.dansuse.bakingapp.recipes;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dansuse.bakingapp.common.presenter.BasePresenter;
import com.dansuse.bakingapp.data.Recipe;
import com.dansuse.bakingapp.data.source.RecipesRepositoryImpl;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by Daniel on 20/08/2017.
 */

public class RecipesPresenter extends BasePresenter<RecipesContract.View> implements RecipesContract.Presenter{
    RecipesRepositoryImpl mRecipesRepositoryImpl;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private ConnectivityManager mConnectivityManager;

    @Inject
    public RecipesPresenter(RecipesContract.View view, RecipesRepositoryImpl recipesRepositoryImpl, ConnectivityManager connectivityManager) {
        //untuk constructor di abstract class BasePresenter
        //bisa dipenuhi dengan pemanggilan super(view).
        //jika mau lihat errornya coba komen pemanggilan super dibawah
        super(view);
        mRecipesRepositoryImpl = recipesRepositoryImpl;
        mConnectivityManager = connectivityManager;
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
    }

    private void cacheIsDirty() {
        mRecipesRepositoryImpl.refreshRecipes();
    }

    private boolean isOnline() {
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    public void onSwipeRefresh() {
        if(isOnline()){
            cacheIsDirty();
            onStart();
        }else{
            mView.showProgressIndicator(false);
            mView.showMessageNoInternetConnection();
        }
    }

    @Override
    public void onStart() {
        //the reason why below code is commented !!
        //because, dengan selalu menjalankan query data ke repository di background thread,
        //lalu mengembalikan hasil di AndroidSchedulers.mainThread(),
        //android tidak bisa mempertahankan posisi scroll secara otomatis dari recycler view pada saat terjadi orientation changes.
        //hal ini dikarenakan .observeOn(AndroidSchedulers.mainThread()) mengandung arti
        //bahwa code yang ada di observer : public void onSuccess(@NonNull List<Recipe> recipes) dan onError()
        //akan di queue di main thread (tidak akan langsung dieksekusi)
        //padahal untuk mempertahankan scroll, data harus secepatnya dimasukkan ke adapter recycler view
        //maksimal sebelum method onResume() selesai dieksekusi

//        mCompositeDisposable.add(mRecipesRepositoryImpl.getRecipes()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<List<Recipe>>(){
//                                   @Override
//                                   public void onSuccess(@NonNull List<Recipe> recipes) {
//                                       mView.showRecyclerViewWithData(recipes);
//                                   }
//
//                                   @Override
//                                   public void onError(@NonNull Throwable e) {
//
//                                   }
//                               }
//                ));

        Single<List<Recipe>> recipeListSingle;
        mView.showProgressIndicator(true);
        if(isOnline()){
            recipeListSingle = mRecipesRepositoryImpl.getRecipes(false);
        }else{
            recipeListSingle = mRecipesRepositoryImpl.getRecipes(true);
        }
        mCompositeDisposable.add(recipeListSingle
                .subscribeWith(new DisposableSingleObserver<List<Recipe>>(){
                                   @Override
                                   public void onSuccess(@NonNull List<Recipe> recipes) {
                                       mView.showProgressIndicator(false);
                                       if(recipes.size() > 0){
                                           mView.showRecyclerViewWithData(recipes);
                                       }else{
                                           mView.showMessageNoDataAvailable();
                                       }
                                   }

                                   @Override
                                   public void onError(@NonNull Throwable e) {
                                       mView.showErrorMessage(e.getMessage());
                                   }
                               }
                ));
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onEnd() {

    }
}
