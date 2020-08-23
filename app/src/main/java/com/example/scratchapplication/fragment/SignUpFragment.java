package com.example.scratchapplication.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.scratchapplication.MainActivity;
import com.example.scratchapplication.R;
import com.example.scratchapplication.adapter.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class SignUpFragment extends Fragment {

    private Button buttonSignUp;
    private TextView login;
    private EditText txtFullName;
    private EditText txtEmailSignUp;
    private EditText txtPassSignUp;
    private FirebaseAuth mAuth;
    // Initialize Firebase Auth


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_sign_up,container,false);

            txtFullName = v.findViewById(R.id.txtFullName);
            txtEmailSignUp = v.findViewById(R.id.txtEmailSignUp);
            txtPassSignUp = v.findViewById(R.id.txtPassSignUp);

            buttonSignUp = v.findViewById(R.id.btn_signUp);
            buttonSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = txtFullName.getText().toString();
                    String email = txtEmailSignUp.getText().toString();
                    String password = txtPassSignUp.getText().toString();

                    createAccount(name, email, password);

//                    Intent intent = new Intent(getContext(), MainActivity.class);
//                    intent.putExtra("CHECK", false);
//                    startActivity(intent);
//                    getActivity().finish();
                }
            });

            login = v.findViewById(R.id.idSignIn);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.login_container, new SignInFragment())
                            .commit();
                }
            });
        return v;
    }

    private void createAccount(final String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .setPhotoUri(Uri.parse("https://kansai-resilience-forum.jp/wp-content/uploads/2019/02/IAFOR-Blank-Avatar-Image-1.jpg"))
                                    .build();
                            user.updateProfile(profileChangeRequest)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("UPDATEPROFILE", "User profile updated.");
                                            }
                                        }
                                    });
                            String uid = user.getUid();

                            ArrayList followers = new ArrayList();
                            User dataUser = new User(name, "","",0, followers);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("users");
                            myRef.child(uid).setValue(dataUser);
                            getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.login_container, new SignInFragment())
                                        .commit();
                        } else {
                            // If sign in fails, display a message to the user.

                        }

                        // ...
                    }
                });
    }
}
