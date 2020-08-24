package com.example.scratchapplication.fragment.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scratchapplication.CreateRecipeActivity;
import com.example.scratchapplication.R;
import com.example.scratchapplication.adapter.FeedAdapter;
import com.example.scratchapplication.adapter.User;
import com.example.scratchapplication.model.RecipeCreate;
import com.example.scratchapplication.model.home.RecipeFeed;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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

    private DatabaseReference mDatabaseRef;
    private List<RecipeFeed> recipeFeedsList;
    List<User> users;

    public HomeFragment() {

    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
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

        recipeFeedsList = new ArrayList<>();


//        for (int i = 0; i<10; i++){
//            recipeFeedsList.add(
//                    new RecipeFeed(
//                            i+"",i+"",
//                            "https://kansai-resilience-forum.jp/wp-content/uploads/2019/02/IAFOR-Blank-Avatar-Image-1.jpg",
//                            "User "+i,
//                            "https://www.bbcgoodfood.com/sites/default/files/recipe-collections/collection-image/2013/05/chorizo-mozarella-gnocchi-bake-cropped.jpg",
//                            "Food "+ i, "Description " +i,
//                            1, 10,
//                            false, false
//                    ));
//        }



        //get a reference to recyclerview
        recyclerView = v.findViewById(R.id.recyclerview_feed);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        layout.setReverseLayout(true);
        recyclerView.setLayoutManager(layout);
        recyclerView.setHasFixedSize(false);
        final FeedAdapter adapter = new FeedAdapter(recipeFeedsList,getContext());
        recyclerView.setAdapter(adapter);


        mDatabaseRef = FirebaseDatabase .getInstance().getReference("recipes");
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot:snapshot.getChildren()){
                    RecipeCreate recipe  = postSnapshot.getValue(RecipeCreate.class);
                    //recipe
                    RecipeFeed recipeFeed = new RecipeFeed(
                            postSnapshot.getKey(),
                            recipe.getpId(),
                            recipe.getProfileAvatar(),
                            recipe.getProfileName(),
                            recipe.getUrlCover().toString(),
                            recipe.getName(),
                            recipe.getDescription(),
                            0,
                            0,
                            false,
                            false);
                    recipeFeed.setIngredients(recipe.getIngredients());
                    recipeFeed.setDirections(recipe.getDirections());
                    recipeFeedsList.add(recipeFeed);
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(recipeFeedsList.size()-1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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