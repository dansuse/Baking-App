package com.dansuse.bakingapp.recipes;

import com.dansuse.bakingapp.common.view.MVPView;

/**
 * Created by LENOVO on 20/08/2017.
 */

public interface RecipesContract {
    interface View extends MVPView {
        void showText(String text);
    }

    interface Presenter extends com.dansuse.bakingapp.common.presenter.Presenter {
        void klikBtn();
        void unsubscribe();
    }

}
