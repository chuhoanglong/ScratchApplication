package com.example.scratchapplication.fragment.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.scratchapplication.R;
import com.example.scratchapplication.adapter.FeedAdapter;
import com.example.scratchapplication.adapter.ProfileViewPagerAdapter;
import com.example.scratchapplication.api.JsonApi;
import com.example.scratchapplication.api.RestClient;
import com.example.scratchapplication.fragment.Follow;
import com.example.scratchapplication.model.Profile;
import com.example.scratchapplication.model.ProfilePojo;
import com.example.scratchapplication.model.User;
import com.example.scratchapplication.tablayout.FollowingFragment;
import com.example.scratchapplication.tablayout.RecipesFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherProfileFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FollowingFragment followingFragment;
    private RecipesFragment recipesFragment;
    private ProfileViewPagerAdapter adapter;

    private CircleImageView imageViewAvatar;
    private TextView textViewName,textViewAddress,textViewCount;
    private Button buttonFollow;

    private String uid;
    private List<String> myFollowList;

    public OtherProfileFragment(String uid){
        this.uid = uid;
    }

    private static final String[] TITLES = new String[]{"Recipes","Following"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_other_profile, container,false);
        JsonApi api = RestClient.createService(JsonApi.class);
        //button follow
        buttonFollow = v.findViewById(R.id.btn_follow);
        String myUid = FirebaseAuth.getInstance().getUid();
        Call<ProfilePojo> callMyProfile = api.getProfile(myUid);
        callMyProfile.enqueue(new Callback<ProfilePojo>() {
            @Override
            public void onResponse(Call<ProfilePojo> call, Response<ProfilePojo> response) {
                if (response.isSuccessful()){
                    Profile profile = response.body().getProfile();
                    myFollowList = profile.getFollows();
                    if (myFollowList.contains(uid)){
                        buttonFollow.setText("Following");
                    }else{
                        buttonFollow.setText("Follow");
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfilePojo> call, Throwable t) {

            }
        });
        buttonFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Follow> callFollow = api.postFollow(new Follow(uid,myUid));
                callFollow.enqueue(new Callback<Follow>() {
                    @Override
                    public void onResponse(Call<Follow> call, Response<Follow> response) {
                        if (response.isSuccessful()){
                            String textFollow = buttonFollow.getText().toString().equals("Follow")?"Following":"Follow";
                            buttonFollow.setText(textFollow);
                        }
                        else {
                            Toast.makeText(getContext(), "Đã xảy ra lỗi, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Follow> call, Throwable t) {
                    }
                });
            }
        });

        imageViewAvatar = v.findViewById(R.id.iv_other_avatar);
        textViewName = v.findViewById(R.id.tv_other_name);
        textViewAddress = v.findViewById(R.id.tv_other_address);
        textViewCount = v.findViewById(R.id.tv_other_count);
        viewPager = v.findViewById(R.id.pager_other_profile);
        tabLayout = v.findViewById(R.id.tab_other_profile);

        Call<ProfilePojo> profileCall = api.getProfile(uid);
        profileCall.enqueue(new Callback<ProfilePojo>() {
            @Override
            public void onResponse(Call<ProfilePojo> call, Response<ProfilePojo> response) {
                if (!response.isSuccessful()){
                    Log.e("Code profileCall ",response.code()+" "+ call.request().url().toString());
                    return;
                }
                Profile profile = response.body().getProfile();

                Picasso.with(getContext()).load(profile.getAvatar()).into(imageViewAvatar);
                textViewName.setText(profile.getUserName());
                textViewAddress.setText(profile.getAddress());
                String likes = profile.getLikes()>1?" likes":" like";
                textViewCount.setText(profile.getLikes()+likes);

                recipesFragment = new RecipesFragment(uid);
                followingFragment = new FollowingFragment(profile.getFollows());
                tabLayout.setupWithViewPager(viewPager);
                List<Fragment> fragments = new ArrayList<>();
                fragments.add(recipesFragment);
                fragments.add(followingFragment);
                List<String> titles = new ArrayList<>(Arrays.asList(TITLES));
                adapter = new ProfileViewPagerAdapter(getFragmentManager(),0,fragments,titles);
                viewPager.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ProfilePojo> call, Throwable t) {
                Log.e("Fail profile call ",t.getMessage());
            }
        });

        return v;
    }
}
