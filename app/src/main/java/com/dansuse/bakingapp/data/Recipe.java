package com.dansuse.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 20/08/2017.
 */

public class Recipe implements Parcelable {
    Integer id;
    String name;
    Integer servings;
    String image;

    //Ingredient.IngredientList ingredients;
    //Step.StepList steps;
    List<Ingredient> ingredients;
    List<Step> steps;

    public Recipe(Integer id, String name, Integer servings, String image) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(servings);
        parcel.writeString(image);
        parcel.writeTypedList(ingredients);
        parcel.writeTypedList(steps);
    }

    public Recipe(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.servings = in.readInt();
        this.image = in.readString();
        List<Ingredient>ingredientList = new ArrayList<>();
        in.readTypedList(ingredientList, Ingredient.CREATOR);
        this.ingredients = ingredientList;

        List<Step>stepList = new ArrayList<>();
        in.readTypedList(stepList, Step.CREATOR);
        this.steps = stepList;
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>(){
        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int i) {
            return new Recipe[i];
        }
    };
}
