package com.dansuse.bakingapp.recipedetail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dansuse.bakingapp.R;
import com.dansuse.bakingapp.common.view.BaseViewFragment;
import com.dansuse.bakingapp.data.Ingredient;
import com.dansuse.bakingapp.data.Step;
import com.google.common.collect.Lists;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends BaseViewFragment<RecipeDetailContract.Presenter>
        implements RecipeDetailContract.View, RecipeDetailClickListener {

    @Override
    public void onRecipeDetailClick(int selectedIndex) {
        mRecipeDetailActivityFragmentInteraction.onRecipeDetailClickCallActivity(selectedIndex);
    }

    @BindView(R.id.tv_recipe_detail_no_data)
    TextView mNoDataTextView;

    @BindView(R.id.rv_recipe_detail)
    RecyclerView mRecipeDetailRecyclerView;

    @Inject
    RecipeDetailAdapter mRecipeDetailAdapter;

    @Inject
    RecipeDetailActivityFragmentInteraction mRecipeDetailActivityFragmentInteraction;

    private List<Ingredient>mIngredientList;
    private List<Step>mStepList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LIST_INGREDIENT = "arg_list_ingredient";
    private static final String ARG_LIST_STEP = "arg_list_step";

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    public static RecipeDetailFragment newInstance(List<Ingredient> ingredientList, List<Step> stepList) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_LIST_INGREDIENT, Lists.newArrayList(ingredientList));
        args.putParcelableArrayList(ARG_LIST_STEP, Lists.newArrayList(stepList));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tes123 frag", String.valueOf(getArguments() != null));
        if (getArguments() != null && getArguments().containsKey(ARG_LIST_INGREDIENT) && getArguments().containsKey(ARG_LIST_STEP)) {
            mIngredientList = getArguments().getParcelableArrayList(ARG_LIST_INGREDIENT);
            mStepList = getArguments().getParcelableArrayList(ARG_LIST_STEP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        viewUnbinder = ButterKnife.bind(this, view);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activityContext);
        mRecipeDetailRecyclerView.setLayoutManager(linearLayoutManager);

        mRecipeDetailRecyclerView.setAdapter(mRecipeDetailAdapter);
        mRecipeDetailAdapter.replaceData(mIngredientList, mStepList);
    }

    @Override
    public void showRecyclerViewWithData() {

    }

    @Override
    public void showErrorMessage(String errorMessage) {

    }

    @Override
    public void showMessageNoDataAvailable() {

    }
}
