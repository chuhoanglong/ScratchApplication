package com.example.scratchapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.scratchapplication.MainActivity;
import com.example.scratchapplication.R;

public class SignUpFragment extends Fragment {

    private Button buttonSignUp;
    private TextView login;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_up,container,false);
            buttonSignUp = v.findViewById(R.id.btn_signUp);
            buttonSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("CHECK", false);
                    startActivity(intent);
                }
            });

            login = v.findViewById(R.id.idSignIn);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
        return v;
    }
}
