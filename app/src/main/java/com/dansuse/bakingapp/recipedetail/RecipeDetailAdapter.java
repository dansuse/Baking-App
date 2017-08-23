package com.dansuse.bakingapp.recipedetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dansuse.bakingapp.R;
import com.dansuse.bakingapp.data.Ingredient;
import com.dansuse.bakingapp.data.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LENOVO on 23/08/2017.
 */

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ITEM_RECIPE_STEP = 0;
    private static final int VIEW_TYPE_ITEM_RECIPE_INGREDIENT = 1;

    private List<Step> mStepList;
    private List<Ingredient>mIngredientList;

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return VIEW_TYPE_ITEM_RECIPE_INGREDIENT;
        }else{
            return VIEW_TYPE_ITEM_RECIPE_STEP;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == VIEW_TYPE_ITEM_RECIPE_INGREDIENT){
            View view = layoutInflater.inflate(R.layout.item_recipe_ingredient, parent, false);
            return new RecipeIngredientViewHolder(view);
        }else if(viewType == VIEW_TYPE_ITEM_RECIPE_STEP){
            View view = layoutInflater.inflate(R.layout.item_recipe_step, parent, false);
            return new RecipeStepViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof RecipeStepViewHolder){
            RecipeStepViewHolder castHolder = (RecipeStepViewHolder) holder;
            castHolder.bind(mStepList.get(position - 1));
        }else if(holder instanceof RecipeIngredientViewHolder){
            RecipeIngredientViewHolder castHolder = (RecipeIngredientViewHolder)holder;
            castHolder.bind(mIngredientList);
        }
    }

    @Override
    public int getItemCount() {
        if(mIngredientList == null && mStepList == null){
            return 0;
        }else if(mIngredientList == null){
            return mStepList.size();
        }else if(mStepList == null){
            return 1;
        }else{
            return mStepList.size() + 1;
        }
    }

    public void replaceIngredientData(List<Ingredient>ingredientList){
        mIngredientList = ingredientList;
        notifyDataSetChanged();
    }

    public void replaceData(List<Ingredient>ingredientList, List<Step>stepList){
        mIngredientList = ingredientList;
        mStepList = stepList;
        notifyDataSetChanged();
    }

    public void replaceStepData(List<Step>stepList){
        mStepList = stepList;
        notifyDataSetChanged();
    }

    class RecipeStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tv_item_recipe_step)
        TextView itemRecipeStepTextView;

        public RecipeStepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        void bind(Step step){
            itemRecipeStepTextView.setText(step.getShortDescription());
        }
        @Override
        public void onClick(View view) {

        }
    }
    class RecipeIngredientViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_item_recipe_ingredient)
        TextView itemRecipeIngredientTextView;

        public RecipeIngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        void bind(List<Ingredient> ingredientList){
            itemRecipeIngredientTextView.setText("Ingredients : ");
            for (Ingredient i : ingredientList) {
                String text = "\n" + i.getIngredient() + " " + String.valueOf(i.getQuantity()) + " " + i.getMeasure();
                itemRecipeIngredientTextView.append(text);
            }
        }
    }
}
