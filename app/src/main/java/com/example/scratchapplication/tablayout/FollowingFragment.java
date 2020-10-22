package com.example.scratchapplication.tablayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scratchapplication.OtherProfileActivity;
import com.example.scratchapplication.R;
import com.example.scratchapplication.api.JsonApi;
import com.example.scratchapplication.api.RestClient;
import com.example.scratchapplication.model.Profile;
import com.example.scratchapplication.model.ProfilePojo;
import com.example.scratchapplication.model.User;
import com.example.scratchapplication.model.profile.FollowingList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingFragment extends Fragment {
    // TODO: Customize parameter argument names
    // TODO: Customize parameters
    private static final String ARG_PARAM1 = "param1";
    List<String> mFollowingList;
    RecyclerView recyclerView;
    private String uid;

    public FollowingFragment(List<String> followingList){
        this.mFollowingList = followingList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following_list, container, false);

        // Set the adapter

        recyclerView = view.findViewById(R.id.following_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        FolllowingAdapter adapter = new FolllowingAdapter(mFollowingList);
        recyclerView.setAdapter(adapter);


        return view;
    }
    class FolllowingAdapter extends RecyclerView.Adapter<FolllowingAdapter.MyViewHolder> {
        List<String> followingList;

        public FolllowingAdapter(List<String> followingList){
            this.followingList = followingList;

        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.following_list_item,parent,false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
            String uid = followingList.get(position);
            JsonApi api = RestClient.createService(JsonApi.class);
            Call<ProfilePojo> call = api.getProfile(uid);
            call.enqueue(new Callback<ProfilePojo>() {
                @Override
                public void onResponse(Call<ProfilePojo> call, Response<ProfilePojo> response) {
                    if (response.isSuccessful()){
                        Profile profile = response.body().getProfile();
                        if (!profile.getUserName().equals("")) {
                            Picasso.with(getContext()).load(profile.getAvatar()).into(holder.avatar);
                            holder.textViewName.setText(profile.getUserName());
                            holder.textViewAddress.setText(profile.getAddress());
                            String likes = profile.getLikes() > 1 ? "likes" : "like";
                            holder.textViewCount.setText(profile.getLikes() + " " + likes);
                            holder.layout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getContext(), OtherProfileActivity.class);
                                    intent.putExtra("UID", uid);
                                    getContext().startActivity(intent);
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProfilePojo> call, Throwable t) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return followingList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView textViewName,textViewAddress,textViewCount;
            CircleImageView avatar;
            RelativeLayout layout;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                avatar = itemView.findViewById(R.id.following_avatar);
                textViewName = itemView.findViewById(R.id.following_name);
                textViewAddress = itemView.findViewById(R.id.following_address);
                textViewCount = itemView.findViewById(R.id.following_count);
                layout = itemView.findViewById(R.id.layout_follow_item);
            }
        }
    }
}
