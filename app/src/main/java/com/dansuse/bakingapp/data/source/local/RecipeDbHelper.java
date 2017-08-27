package com.dansuse.bakingapp.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LENOVO on 25/08/2017.
 */

public class RecipeDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "recipes.db";
    private static final int DATABASE_VERSION = 1;
    public RecipeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(RecipeDbContract.RecipeEntry.SQL_CREATE_RECIPES_TABLE);
        sqLiteDatabase.execSQL(RecipeDbContract.IngredientEntry.SQL_CREATE_INGREDIENTS_TABLE);
        sqLiteDatabase.execSQL(RecipeDbContract.StepEntry.SQL_CREATE_STEPS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
