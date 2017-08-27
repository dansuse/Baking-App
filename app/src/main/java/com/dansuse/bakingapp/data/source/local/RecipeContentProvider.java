package com.dansuse.bakingapp.data.source.local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.dansuse.bakingapp.data.source.local.RecipeDbContract.AUTHORITY;
import static com.dansuse.bakingapp.data.source.local.RecipeDbContract.IngredientEntry;
import static com.dansuse.bakingapp.data.source.local.RecipeDbContract.PATH_INGREDIENTS;
import static com.dansuse.bakingapp.data.source.local.RecipeDbContract.PATH_RECIPES;
import static com.dansuse.bakingapp.data.source.local.RecipeDbContract.PATH_STEPS;
import static com.dansuse.bakingapp.data.source.local.RecipeDbContract.RecipeEntry;
import static com.dansuse.bakingapp.data.source.local.RecipeDbContract.StepEntry;

/**
 * Created by LENOVO on 25/08/2017.
 */

public class RecipeContentProvider extends ContentProvider {

    public static final int CODE_RECIPE = 100;
    public static final int CODE_RECIPE_WITH_ID = 101;
    public static final int CODE_FAVORITE_RECIPE = 102;
    public static final int CODE_INGREDIENT = 200;
    public static final int CODE_INGREDIENT_BY_RECIPE_ID = 201;
    public static final int CODE_STEP = 300;
    public static final int CODE_STEP_BY_RECIPE_ID = 301;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private RecipeDbHelper mRecipeDbHelper;

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AUTHORITY;
        matcher.addURI(authority, PATH_RECIPES, CODE_RECIPE);
        matcher.addURI(authority, PATH_RECIPES + "/#", CODE_RECIPE_WITH_ID);
        matcher.addURI(authority, PATH_RECIPES + "/" + RecipeEntry.PATH_FAV_RECIPE, CODE_FAVORITE_RECIPE);
        matcher.addURI(authority, PATH_INGREDIENTS + "/#", CODE_INGREDIENT_BY_RECIPE_ID);
        matcher.addURI(authority, PATH_STEPS + "/#", CODE_STEP_BY_RECIPE_ID);
        matcher.addURI(authority, PATH_INGREDIENTS, CODE_INGREDIENT);
        matcher.addURI(authority, PATH_STEPS, CODE_STEP);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mRecipeDbHelper = new RecipeDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int match = sUriMatcher.match(uri);
        SQLiteDatabase database = mRecipeDbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (match){
            case CODE_RECIPE:
                cursor = database.query(RecipeEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                break;
            case CODE_FAVORITE_RECIPE:
                String mSelection = RecipeEntry.COLUMN_IS_FAVORITE + " = 1";
                cursor = database.query(RecipeEntry.TABLE_NAME, projection, mSelection, selectionArgs, null, null, sortOrder);
                break;
            case CODE_INGREDIENT_BY_RECIPE_ID:
                String recipeId = uri.getPathSegments().get(1);
                mSelection = IngredientEntry.COLUMN_RECIPE_ID + " = ?";
                String[] mSelectionArgs = new String[]{recipeId};
                cursor = database.query(IngredientEntry.TABLE_NAME, projection, mSelection, mSelectionArgs, null, null, sortOrder);
                break;
            case CODE_STEP_BY_RECIPE_ID:
                recipeId = uri.getPathSegments().get(1);
                mSelection = StepEntry.COLUMN_RECIPE_ID + " = ?";
                mSelectionArgs = new String[]{recipeId};
                cursor = database.query(StepEntry.TABLE_NAME, projection, mSelection, mSelectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("URI not recognized " + uri.toString());
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mRecipeDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case CODE_INGREDIENT_BY_RECIPE_ID:
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(IngredientEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    //getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;
            case CODE_STEP_BY_RECIPE_ID:
                db.beginTransaction();
                rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(StepEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    //getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        int match = sUriMatcher.match(uri);
        SQLiteDatabase database = mRecipeDbHelper.getWritableDatabase();
        long id;
        Uri returnUri = null;
        switch (match){
            case CODE_RECIPE:
                id = database.insert(RecipeEntry.TABLE_NAME, null, contentValues);
                if(id > 0){
                    returnUri = ContentUris.withAppendedId(RecipeEntry.CONTENT_URI, id);
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                break;
            default:
                throw new UnsupportedOperationException("URI not recognized");
        }
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        SQLiteDatabase db = mRecipeDbHelper.getWritableDatabase();
        int rowsDeleted = 0;
        switch (match){
            case CODE_RECIPE:
//                String id = uri.getPathSegments().get(1);
//                String mSelection = "_id=?";
//                String[] mSelectionArgs = new String[]{id};
                rowsDeleted = db.delete(RecipeEntry.TABLE_NAME, selection, selectionArgs);
                if(rowsDeleted > 0){
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                break;
            case CODE_INGREDIENT:
//                String recipeId = uri.getPathSegments().get(1);
//                mSelection = IngredientEntry.COLUMN_RECIPE_ID + "=?";
//                mSelectionArgs = new String[]{recipeId};
                rowsDeleted = db.delete(IngredientEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CODE_STEP:
//                recipeId = uri.getPathSegments().get(1);
//                mSelection = StepEntry.COLUMN_RECIPE_ID + "=?";
//                mSelectionArgs = new String[]{recipeId};
                rowsDeleted = db.delete(StepEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("URI not recognized");
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        SQLiteDatabase db = mRecipeDbHelper.getWritableDatabase();
        int rowsUpdated = 0;
        switch (match){
            case CODE_RECIPE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = RecipeEntry._ID + " = ?";
                String[] mSelectionArgs = new String[]{id};
                rowsUpdated = db.update(RecipeEntry.TABLE_NAME, contentValues, mSelection, mSelectionArgs);
                if(rowsUpdated > 0){
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                break;
            default:
                throw new UnsupportedOperationException("URI not recognized");
        }
        return rowsUpdated;
    }
}
