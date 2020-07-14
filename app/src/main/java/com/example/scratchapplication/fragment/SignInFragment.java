package com.example.scratchapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.scratchapplication.MainActivity;
import com.example.scratchapplication.R;

public class SignInFragment extends Fragment {
    private Button buttonSignIn;
    private TextView signUp;
    private LinearLayout buttonSignInFB;
    private LinearLayout buttonSignInGoogle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login,container,false);
        buttonSignIn = v.findViewById(R.id.btn_signin);
        signUp = v.findViewById(R.id.idSignUp);
        buttonSignInFB = v.findViewById(R.id.btn_signin_fb);
        buttonSignInGoogle = v.findViewById(R.id.btn_signin_google);

        buttonSignInFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("CHECK", false);
                startActivity(intent);
                getActivity().finish();
            }
        });

        buttonSignInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("CHECK", false);
                startActivity(intent);
                getActivity().finish();
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("CHECK", false);
                startActivity(intent);
                getActivity().finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.login_container,new SignUpFragment()).commit();
            }
        });
        return v;
    }
}
