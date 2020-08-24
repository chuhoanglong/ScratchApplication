package com.example.scratchapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scratchapplication.OtherProfileActivity;
import com.example.scratchapplication.R;
import com.example.scratchapplication.ViewRecipeActivity;
import com.example.scratchapplication.model.home.RecipeFeed;
import com.example.scratchapplication.room.IngredientsModel;
import com.example.scratchapplication.room.RecipeModel;
import com.example.scratchapplication.sql.DatabaseHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {
    List<RecipeFeed> recipeFeedsList;
    Context mContext;
    public FeedAdapter(List<RecipeFeed> recipeList, Context context){
        this.recipeFeedsList = recipeList;
        this.mContext = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_feed,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final RecipeFeed recipeFeed = recipeFeedsList.get(position);

        Picasso.with(mContext)
                .load(recipeFeed.getProfileAvatar())
                .into(holder.imageViewAvatar);
        holder.textViewProfileName.setText(recipeFeed.getProfileName());

        Picasso.with(mContext)
                .load(recipeFeed.getRecipeCover())
                .into(holder.imageViewCover);
        Log.e("IMAGE",recipeFeed.getRecipeCover());
        //Toast.makeText(mContext, recipeFeed.getRecipeCover(), Toast.LENGTH_SHORT).show();
        holder.textViewRecipeName.setText(recipeFeed.getRecipeName());
        holder.textViewRecipeDesc.setText(recipeFeed.getRecipeDescription());

        final boolean isLiked = recipeFeed.isLiked();

        final int liked = recipeFeed.getLikeCount();
        final int cmted = recipeFeed.getCmtCount();

        if (isLiked){
            holder.imageViewLike.setImageResource(R.drawable.ic_liked);
        }
        else {
            holder.imageViewLike.setImageResource(R.drawable.ic_like);
        }
        holder.imageViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLiked){
                    recipeFeed.setLiked(true);
                    recipeFeed.setLikeCount(liked+1);
                    notifyItemChanged(position);
                }
                else {

                    recipeFeed.setLiked(false);
                    recipeFeed.setLikeCount(liked-1);
                    notifyItemChanged(position);
                }
            }
        });


        if (liked>1){
            holder.textViewLikeCount.setText(liked + " likes");
        }
        else{
            holder.textViewLikeCount.setText(liked + " like");
        }
        if (cmted>1){
            holder.textViewCmtCount.setText(cmted + " comments");
        }
        else{
            holder.textViewCmtCount.setText(cmted + " comment");
        }

    }

    @Override
    public int getItemCount() {
        return recipeFeedsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout layoutTittle;
        private ImageView imageViewAvatar, imageViewCover, imageViewLike;
        private TextView textViewProfileName, textViewRecipeName, textViewRecipeDesc, textViewLikeCount,textViewCmtCount;
        private Button buttonSave;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewAvatar = itemView.findViewById(R.id.image_avatar);
            imageViewCover = itemView.findViewById(R.id.image_cover);
            imageViewCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ViewRecipeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("RID",recipeFeedsList.get(getAdapterPosition()).getrId());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
            imageViewLike = itemView.findViewById(R.id.image_like);

            textViewProfileName = itemView.findViewById(R.id.txt_profile_name);
            textViewRecipeName = itemView.findViewById(R.id.txt_recipe_name);
            textViewRecipeName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ViewRecipeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("RID",recipeFeedsList.get(getAdapterPosition()).getrId());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
            textViewRecipeDesc = itemView.findViewById(R.id.txt_recipe_desc);
            textViewLikeCount = itemView.findViewById(R.id.txt_like_count);
            textViewCmtCount = itemView.findViewById(R.id.txt_cmt_count);
            buttonSave = itemView.findViewById(R.id.btn_save);
            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Save")
                            .setMessage("Save to cookbook?")
                            .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseHandler databaseHandler = new DatabaseHandler(mContext);
                                    RecipeFeed recipeFeed = recipeFeedsList.get(getAdapterPosition());
                                    RecipeModel recipeModel =
                                            new RecipeModel(
                                                    recipeFeed.getrId(),
                                                    recipeFeed.getRecipeName(),
                                                    recipeFeed.getRecipeDescription());
                                    databaseHandler.addRecipe(recipeModel);
                                    for (String ingredient:recipeFeed.getIngredients()){
                                        databaseHandler.addIngredients(recipeFeed.getrId(),ingredient);
                                    }
                                    for (String direction:recipeFeed.getDirections()){
                                        databaseHandler.addIngredients(recipeFeed.getrId(),direction);
                                    }
                                    Toast.makeText(mContext, "Saved "+recipeModel.getRecipeName(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }
            });
            textViewProfileName = itemView.findViewById(R.id.txt_profile_name);
            layoutTittle = itemView.findViewById(R.id.title_bar);
            layoutTittle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, OtherProfileActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("UID",recipeFeedsList.get(getAdapterPosition()).getuId());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });


        }
    }
}
