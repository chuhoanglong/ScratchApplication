package com.example.scratchapplication.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.scratchapplication.api.ProfileServiceRepository;
import com.example.scratchapplication.model.Profile;

public class ProfileViewModel extends AndroidViewModel {
    private RecipeRoomDBRepository recipeRoomDBRepository;
    private ProfileServiceRepository profileServiceRepository;
    private LiveData<Profile> mProfile;
    private LiveData<Profile> observable;

    private String id;
    public ProfileViewModel(@NonNull Application application) {
        super(application);
        recipeRoomDBRepository = new RecipeRoomDBRepository(application);
        profileServiceRepository = new ProfileServiceRepository(application);
    }
    public LiveData<Profile> getProfileById(String id){
        observable = profileServiceRepository.providesProfileService();
        mProfile = recipeRoomDBRepository.getProfileById(id);
        return mProfile;
    }
}
