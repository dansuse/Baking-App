package com.dansuse.bakingapp.recipedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dansuse.bakingapp.common.presenter.BasePresenter;

import javax.inject.Inject;

/**
 * Created by LENOVO on 23/08/2017.
 */

public class RecipeDetailPresenter extends BasePresenter<RecipeDetailContract.View>
implements RecipeDetailContract.Presenter{

    @Inject
    public RecipeDetailPresenter(RecipeDetailContract.View view) {
        super(view);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

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
