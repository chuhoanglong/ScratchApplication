package com.example.scratchapplication.room;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.scratchapplication.model.Profile;
import com.example.scratchapplication.model.home.ModelRecipe;

import java.util.List;

public class RecipeRoomDBRepository {
    private RecipeDao recipeDao;
    private ProfileDao profileDao;
    LiveData<List<ModelRecipe>> mAllRecipes;
    LiveData<ModelRecipe> mRecipe;
    LiveData<Profile> mProfile;

    public RecipeRoomDBRepository(Application application){
        RecipeRoomDatabase db = RecipeRoomDatabase.getDatabase(application);
        recipeDao = db.recipeDao();
        mAllRecipes = recipeDao.getAllRecipes();

    }

    public LiveData<Profile> getProfileById(String id){
        mProfile = profileDao.getProfileById(id);
        return mProfile;
    }

    public void insertProfiles(List<Profile> profiles){
        new InsertProfileAsyncTask(profileDao).execute(profiles);
    }

    public LiveData<List<ModelRecipe>> getAllRecipes(){
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
            return null;
        }
    }
    private static class InsertProfileAsyncTask extends AsyncTask<List<Profile>, Void, Void>{
        private ProfileDao mAsyncTaskDao;

        InsertProfileAsyncTask(ProfileDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }

        @Override
        protected Void doInBackground(List<Profile>... lists) {
            mAsyncTaskDao.insertProfiles(lists[0]);
            return null;
        }
    }
}
