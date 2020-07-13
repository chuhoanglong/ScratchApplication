package com.example.scratchapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_login#newInstance} factory method to
 * create an instance of this fragment.
 */
import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;

public class fragment_login extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_login.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_login newInstance(String param1, String param2) {
        fragment_login fragment = new fragment_login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        //get user name
        EditText txtEmailOrUsername = v.findViewById(R.id.txtEmailOrUsername);
        final Editable userName = txtEmailOrUsername.getText();
        //get pass
        EditText txtPass = v.findViewById(R.id.txtPass);
        final Editable password = txtPass.getText();
        // get Btn
        TextView idForgotPass = v.findViewById(R.id.idForgotPass);
        final Button idSignIn = v.findViewById(R.id.idSignIn);
        TextView idSignUp = v.findViewById(R.id.idSignUp);

        idSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Login", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    public static void hideKeyboard(Activity activity) {
        View v = activity.getWindow().getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}