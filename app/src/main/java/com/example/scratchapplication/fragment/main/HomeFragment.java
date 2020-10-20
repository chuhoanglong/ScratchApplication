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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scratchapplication.AllMessageActivity;
import com.example.scratchapplication.CreateRecipeActivity;
import com.example.scratchapplication.MainActivity;
import com.example.scratchapplication.R;
import com.example.scratchapplication.adapter.FeedAdapter;
import com.example.scratchapplication.api.JsonApi;
import com.example.scratchapplication.api.RestClient;
import com.example.scratchapplication.dialog.BottomSheetFilter;
import com.example.scratchapplication.model.ListRecipes;
import com.example.scratchapplication.model.Profile;
import com.example.scratchapplication.model.ProfilePojo;
import com.example.scratchapplication.model.home.ModelRecipe;
import com.example.scratchapplication.room.RecipesViewModel;
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
    private ImageView imageViewAllMessages;

    private FeedAdapter adapter;

    //private List<ModelRecipe> modelRecipeList;
    private List<String> listFilter;
    private boolean filterFollow,orderByLike;

    private RecipesViewModel recipesViewModel;

    public HomeFragment(List<String> listFilter,boolean filterFollow, boolean orderByLike) {
        this.listFilter = listFilter;
        this.filterFollow = filterFollow;
        this.orderByLike = orderByLike;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        imageViewAllMessages = v.findViewById(R.id.image_messages);
        imageViewAllMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllMessageActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = v.findViewById(R.id.recyclerview_feed);
        String uid =FirebaseAuth.getInstance().getCurrentUser().getUid();
        adapter = new FeedAdapter(container.getContext(),new ArrayList<>(),uid,new ArrayList<>(),new ArrayList<>());
        //layout
        LinearLayoutManager layout = new LinearLayoutManager(getContext());

        //set rv
        recyclerView.setLayoutManager(layout);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);
        //recipesViewModel = new RecipesViewModel()
        recipesViewModel = ViewModelProviders.of(getActivity()).get(RecipesViewModel.class);
        recipesViewModel.getAllRecipes().observe(getActivity(), new Observer<List<ModelRecipe>>() {
            @Override
            public void onChanged(List<ModelRecipe> modelRecipes) {
                if (modelRecipes.size()>0){
                    //call profile data
                    JsonApi service = RestClient.createService(JsonApi.class);
                    Call<ProfilePojo> profileCall = service.getProfile(uid);
                    profileCall.enqueue(new Callback<ProfilePojo>() {
                        @Override
                        public void onResponse(Call<ProfilePojo> call, Response<ProfilePojo> response) {
                            if (!response.isSuccessful()){
                                Log.e("Code profileCall ",response.code()+" "+ call.request().url().toString());
                                return;
                            }
                            List<ModelRecipe> list = new ArrayList<>(modelRecipes);
                            Profile profile = response.body().getProfile();
                            //sap xep
                            if (orderByLike){
                                Collections.sort(list,new ModelRecipe());
                            }
                            else list = new ArrayList<>(modelRecipes);
                            //loc theo doi
                            if (filterFollow){
                                if (profile.getFollows().size()==0){
                                    Toast.makeText(getContext(), "Hiện tại bạn chưa follow người dùng nào", Toast.LENGTH_SHORT).show();
                                    filterFollow = false;
                                }else {
                                    Iterator<ModelRecipe> iterator = list.iterator();
                                    while (iterator.hasNext()){
                                        if (!profile.getFollows().contains(iterator.next().getUid())){
                                            iterator.remove();
                                        }
                                    }
                                }
                            }
                            //loc chu de
                            if (listFilter.size()>0){
                                Iterator<ModelRecipe> iterator = list.iterator();
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
                            adapter = new FeedAdapter(getContext(),list,uid,profile.getFollows(),profile.getSaves());
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<ProfilePojo> call, Throwable t) {
                            Log.e("Fail profile call ",t.getMessage());
                            Toast.makeText(getContext(), "Không có kết nối internet", Toast.LENGTH_SHORT).show();
                            adapter = new FeedAdapter(getContext(),modelRecipes,uid,new ArrayList<>(),new ArrayList<>());
                            recyclerView.setAdapter(adapter);
                        }
                    });
//                    adapter = new FeedAdapter(getContext(),modelRecipes,uid,new ArrayList<>(),new ArrayList<>());
//                    recyclerView.setAdapter(adapter);
                }
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