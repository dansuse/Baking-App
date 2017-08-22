package com.dansuse.bakingapp.common.view;

import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.dansuse.bakingapp.common.presenter.Presenter;

import javax.inject.Inject;

/**
 * Created by LENOVO on 22/08/2017.
 */
//dengan class ini kita bisa menghasilkan code yang dry (don't repeat yourself)
//isi code dalam class ini adalah line code yang umum sehingga daripada mengulang code pada setiap class view
//kita buatkan class abstract untuk pengulangan code ini.
public abstract class BaseViewFragment<T extends Presenter> extends BaseFragment implements MVPView {
    @Inject
    protected T presenter;

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        presenter.onStart(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @CallSuper
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        presenter.onEnd();
        super.onDestroyView();
    }
}
