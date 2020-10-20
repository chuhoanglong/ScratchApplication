package com.example.scratchapplication.api;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.scratchapplication.api.JsonApi;
import com.example.scratchapplication.api.RestClient;
import com.example.scratchapplication.model.ListRecipes;
import com.example.scratchapplication.model.home.ModelRecipe;
import com.example.scratchapplication.room.RecipeRoomDBRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebServiceRepository {
    Application application;

    public WebServiceRepository(Application application) {
        this.application = application;
    }

    List<ModelRecipe> webserviceResponseList = new ArrayList<>();

    public LiveData<List<ModelRecipe>> providesWebservice(){
        final MutableLiveData<List<ModelRecipe>> data = new MutableLiveData<>();
        try {
            JsonApi service = RestClient.createService(JsonApi.class);
            service.getAllRecipes().enqueue(new Callback<ListRecipes>() {
                @Override
                public void onResponse(Call<ListRecipes> call, Response<ListRecipes> response) {
                    if (!response.isSuccessful()){
                        Log.e("Code",response.code()+"");
                        return;
                    }
                    webserviceResponseList = response.body().getData();
                    data.setValue(webserviceResponseList);
                    RecipeRoomDBRepository roomDBRepository = new RecipeRoomDBRepository(application);
                    if (webserviceResponseList.size()>0)
                        roomDBRepository.insertRecipes(webserviceResponseList);
                }

                @Override
                public void onFailure(Call<ListRecipes> call, Throwable t) {
                    Log.e("failWSR", t.getMessage());
                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return data;
    }
}
