package com.dansuse.bakingapp.common.presenter;

import com.dansuse.bakingapp.common.view.MVPView;

/**
 * Created by LENOVO on 22/08/2017.
 */

//dengan class ini kita bisa menghasilkan code yang dry (don't repeat yourself)
//2 line code dalam class ini adalah line code yang umum sehingga daripada mengulang code pada setiap class presenter
//kita buatkan class abstract untuk pengulangan code ini.
//<T extends MVPView> type parameter
public abstract class BasePresenter<T extends MVPView> implements Presenter {
    protected final T mView;
    protected BasePresenter(T view) {
        this.mView = view;
    }
}
