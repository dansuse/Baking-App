package com.dansuse.bakingapp.recipes;

import com.dansuse.bakingapp.common.view.MVPView;
import com.dansuse.bakingapp.data.Recipe;

import java.util.List;

/**
 * Created by LENOVO on 20/08/2017.
 */

public interface RecipesContract {
    interface View extends MVPView {
        void showRecyclerViewWithData(List<Recipe> recipeList);
        void showProgressIndicator(boolean active);
        void showMessageNoInternetConnection();
        void showErrorMessage(String errorMessage);
        void showMessageNoDataAvailable();
    }

    interface Presenter extends com.dansuse.bakingapp.common.presenter.Presenter {
        void onSwipeRefresh();
        void unsubscribe();
    }
}
