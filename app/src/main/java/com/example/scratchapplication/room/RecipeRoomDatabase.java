package com.example.scratchapplication.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.scratchapplication.model.home.ModelRecipe;

@Database(entities = {ModelRecipe.class},version = 1)
public abstract class RecipeRoomDatabase extends RoomDatabase {
    public  abstract RecipeDao recipeDao();

    private static RecipeRoomDatabase INSTANCE;

    static RecipeRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (RecipeRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),RecipeRoomDatabase.class,"recipe_databse")
                                                    .addCallback(roomDatabaseCallBack)
                                                    .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback roomDatabaseCallBack = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };
}
