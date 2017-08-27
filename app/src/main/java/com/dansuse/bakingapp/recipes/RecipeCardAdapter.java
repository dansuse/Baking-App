package com.dansuse.bakingapp.recipes;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dansuse.bakingapp.R;
import com.dansuse.bakingapp.data.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LENOVO on 22/08/2017.
 */

public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.RecipeCardViewHolder>{
    private final Context mContext;
    private final RecipeCardClickListener mRecipeCardClickListener;
    private List<Recipe> mRecipeList;

    public RecipeCardAdapter(Context context, RecipeCardClickListener recipeCardClickListener) {
        this.mContext = context;
        mRecipeCardClickListener = recipeCardClickListener;
    }

    @Override
    public RecipeCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_recipe_card, parent, false);
        return new RecipeCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeCardViewHolder holder, int position) {
        holder.bind(mRecipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecipeList == null ? 0 : mRecipeList.size();
    }

    public void replaceData(List<Recipe> recipeList){
        mRecipeList = recipeList;
        if(mRecipeList != null){
            notifyDataSetChanged();
        }
    }

    class RecipeCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Recipe recipe;

        @BindView(R.id.tv_recipe_name)
        TextView recipeNameTextView;

        @BindView(R.id.tv_recipe_servings)
        TextView recipeServingsTextView;

        @BindView(R.id.iv_recipe_picture)
        ImageView recipePictureImageView;

        RecipeCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        void bind(Recipe recipe){
            this.recipe = recipe;
            if(recipe.getName() != null && !TextUtils.isEmpty(recipe.getName())){
                recipeNameTextView.setText(recipe.getName());
            }
            if(recipe.getImage() != null && !TextUtils.isEmpty(recipe.getImage())){
                Picasso.with(mContext).load(recipe.getImage()).into(recipePictureImageView);
            }else{
                recipePictureImageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.no_image_available));
            }
            if(recipe.getServings() != null){
                recipeServingsTextView.setText(mContext.getResources().getString(R.string.servings, recipe.getServings()));
            }
        }
        @Override
        public void onClick(View view) {
            mRecipeCardClickListener.onRecipeCardClick(recipe);
        }
    }
}
