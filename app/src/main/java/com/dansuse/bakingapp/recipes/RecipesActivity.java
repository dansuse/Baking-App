package com.dansuse.bakingapp.recipes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.dansuse.bakingapp.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class RecipesActivity extends AppCompatActivity implements RecipesContract.View{

//    @BindView(R.id.btn_klik_me)
//    Button mKlikMeButton;

    @BindView(R.id.tv_text)
    TextView mTextView;

    @OnClick(R.id.btn_klik_me)
    public void setKlikMeButton(Button button){
        mRecipesPresenter.klikBtn();
    }

    @Inject
    RecipesContract.Presenter mRecipesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);
    }

    @Override
    public void showText(String text) {
        mTextView.setText(text);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRecipesPresenter.unsubscribe();
    }
}
