package com.example.scratchapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scratchapplication.R;
import com.example.scratchapplication.obj.RecipeFeed;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {
    List<RecipeFeed> recipeFeedList;
    Context mContext;
    public FeedAdapter(List<RecipeFeed> recipeFeedList, Context context){
        this.recipeFeedList = recipeFeedList;
        this.mContext = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_feed,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RecipeFeed recipeFeed = recipeFeedList.get(position);
        holder.imageViewAvatar.setImageResource(recipeFeed.getProfileAvatar());
        holder.imageViewCover.setImageResource(recipeFeed.getRecipeCover());
        if (!recipeFeed.isLiked()){
            holder.imageViewLike.setImageResource(R.drawable.ic_like);
        }
        holder.textViewProfileName.setText(recipeFeed.getProfileName());
        holder.textViewRecipeName.setText(recipeFeed.getRecipeName());
        holder.textViewRecipeDesc.setText(recipeFeed.getRecipeDescription());
        int liked = recipeFeed.getLikeCount();
        int cmted = recipeFeed.getCmtCount();
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
        return recipeFeedList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewAvatar, imageViewCover, imageViewLike;
        private TextView textViewProfileName, textViewRecipeName, textViewRecipeDesc, textViewLikeCount,textViewCmtCount;
        private Button buttonSave;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewAvatar = itemView.findViewById(R.id.image_avatar);
            imageViewCover = itemView.findViewById(R.id.image_cover);
            imageViewLike = itemView.findViewById(R.id.image_like);
            textViewProfileName = itemView.findViewById(R.id.txt_profile_name);
            textViewRecipeName = itemView.findViewById(R.id.txt_recipe_name);
            textViewRecipeDesc = itemView.findViewById(R.id.txt_recipe_desc);
            textViewLikeCount = itemView.findViewById(R.id.txt_like_count);
            textViewCmtCount = itemView.findViewById(R.id.txt_cmt_count);
            buttonSave = itemView.findViewById(R.id.btn_save);
        }
    }
}
