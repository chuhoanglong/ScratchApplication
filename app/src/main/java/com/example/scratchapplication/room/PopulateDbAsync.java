package com.example.scratchapplication.room;

import android.os.AsyncTask;

public class PopulateDbAsync extends AsyncTask<Void,Void,Void> {
    private final RecipeDao mDao;

    public PopulateDbAsync(RecipeRoomDatabase db) {
        this.mDao = db.recipeDao();
    }

    @Override
    protected Void doInBackground(final Void... voids) {
        mDao.deleteAll();
        return null;
    }
}
