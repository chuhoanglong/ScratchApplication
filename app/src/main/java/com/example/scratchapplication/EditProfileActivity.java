package com.example.scratchapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.preference.PreferenceManager;

public class EditProfileActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getString(R.string.pref_full_name_key))) {
            sharedPreferences.getString(key,getString(R.string.pref_full_name_default));
        }
        if(key.equals(getString(R.string.pref_bio_key))) {
            sharedPreferences.getString(key,getString(R.string.pref_bio_default));
        }
        if(key.equals(getString(R.string.pref_email_key))) {
            sharedPreferences.getString(key, getString(R.string.pref_email_default));
        }
        if(key.equals(getString(R.string.pref_phone_key))) {
            sharedPreferences.getString(key, getString(R.string.pref_phone_default));
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}
