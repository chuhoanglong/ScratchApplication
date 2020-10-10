package com.example.scratchapplication.api;

import com.example.scratchapplication.fragment.Follow;
import com.example.scratchapplication.model.ListRecipes;
import com.example.scratchapplication.model.Like;
import com.example.scratchapplication.model.ProfilePojo;
import com.example.scratchapplication.model.RecipePojo;
import com.example.scratchapplication.model.Save;
import com.example.scratchapplication.model.home.ModelRecipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonApi {

    @GET("home/recipes")
    Call<ListRecipes> getAllRecipes();
    @POST("home/recipes")
    Call<ModelRecipe> getRecipe(@Body String rId);
    @POST("home/recipes")
    Call<ModelRecipe> postRecipe(@Body ModelRecipe modelRecipe);
    @POST("home/recipes/like")
    Call<Like> postLike(@Body Like like);
    @POST("home/recipes/save")
    Call<Save> saveRecipe(@Body Save save);
    @GET("profile/info")
    Call<ProfilePojo> getProfile(@Query("uId")String uid);
    @POST("home/recipes/detail")
    Call<RecipePojo> getRecipeDetail(@Body Rid rid);
    @GET("profile/recipes")
    Call<ListRecipes> getProfileRecipes(@Query("uId")String uid);
    @POST("home/recipes/follow")
    Call<Follow> postFollow(@Body Follow follow);

    class Rid {
        @SerializedName("rId")
        @Expose
        private String rId;

        public Rid(String rId) {
            this.rId = rId;
        }
    }
}
