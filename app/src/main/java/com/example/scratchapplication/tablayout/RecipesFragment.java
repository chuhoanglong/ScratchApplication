package com.example.scratchapplication.tablayout;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scratchapplication.R;
import com.example.scratchapplication.ViewRecipeActivity;
import com.example.scratchapplication.api.JsonApi;
import com.example.scratchapplication.api.RestClient;
import com.example.scratchapplication.model.ListRecipes;
import com.example.scratchapplication.model.home.ModelRecipe;
import com.example.scratchapplication.model.recipe.RecipeCreate;
import com.example.scratchapplication.model.profile.MyProfileRecipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 */
public class RecipesFragment extends Fragment {

    private RecyclerView recyclerView;
    private String uid;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipesFragment(String uid) {
        this.uid = uid;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_list, container, false);

        // Set the adapter
        JsonApi api = RestClient.createService(JsonApi.class);

        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setHasFixedSize(true);

        Call<ListRecipes> call = api.getProfileRecipes(uid);
        call.enqueue(new Callback<ListRecipes>() {
            @Override
            public void onResponse(Call<ListRecipes> call, Response<ListRecipes> response) {
                if (response.isSuccessful()){
                    List<ModelRecipe> modelRecipes = response.body().getData();
                    recyclerView.setAdapter(new MyRecipeAdapter(modelRecipes));
                }
                else {
                    Log.e("Code",response.code()+" "+call.request().url().toString());
                }
            }

            @Override
            public void onFailure(Call<ListRecipes> call, Throwable t) {

            }
        });
/*
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("recipes");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    List<RecipeCreate> recipes = new ArrayList<>();
                    List<String> keys = new ArrayList<>();
                    for (DataSnapshot d:snapshot.getChildren()){
                        RecipeCreate recipe = d.getValue(RecipeCreate.class);
                        if (recipe.getpId().equals(uid)) {
                            keys.add(d.getKey());
                            recipes.add(recipe);
                        }
                    }
                    recyclerView.setAdapter(new MyRecipeAdapter(recipes,keys));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
 */

        return view;
    }
    class MyRecipeAdapter extends RecyclerView.Adapter<MyRecipeAdapter.MyViewHolder> {
        private List<ModelRecipe> modelRecipes;

        public MyRecipeAdapter(List<ModelRecipe> modelRecipes) {
            this.modelRecipes = modelRecipes;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item,parent,false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
            ModelRecipe modelRecipe = modelRecipes.get(position);
            Picasso.with(getContext()).load(modelRecipe.getUrlCover()).into(holder.imageView);
            holder.textView.setText(modelRecipe.getName());

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ViewRecipeActivity.class);
                    intent.putExtra("RID",modelRecipe.getrId());
                    getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return modelRecipes.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
                private ImageView imageView;
                private TextView textView;
                public MyViewHolder(@NonNull View itemView) {
                    super(itemView);
                    imageView = itemView.findViewById(R.id.img_my_recipe);
                    textView = itemView.findViewById(R.id.tv_my_recipe);
                }
            }
    }
}