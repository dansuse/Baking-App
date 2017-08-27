package com.dansuse.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import static com.dansuse.bakingapp.data.source.local.RecipeDbContract.RecipeEntry;

/**
 * Created by LENOVO on 25/08/2017.
 */

public class RecipeWidgetService extends IntentService {

    public static final String ACTION_QUERY_FAV_RECIPE_INGREDIENT = "RecipeWidgetService.action.query_fav_recipe_ingredient";

    public RecipeWidgetService() {
        super(RecipeWidgetService.class.getSimpleName());
    }

    public static void startActionQueryFavRecipeIngredient(Context context) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ACTION_QUERY_FAV_RECIPE_INGREDIENT);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_QUERY_FAV_RECIPE_INGREDIENT.equals(action)) {
                handleActionQueryFavRecipeIngreddient();
            }
        }
    }

    private void handleActionQueryFavRecipeIngreddient() {
        Uri FAV_RECIPE_URI = RecipeEntry.CONTENT_URI_FAVORITE_RECIPE;
//        Log.d("tes123", FAV_RECIPE_URI.toString());
        Cursor cursor = getContentResolver().query(
                FAV_RECIPE_URI,
                null,
                null,
                null,
                null
        );
        String recipeName = "";
        if(cursor != null){
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                recipeName = cursor.getString(cursor.getColumnIndex(RecipeEntry.COLUMN_NAME));
            }
            cursor.close();
        }
//        List<Ingredient> ingredientList = null;
//        if (cursor != null && cursor.getCount() > 0) {
//            cursor.moveToFirst();
//            long recipeId = cursor.getLong(cursor.getColumnIndex(RecipeEntry._ID));
//            if(recipeId != INVALID_ID){
//                Uri ingredientUri = IngredientEntry.CONTENT_URI.buildUpon()
//                        .appendPath(String.valueOf(recipeId)).build();
//                Cursor ingreCursor = getContentResolver().query(ingredientUri, null, null, null, null);
//                if(ingreCursor != null && ingreCursor.getCount() > 0){
//                    ingredientList = new ArrayList<>();
//                    while(cursor.moveToNext()){
//                        String name = ingreCursor.getString(ingreCursor.getColumnIndex(IngredientEntry.COLUMN_INGREDIENT));
//                        String measure = ingreCursor.getString(ingreCursor.getColumnIndex(IngredientEntry.COLUMN_MEASURE));
//                        Float quantity = ingreCursor.getFloat(ingreCursor.getColumnIndex(IngredientEntry.COLUMN_QUANTITY));
//                        ingredientList.add(new Ingredient(quantity, measure, name));
//                    }
//                }
//                if(ingreCursor != null){
//                    ingreCursor.close();
//                }
//            }
//        }
//        if(cursor != null){
//            cursor.close();
//        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_list);

        //Now update all widgets
        //RecipeWidgetProvider.updateRecipeWidgets(this, appWidgetManager, ingredientList, appWidgetIds);
        RecipeWidgetProvider.updateRecipeWidgets(this, appWidgetManager, recipeName, appWidgetIds);
    }
}
