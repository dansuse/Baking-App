package com.dansuse.bakingapp.recipestepdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dansuse.bakingapp.R;
import com.dansuse.bakingapp.common.BaseActivity;
import com.dansuse.bakingapp.data.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class RecipeStepDetailActivity extends BaseActivity {

    public static final String EXTRA_SELECTED_INDEX = "RecipeStepDetailActivity.extra_selected_index";
    public static final String EXTRA_STEP_LIST = "RecipeStepDetailActivity.extra_step_list";

    private List<Step> mStepList;
    private PagerAdapter mPagerAdapter;
    private int mSelectedItemIndex;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Nullable
    @BindView(R.id.indicator)
    CircleIndicator mCircleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_SELECTED_INDEX) && intent.hasExtra(EXTRA_STEP_LIST)){
            mStepList = intent.getParcelableArrayListExtra(EXTRA_STEP_LIST);
            mSelectedItemIndex = intent.getIntExtra(EXTRA_SELECTED_INDEX, -1);
            mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(mPagerAdapter);
            if(mCircleIndicator != null){
                mCircleIndicator.setViewPager(mViewPager);
            }
            if(mSelectedItemIndex != -1){
                mViewPager.setCurrentItem(mSelectedItemIndex);
            }
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return RecipeStepDetailFragment.newInstance(mStepList.get(position));
        }

        @Override
        public int getCount() {
            return mStepList.size();
        }
    }
}
