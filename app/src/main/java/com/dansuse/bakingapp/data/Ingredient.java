package com.dansuse.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LENOVO on 20/08/2017.
 */

public class Ingredient implements Parcelable {
    Float quantity;
    String measure;
    String ingredient;

    public Ingredient(Float quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }
//    class IngredientList{
//        List<Ingredient> mIngredientList;
//    }

    public String getIngredient() {
        return ingredient;
    }

    public Float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.ingredient);
        parcel.writeFloat(this.quantity);
        parcel.writeString(this.measure);
    }

    public Ingredient(Parcel in){
        this.ingredient = in.readString();
        this.quantity = in.readFloat();
        this.measure = in.readString();
    }
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>(){
        @Override
        public Ingredient createFromParcel(Parcel parcel) {
            return new Ingredient(parcel);
        }

        @Override
        public Ingredient[] newArray(int i) {
            return new Ingredient[i];
        }
    };
}
