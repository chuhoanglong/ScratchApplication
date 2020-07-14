package com.example.scratchapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.scratchapplication.fragment.SignUpFragment;

public class SignUpActivity extends AppCompatActivity {
    private Fragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fragment = new SignUpFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.signUp_container,fragment).commit();
    }
}
