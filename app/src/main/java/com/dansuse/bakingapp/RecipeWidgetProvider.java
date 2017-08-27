package com.dansuse.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static RemoteViews getGardenGridRemoteView(Context context){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);

        Intent intent = new Intent(context, ListWidgetService.class);
        views.setRemoteAdapter(R.id.appwidget_list, intent);

        views.setEmptyView(R.id.appwidget_list, R.id.appwidget_text);
        return views;
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, String recipeName,
                                int appWidgetId) {

        // Construct the RemoteViews object
        //RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_item);
        RemoteViews views = getGardenGridRemoteView(context);
        views.setTextViewText(R.id.tv_widget_recipe_name, recipeName);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
//        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
//        }
        RecipeWidgetService.startActionQueryFavRecipeIngredient(context);
    }

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager,
                                           String recipeName, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipeName, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

