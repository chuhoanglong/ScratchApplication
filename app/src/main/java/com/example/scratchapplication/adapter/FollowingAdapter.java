package com.example.scratchapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scratchapplication.OtherProfileActivity;
import com.example.scratchapplication.R;
import com.example.scratchapplication.model.profile.FollowingList;

import java.util.List;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder> {
    private FragmentManager fragmentManager;
    private List<FollowingList> mFollowingList;
    private Context mContext;

    public FollowingAdapter(FragmentManager fragmentManager, List<FollowingList> mFollowingList, Context mContext) {
        this.fragmentManager = fragmentManager;
        this.mFollowingList = mFollowingList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public FollowingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.following_list_item,
                parent, false);
        return new FollowingViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull FollowingViewHolder holder, int position) {
        FollowingList followingList = mFollowingList.get(position);
        holder.imageAvatar.setImageResource(followingList.getImageAvatar());
        holder.name.setText(followingList.getName());
        holder.bio.setText(followingList.getBio());
        holder.numberInfo.setText(followingList.getNumberInfo());

    }


    @Override
    public int getItemCount() {
        return mFollowingList.size();
    }

    class FollowingViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageAvatar;
        private TextView name;
        private TextView bio;
        private TextView numberInfo;

        public FollowingViewHolder(@NonNull View itemView) {
            super(itemView);
            imageAvatar = itemView.findViewById(R.id.other_avatar);
            name = itemView.findViewById(R.id.other_name);
            bio = itemView.findViewById(R.id.other_bio);
            numberInfo = itemView.findViewById(R.id.other_info);
            imageAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, OtherProfileActivity.class));
                }
            });
            bio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, OtherProfileActivity.class));
                }
            });
            numberInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, OtherProfileActivity.class));
                }
            });
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, OtherProfileActivity.class));
                }
            });


        }
    }

}