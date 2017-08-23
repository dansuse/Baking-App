package com.dansuse.bakingapp.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dansuse.bakingapp.R;
import com.dansuse.bakingapp.common.view.BaseViewFragment;
import com.dansuse.bakingapp.data.Ingredient;
import com.dansuse.bakingapp.data.Recipe;
import com.dansuse.bakingapp.data.Step;
import com.dansuse.bakingapp.recipedetail.RecipeDetailActivity;
import com.dansuse.bakingapp.ui.SpacesItemDecoration;
import com.google.common.collect.Lists;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesFragment extends BaseViewFragment<RecipesContract.Presenter>
        implements RecipesContract.View, RecipeCardClickListener {

    @Override
    public void onRecipeCardClick(List<Ingredient> ingredientList, List<Step> stepList) {
        Intent intent = new Intent(activityContext, RecipeDetailActivity.class);
        intent.putParcelableArrayListExtra(RecipeDetailActivity.EXTRA_LIST_INGREDIENT, Lists.newArrayList(ingredientList));
        intent.putParcelableArrayListExtra(RecipeDetailActivity.EXTRA_LIST_STEP, Lists.newArrayList(stepList));
        startActivity(intent);
    }

    @BindView(R.id.rv_recipe_card)
    RecyclerView mRecipeCardRecyclerView;

    @BindView(R.id.swipe_refresh_recipe_card)
    SwipeRefreshLayout mRecipeCardSwipeRefresh;

    @BindView(R.id.tv_recipe_card_no_data)
    TextView mNoDataTextView;

    @Inject
    RecipeCardAdapter mRecipeCardAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public RecipesFragment() {
        // Required empty public constructor
    }

    public static RecipesFragment newInstance() {
        RecipesFragment fragment = new RecipesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        viewUnbinder = ButterKnife.bind(this, view);
        //ingat untuk mempertahankan scroll position ketika terjadi orientation change,
        //harus minimal set layout manager pada RecyclerView di onCreateView(), tidak bisa di onViewStateRestored(Bundle savedInstanceState). COBA lihat 3 line code dibawah
//        final int numColumns = activityContext.getResources().getInteger(R.integer.num_rv_recipe_card_columns);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(activityContext, numColumns);
//        mRecipeCardRecyclerView.setLayoutManager(gridLayoutManager);
        //dan juga jangan lupa, data untuk adapter harus di masukkan secepatnya (replace data adapter) sebelum onResume() selesai dieksekusi

        initRecyclerView();
        mRecipeCardSwipeRefresh.setColorSchemeColors(
                ContextCompat.getColor(activityContext, R.color.colorPrimary),
                ContextCompat.getColor(activityContext, R.color.colorAccent),
                ContextCompat.getColor(activityContext, R.color.colorPrimaryDark));
        mRecipeCardSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onSwipeRefresh();
            }
        });

        return view;
    }

    @Override
    public void showRecyclerViewWithData(List<Recipe> recipeList) {
        mNoDataTextView.setVisibility(View.INVISIBLE);
        mRecipeCardRecyclerView.setVisibility(View.VISIBLE);
        mRecipeCardAdapter.replaceData(recipeList);
    }

    @Override
    public void showProgressIndicator(final boolean active) {
        // Make sure setRefreshing() is called after the layout is done with everything else.
        mRecipeCardSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                mRecipeCardSwipeRefresh.setRefreshing(active);
            }
        });
    }

    @Override
    public void showMessageNoInternetConnection() {
        Snackbar.make(getView(), getString(R.string.no_internet_connection), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessageNoDataAvailable() {
        mRecipeCardRecyclerView.setVisibility(View.INVISIBLE);
        mNoDataTextView.setVisibility(View.VISIBLE);
    }

    private void initRecyclerView(){
        final int numColumns = activityContext.getResources().getInteger(R.integer.num_rv_recipe_card_columns);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activityContext, numColumns);
        mRecipeCardRecyclerView.setLayoutManager(gridLayoutManager);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.rv_grid_spacing);
        mRecipeCardRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        mRecipeCardRecyclerView.setHasFixedSize(true);

        mRecipeCardRecyclerView.setAdapter(mRecipeCardAdapter);
    }

    //jika layar handphone mati, dan aku menekan tombol Run 'app' di android studio
    //maka lifecycle akan berlangsung sebagai berikut :
    //onViewStateRestored -> onStart -> onResume -> onPause -> onStop
    //dan ketika layar handphone dinyalakan
    //onStart -> onResume

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }
}
