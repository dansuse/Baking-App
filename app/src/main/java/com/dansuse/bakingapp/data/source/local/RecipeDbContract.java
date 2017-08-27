package com.dansuse.bakingapp.data.source.local;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by LENOVO on 25/08/2017.
 */

public class RecipeDbContract {
    public static final String AUTHORITY = "com.dansuse.bakingapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_RECIPES = "recipes";
    public static final String PATH_INGREDIENTS = "ingredients";
    public static final String PATH_STEPS = "steps";

    public static final long INVALID_ID = -1;

    public static final class RecipeEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPES).build();

        static final String PATH_FAV_RECIPE = "favorite";
        public static final Uri CONTENT_URI_FAVORITE_RECIPE =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPES).appendPath(PATH_FAV_RECIPE).build();

        public static final String TABLE_NAME = "recipes";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SERVINGS = "servings";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_IS_FAVORITE = "isFavorite";

        static final String SQL_CREATE_RECIPES_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_SERVINGS + " INTEGER NOT NULL, " +
                COLUMN_IMAGE + " TEXT NOT NULL, " +
                COLUMN_IS_FAVORITE + " INTEGER NOT NULL DEFAULT 0 " +
                ");";
    }
    public static final class IngredientEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENTS).build();

        public static final String TABLE_NAME = "ingredients";
        public static final String COLUMN_INGREDIENT = "ingredient";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_MEASURE = "measure";
        public static final String COLUMN_RECIPE_ID = "recipeID";

        static final String SQL_CREATE_INGREDIENTS_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_INGREDIENT + " TEXT NOT NULL, " +
                COLUMN_QUANTITY + " REAL NOT NULL, " +
                COLUMN_MEASURE + " TEXT NOT NULL, " +
                COLUMN_RECIPE_ID   + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + COLUMN_RECIPE_ID + ") REFERENCES " + RecipeEntry.TABLE_NAME + "(" + _ID + ")" +
                ");";
    }
    public static final class StepEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_STEPS).build();

        public static final String TABLE_NAME = "steps";
        public static final String COLUMN_SHORT_DESCRIPTION = "shortDescription";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_VIDEO_URL = "videoURL";
        public static final String COLUMN_THUMBNAIL_URL = "thumbnailURL";
        public static final String COLUMN_RECIPE_ID = "recipeID";

        public static final String SQL_CREATE_STEPS_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_SHORT_DESCRIPTION + " TEXT NOT NULL, " +
                COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                COLUMN_VIDEO_URL + " TEXT NOT NULL, " +
                COLUMN_THUMBNAIL_URL + " TEXT NOT NULL, " +
                COLUMN_RECIPE_ID   + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + COLUMN_RECIPE_ID + ") REFERENCES " + RecipeEntry.TABLE_NAME + "(" + _ID + ")" +
                ");";
    }
}
