package com.example.scratchapplication.fragment.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scratchapplication.CreateRecipeActivity;
import com.example.scratchapplication.R;
import com.example.scratchapplication.adapter.FeedAdapter;
import com.example.scratchapplication.api.JsonApi;
import com.example.scratchapplication.api.RestClient;
import com.example.scratchapplication.dialog.BottomSheetFilter;
import com.example.scratchapplication.model.ListRecipes;
import com.example.scratchapplication.model.Profile;
import com.example.scratchapplication.model.ProfilePojo;
import com.example.scratchapplication.model.home.ModelRecipe;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //View
    private RecyclerView recyclerView;
    private Button buttonAdd;
    private ImageView imageViewFilter;

    private FeedAdapter adapter;

    private List<ModelRecipe> modelRecipeList;
    private List<String> listFilter;
    private boolean filterFollow,orderByLike;

    public HomeFragment(List<String> listFilter,boolean filterFollow, boolean orderByLike) {
        this.listFilter = listFilter;
        this.filterFollow = filterFollow;
        this.orderByLike = orderByLike;
        Log.e("FILTER", this.listFilter.size()+" "+this.filterFollow+" "+this.orderByLike);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = v.findViewById(R.id.recyclerview_feed);
        //adapter
        modelRecipeList = new ArrayList<>();
        String uid =FirebaseAuth.getInstance().getCurrentUser().getUid();
        adapter = new FeedAdapter(container.getContext(),modelRecipeList,uid,new ArrayList<>(),new ArrayList<>());
        //layout
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        layout.setReverseLayout(true);
        layout.setStackFromEnd(true);
        //set rv
        recyclerView.setLayoutManager(layout);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);

        JsonApi service = RestClient.createService(JsonApi.class);
        //call recipes data
        Call<ListRecipes> call = service.getAllRecipes();
        call.enqueue(new Callback<ListRecipes>() {
            @Override
            public void onResponse(Call<ListRecipes> call, Response<ListRecipes> response) {
                if (!response.isSuccessful()){
                    Log.e("Code recipes call ",response.code()+"");
                    return;
                }
                ListRecipes allRecipe = response.body();
                modelRecipeList = allRecipe.getData();
                adapter = new FeedAdapter(getContext(),modelRecipeList,uid,new ArrayList<>(),new ArrayList<>());
                recyclerView.setAdapter(adapter);
                //call profile data
                Call<ProfilePojo> profileCall = service.getProfile(uid);
                profileCall.enqueue(new Callback<ProfilePojo>() {
                    @Override
                    public void onResponse(Call<ProfilePojo> call, Response<ProfilePojo> response) {
                        if (!response.isSuccessful()){
                            Log.e("Code profileCall ",response.code()+" "+ call.request().url().toString());
                            return;
                        }
                        Profile profile = response.body().getProfile();
                        //sap xep
                        if (orderByLike){
                            Collections.sort(modelRecipeList,new ModelRecipe());
                        }
                        //loc theo doi
                        if (filterFollow){
                            if (profile.getFollows().size()==0){
                                Toast.makeText(getContext(), "Hiện tại bạn chưa follow người dùng nào", Toast.LENGTH_SHORT).show();
                                filterFollow = false;
                            }else {
                                Iterator<ModelRecipe> iterator = modelRecipeList.iterator();
                                while (iterator.hasNext()){
                                    if (!profile.getFollows().contains(iterator.next().getuId())){
                                        iterator.remove();
                                    }
                                }
                            }
                        }
                        //loc chu de
                        if (listFilter.size()>0){
                            Iterator<ModelRecipe> iterator = modelRecipeList.iterator();
                            while (iterator.hasNext()) {
                                boolean check = false;
                                for (String filter : listFilter) {
                                    if (iterator.next().getFilters().contains(filter))
                                        check = true;
                                }
                                if (!check)
                                    iterator.remove();
                            }
                        }
                        Log.e("FeedListSize", modelRecipeList.size()+"");
                        adapter = new FeedAdapter(getContext(),modelRecipeList,uid,profile.getFollows(),profile.getSaves());
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<ProfilePojo> call, Throwable t) {
                        Log.e("Fail profile call ",t.getMessage());
                    }
                });
            }
            @Override
            public void onFailure(Call<ListRecipes> call, Throwable t) {
                Log.e("Fail recipes call: ",t.getMessage());
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
        //filter
        imageViewFilter = v.findViewById(R.id.image_filter);
        imageViewFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFilter bottomSheetFilter = new BottomSheetFilter(listFilter,filterFollow,orderByLike);
                bottomSheetFilter.show(getFragmentManager(),"bottomSheetFilter");
            }
        });

        return v;
    }
}