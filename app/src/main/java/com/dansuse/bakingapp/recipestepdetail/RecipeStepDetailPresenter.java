package com.dansuse.bakingapp.recipestepdetail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dansuse.bakingapp.common.presenter.BasePresenter;
import com.dansuse.bakingapp.data.Step;
import com.dansuse.bakingapp.recipedetail.RecipeDetailContract;
import com.google.android.exoplayer2.util.Util;

import javax.inject.Inject;

/**
 * Created by LENOVO on 23/08/2017.
 */

public class RecipeStepDetailPresenter extends BasePresenter<RecipeStepDetailContract.View>
        implements RecipeStepDetailContract.Presenter {

    private Step mStep;
    private int mSDKInt;
    private long mExoPlayerPosition;

    @Inject
    public RecipeStepDetailPresenter(RecipeStepDetailContract.View view) {
        super(view);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null && savedInstanceState.containsKey(RecipeStepDetailContract.SAVED_EXO_POSITION)){
            mExoPlayerPosition = savedInstanceState.getLong(RecipeStepDetailContract.SAVED_EXO_POSITION);
            //mView.setExoPlayerPosition(mExoPlayerPosition);
        }
    }

    @Override
    public void onStart() {
        if (mSDKInt > 23) {
            checkVideoURLThenInitExoPlayer();
        }
    }

    @Override
    public void onResume() {
        if (mSDKInt <= 23 || mView.isExoPlayerNull()) {
            checkVideoURLThenInitExoPlayer();
        }
    }

    private void checkVideoURLThenInitExoPlayer(){
        if(mStep.getVideoURL() != null && !mStep.getVideoURL().equals("")){
            mView.showExoPlayer();
            mView.initializePlayer(Uri.parse(mStep.getVideoURL()));
        }else{
            mView.hideExoPlayer();
        }
    }

    @Override
    public void onPause() {
        if (mSDKInt <= 23) {
            mView.releasePlayer();
        }
    }

    @Override
    public void onStop() {
        if (mSDKInt > 23) {
            mView.releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putLong(RecipeStepDetailContract.SAVED_EXO_POSITION, mExoPlayerPosition);
    }

    @Override
    public void onEnd() {

    }

    @Override
    public void setRecipeStep(Step step) {
        mStep = step;
    }

    @Override
    public void setSDKInt(int sdkInt) {
        mSDKInt = sdkInt;
    }

    @Override
    public void setExoPlayerPosition(long position) {
        mExoPlayerPosition = position;
    }
}
