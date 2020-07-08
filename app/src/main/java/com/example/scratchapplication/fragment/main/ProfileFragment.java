package com.example.scratchapplication.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scratchapplication.CreateRecipeActivity;
import com.example.scratchapplication.EditProfileActivity;
import com.example.scratchapplication.R;
import com.example.scratchapplication.SettingsActivity;
import com.example.scratchapplication.adapter.RecipeAdapter;
import com.example.scratchapplication.obj.MyProfileRecipe;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Ahihi";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final int REP_LIST_ITEMS = 100;
    private RecipeAdapter recipeAdapter;
    private RecyclerView recyclerView;
    private Button button;
    private ImageView imageView;
    private TextView textView;
    public ProfileFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SmsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        List<MyProfileRecipe> myProfileRecipeList = new ArrayList<>();
        for (int i=0; i<10; i++){
            myProfileRecipeList.add(new MyProfileRecipe( R.drawable.img1, "Food " + i));
        }

        recyclerView = v.findViewById(R.id.rv_my_recipes);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setHasFixedSize(true);
        recipeAdapter = new RecipeAdapter(myProfileRecipeList);
        recyclerView.setAdapter(recipeAdapter);
        button = v.findViewById(R.id.settings);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProfileFragment.this.getActivity(), SettingsActivity.class);
                    startActivity(intent);
                }
            });

        imageView = v.findViewById(R.id.edit_profile);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), EditProfileActivity.class));
                }
            });



        return v;

    }


}


