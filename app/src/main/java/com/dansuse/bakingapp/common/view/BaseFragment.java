package com.dansuse.bakingapp.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by Daniel on 21/08/2017.
 */

public abstract class BaseFragment extends Fragment {
    //disini @Inject harusnya ada @Named annotation atau @Qualifier
    //karena aku ada provide 2 context dengan scoped berbeda,
    //tapi belum aku tambahkan @Named annotation atau @Qualifier.

    //tapi ketika project di rebuild kok nda error ya ? TERNYATA ERROR(kayaknya tadi android studionya nyantol)
    //oleh karena itu aku mau coba ngecek hashcode dari context disini
    //dengan hashcode context level application dan level activity
    @Inject
    @Named(BaseActivityModule.ACTIVITY_CONTEXT)
    protected Context activityContext;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    private Unbinder viewUnbinder;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        viewUnbinder = ButterKnife.bind(this, getView());
    }

    @Override
    public void onDestroyView() {
        if(viewUnbinder != null){
            viewUnbinder.unbind();
        }
        super.onDestroyView();
    }
}
