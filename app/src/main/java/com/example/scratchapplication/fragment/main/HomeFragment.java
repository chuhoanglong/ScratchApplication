package com.example.scratchapplication.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scratchapplication.CreateRecipeActivity;
import com.example.scratchapplication.R;
import com.example.scratchapplication.adapter.FeedAdapter;
import com.example.scratchapplication.api.JsonApi;
import com.example.scratchapplication.api.RestClient;
import com.example.scratchapplication.model.AllRecipe;
import com.example.scratchapplication.model.home.ModelRecipe;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //View
    private RecyclerView recyclerView;
    private Button buttonAdd;
    private ImageView imageViewFilter;

    private FeedAdapter adapter;

    private List<ModelRecipe> modelRecipeList;

    public HomeFragment() {
    }
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = v.findViewById(R.id.recyclerview_feed);
        modelRecipeList = new ArrayList<>();
        adapter = new FeedAdapter(container.getContext(),modelRecipeList);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        layout.setReverseLayout(true);
        layout.setStackFromEnd(true);
        recyclerView.setLayoutManager(layout);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);

        JsonApi service = RestClient.createService(JsonApi.class);

        Call<AllRecipe> call = service.getAllRecipes();
        call.enqueue(new Callback<AllRecipe>() {
            @Override
            public void onResponse(Call<AllRecipe> call, Response<AllRecipe> response) {
                if (!response.isSuccessful()){
                    Log.e("Code: ",response.code()+"");
                    return;
                }
                AllRecipe allRecipe = response.body();
                modelRecipeList = allRecipe.getData();
                Log.d("List", modelRecipeList.size()+"");
                adapter = new FeedAdapter(getContext(),modelRecipeList);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<AllRecipe> call, Throwable t) {
                Log.e("Fail: ",t.getMessage());
            }
        });
        //button add
        buttonAdd =(Button) v.findViewById(R.id.btn_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateRecipeActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }
}