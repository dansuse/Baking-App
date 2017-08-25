package com.dansuse.bakingapp.recipestepdetail;

import android.net.Uri;

import com.dansuse.bakingapp.common.view.MVPView;
import com.dansuse.bakingapp.data.Step;

/**
 * Created by LENOVO on 23/08/2017.
 */

public interface RecipeStepDetailContract {
    String SAVED_EXO_POSITION = "RecipeStepDetailContract.saved_exo_position";
    interface View extends MVPView {
        void showMessageNoInternetConnection();
        void showErrorMessage(String errorMessage);
        void showMessageNoDataAvailable();
        void showExoPlayer();
        void hideExoPlayer();
        void initializePlayer(Uri mediaUri);
        void releasePlayer();
        boolean isExoPlayerNull();
        void setExoPlayerPosition(long position);
    }

    interface Presenter extends com.dansuse.bakingapp.common.presenter.Presenter {
        void setRecipeStep(Step step);
        void setSDKInt(int sdkInt);
        void setExoPlayerPosition(long position);
        void onStop();
    }
}
