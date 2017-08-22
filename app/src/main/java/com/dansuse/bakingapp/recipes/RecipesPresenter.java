package com.dansuse.bakingapp.recipes;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dansuse.bakingapp.common.presenter.BasePresenter;
import com.dansuse.bakingapp.data.Recipe;
import com.dansuse.bakingapp.data.source.RecipesRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

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
    public void klikBtn() {
        mCompositeDisposable.add(mRecipesRepository.getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Recipe>>(){
                    @Override
                    public void onSuccess(@NonNull List<Recipe> recipes) {
                        mView.showText(String.valueOf(recipes.get(3).getIngredients().get(0).getIngredient()));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                }
                ));
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void onStart(@Nullable Bundle savedInstanceState) {

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
