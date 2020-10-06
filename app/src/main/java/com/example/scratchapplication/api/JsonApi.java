package com.example.scratchapplication.api;

import com.example.scratchapplication.model.AllRecipe;
import com.example.scratchapplication.model.Like;
import com.example.scratchapplication.model.Save;
import com.example.scratchapplication.model.home.ModelRecipe;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonApi {

    @GET("recipes")
    Call<AllRecipe> getAllRecipes();
    @POST("recipes")
    Call<ModelRecipe> getRecipe(@Body String rId);
    @POST("recipes")
    Call<ModelRecipe> postRecipe(@Body ModelRecipe modelRecipe);
    @POST("recipes/like")
    Call<Like> postLike(@Body Like like);
    @POST("recipes/save")
    Call<Save> saveRecipe(@Body Save save);
}
