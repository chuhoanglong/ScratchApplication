package com.example.scratchapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scratchapplication.R;
import com.example.scratchapplication.obj.MyProfileRecipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{
    List<MyProfileRecipe> myProfileRecipeList;
    private List<MyProfileRecipe> mRecipeItems;

    public RecipeAdapter(List<MyProfileRecipe> mRecipeItems) {
        this.mRecipeItems = mRecipeItems;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item,
                parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        MyProfileRecipe myProfileRecipe = mRecipeItems.get(position);
        holder.recipeImageView.setImageResource(myProfileRecipe.getImageRecipe());
        holder.recipeTextView.setText(myProfileRecipe.getNameRecipe());
    }

    @Override
    public int getItemCount() {
        return mRecipeItems.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        ImageView recipeImageView;
        TextView recipeTextView;
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImageView = itemView.findViewById(R.id.img_recipe);
            recipeTextView = itemView.findViewById(R.id.tv_recipe);
        }
    }
}
