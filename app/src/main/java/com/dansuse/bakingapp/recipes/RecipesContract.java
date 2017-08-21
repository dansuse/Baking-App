package com.dansuse.bakingapp.recipes;

import com.dansuse.bakingapp.BasePresenter;
import com.dansuse.bakingapp.BaseView;

/**
 * Created by LENOVO on 20/08/2017.
 */

public interface RecipesContract {
    interface View extends BaseView {
        void showText(String text);
    }

    interface Presenter extends BasePresenter{
        void klikBtn();
        void unsubscribe();
    }

}
