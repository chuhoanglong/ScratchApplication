package com.example.scratchapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.scratchapplication.fragment.SignInFragment;

public class LoginActivity extends AppCompatActivity {

    private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fragment = new SignInFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.login_container,fragment).commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}