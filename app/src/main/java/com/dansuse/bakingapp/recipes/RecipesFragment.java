package com.dansuse.bakingapp.recipes;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dansuse.bakingapp.R;
import com.dansuse.bakingapp.common.view.BaseViewFragment;
import com.dansuse.bakingapp.data.Recipe;
import com.dansuse.bakingapp.ui.SpacesItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesFragment extends BaseViewFragment<RecipesContract.Presenter> implements RecipesContract.View {

    @BindView(R.id.rv_recipe_card)
    RecyclerView mRecipeCardRecyclerView;

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
        return view;
    }

    @Override
    public void showRecyclerViewWithData(List<Recipe> recipeList) {
        mRecipeCardAdapter.replaceData(recipeList);
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

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }
}
