package com.dansuse.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import static com.dansuse.bakingapp.data.source.local.RecipeDbContract.IngredientEntry;
import static com.dansuse.bakingapp.data.source.local.RecipeDbContract.RecipeEntry;

/**
 * Created by LENOVO on 25/08/2017.
 */

public class ListWidgetService extends RemoteViewsService{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    Context mContext;
    Cursor mCursor;

    public ListRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Uri FAV_RECIPE_URI = RecipeEntry.CONTENT_URI_FAVORITE_RECIPE;
        if(mCursor != null){
            mCursor.close();
        }
        mCursor = mContext.getContentResolver().query(
                FAV_RECIPE_URI,
                null,
                null,
                null,
                null
        );
        if(mCursor != null && mCursor.getCount() > 0){
            mCursor.moveToFirst();
            long id = mCursor.getLong(mCursor.getColumnIndex(RecipeEntry._ID));
            Uri uriForIngre = IngredientEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            mCursor = mContext.getContentResolver().query(uriForIngre, null, null, null, null);
            //Log.d("tes123", String.valueOf(mCursor.getCount()));
        }
    }

    @Override
    public void onDestroy() {
        if(mCursor != null){
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        if(mCursor == null){
            return 0;
        }else{
            return mCursor.getCount();
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if(mCursor == null || mCursor.getCount() == 0)return null;
        mCursor.moveToPosition(position);
        String ingredient = mCursor.getString(mCursor.getColumnIndex(IngredientEntry.COLUMN_INGREDIENT));
        String measure = mCursor.getString(mCursor.getColumnIndex(IngredientEntry.COLUMN_MEASURE));
        float quantity = mCursor.getFloat(mCursor.getColumnIndex(IngredientEntry.COLUMN_QUANTITY));

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_item);
        String textToDisplay = ingredient + " , " + String.valueOf(quantity) + " " + measure;
        views.setTextViewText(R.id.tv_widget_item, textToDisplay);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
