package com.example.scratchapplication.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.scratchapplication.room.DirectionsModel;
import com.example.scratchapplication.room.IngredientsModel;
import com.example.scratchapplication.room.RecipeModel;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "recipe";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_recipes_table =
                String.format("CREATE TABLE %s(%s TEXT PRIMARY KEY, %s TEXT, %s TEXT)",
                        "recipes", "id", "name", "desc");
        db.execSQL(create_recipes_table);
        String create_ingredients_table =
                String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)",
                        "ingredients", "id", "rid", "ingredient");
        db.execSQL(create_ingredients_table);
        String create_directions_table =
                String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)",
                        "directions", "id", "rid", "direction");
        db.execSQL(create_directions_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_recipes_table = String.format("DROP TABLE IF EXISTS %s", "recipes");
        db.execSQL(drop_recipes_table);
        String drop_ingredients_table = String.format("DROP TABLE IF EXISTS %s", "ingredients");
        db.execSQL(drop_ingredients_table);
        String drop_directions_table = String.format("DROP TABLE IF EXISTS %s", "directions");
        db.execSQL(drop_directions_table);
        onCreate(db);
    }
    public void addRecipe(RecipeModel recipeModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",recipeModel.getRid());
        values.put("name",recipeModel.getRecipeName());
        values.put("desc",recipeModel.getRecipeDesc());

        db.insert("recipes",null,values);
        db.close();
    }
    public void addIngredients(String rid, String ingredient){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("id",recipeModel.getRid());
        values.put("rid",rid);
        values.put("desc",ingredient);

        db.insert("ingredients",null,values);
        db.close();
    }
    public void addDirections(String rid, String direction){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("rid",rid);
        values.put("direction",direction);

        db.insert("directions",null,values);
        db.close();
    }
}
