package com.example.scratchapplication.room;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.scratchapplication.model.home.ModelRecipe;

import java.util.List;

public class RecipeRoomDBRepository {
    private RecipeDao recipeDao;
    LiveData<List<ModelRecipe>> mAllRecipes;
    LiveData<ModelRecipe> mRecipe;

    public RecipeRoomDBRepository(Application application){
        RecipeRoomDatabase db = RecipeRoomDatabase.getDatabase(application);
        recipeDao = db.recipeDao();
        mAllRecipes = recipeDao.getAllRecipes();
    }

    public LiveData<List<ModelRecipe>> getAllRecipes(){
        //Log.e("getdata", mAllRecipes.getValue().size()+"");
        return mAllRecipes;
    }
    public LiveData<ModelRecipe> getRecipeById(String rId){
        mRecipe = recipeDao.getRecipeByRid(rId);
        return mRecipe;
    }

    public void insertRecipes(List<ModelRecipe> modelRecipes){
        new InsertAsyncTask(recipeDao).execute(modelRecipes);
    }

    private static class InsertAsyncTask extends AsyncTask<List<ModelRecipe>,Void,Void>{
        private RecipeDao mAsynctaskDao;

        InsertAsyncTask(RecipeDao dao){
            mAsynctaskDao = dao;
        }

        @Override
        protected Void doInBackground(List<ModelRecipe>... lists) {
            mAsynctaskDao.insertRecipes(lists[0]);
            Log.e("insert_dao", lists[0].size()+"");
            return null;
        }
    }
}
