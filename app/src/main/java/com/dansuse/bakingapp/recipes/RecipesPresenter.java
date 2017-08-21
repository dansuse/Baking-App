package com.dansuse.bakingapp.recipes;

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
 * Created by LENOVO on 20/08/2017.
 */

public class RecipesPresenter implements RecipesContract.Presenter{
    RecipesContract.View mView;
    RecipesRepository mRecipesRepository;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Inject
    public RecipesPresenter(RecipesContract.View view, RecipesRepository recipesRepository) {
        mView = view;
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
}
