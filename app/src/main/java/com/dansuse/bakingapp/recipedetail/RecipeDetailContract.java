package com.dansuse.bakingapp.recipedetail;

import com.dansuse.bakingapp.common.view.MVPView;

/**
 * Created by LENOVO on 23/08/2017.
 */

public interface RecipeDetailContract {
    interface View extends MVPView {
        void showRecyclerViewWithData();
        void showErrorMessage(String errorMessage);
        void showMessageNoDataAvailable();
        //void showSnackBar(String message);
    }

    interface Presenter extends com.dansuse.bakingapp.common.presenter.Presenter {

    }
}
