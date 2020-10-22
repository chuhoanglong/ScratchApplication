package com.example.scratchapplication.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.scratchapplication.model.Profile;

import java.util.List;

@Dao
public interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProfile(Profile profile);
    @Query("SELECT * from profiles where userId = :userId")
    LiveData<Profile> getProfileById(String userId);
}