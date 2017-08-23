package com.dansuse.bakingapp.recipes;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dansuse.bakingapp.common.presenter.BasePresenter;
import com.dansuse.bakingapp.data.Recipe;
import com.dansuse.bakingapp.data.source.RecipesRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by Daniel on 20/08/2017.
 */

public class RecipesPresenter extends BasePresenter<RecipesContract.View> implements RecipesContract.Presenter{
    RecipesRepository mRecipesRepository;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Inject
    public RecipesPresenter(RecipesContract.View view, RecipesRepository recipesRepository) {
        //untuk constructor di abstract class BasePresenter
        //bisa dipenuhi dengan pemanggilan super(view).
        //jika mau lihat errornya coba komen pemanggilan super dibawah
        super(view);
        mRecipesRepository = recipesRepository;
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
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

//        mCompositeDisposable.add(mRecipesRepository.getRecipes()
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

        mCompositeDisposable.add(mRecipesRepository.getRecipes()
                .subscribeWith(new DisposableSingleObserver<List<Recipe>>(){
                                   @Override
                                   public void onSuccess(@NonNull List<Recipe> recipes) {
                                       mView.showRecyclerViewWithData(recipes);
                                   }

                                   @Override
                                   public void onError(@NonNull Throwable e) {

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
