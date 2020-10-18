package com.example.scratchapplication.room;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.scratchapplication.model.home.ModelRecipe;

import java.util.List;

public class RecipesViewModel extends AndroidViewModel {

    private RecipeRoomDBRepository recipeRoomDBRepository;
    private LiveData<List<ModelRecipe>> mAllRecipes;
    private LiveData<ModelRecipe> mRecipe;
    WebServiceRepository webServiceRepository;
    private final LiveData<List<ModelRecipe>> retroObservable;
    public RecipesViewModel(Application application){
        super(application);
        recipeRoomDBRepository = new RecipeRoomDBRepository(application);
        webServiceRepository = new WebServiceRepository(application);
        retroObservable = webServiceRepository.providesWebservice();
        mAllRecipes = recipeRoomDBRepository.getAllRecipes();

    }

    public LiveData<List<ModelRecipe>> getAllRecipes(){
        return mAllRecipes;
    }
    public LiveData<ModelRecipe> getRecipeById(String rId){
        mRecipe = recipeRoomDBRepository.getRecipeById(rId);
        return mRecipe;
    }
}
