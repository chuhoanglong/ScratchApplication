package com.example.scratchapplication.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scratchapplication.EditProfileActivity;
import com.example.scratchapplication.R;
import com.example.scratchapplication.SettingsActivity;
import com.example.scratchapplication.adapter.ProfileViewPagerAdapter;
import com.example.scratchapplication.adapter.RecipeAdapter;
import com.example.scratchapplication.tablayout.FollowingFragment;
import com.example.scratchapplication.tablayout.RecipesFragment;
import com.example.scratchapplication.tablayout.SaveFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Ahihi";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final int REP_LIST_ITEMS = 100;

    private Button button;
    private ImageView imageView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FollowingFragment followingFragment;
    private RecipesFragment recipesFragment;
    private SaveFragment saveFragment;

    private ProfileViewPagerAdapter adapter;
    private static final String[] TITLES = new String[]{"Recipes","Saved","Following"};
    public ProfileFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SmsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        viewPager = v.findViewById(R.id.pager_recipe);


        button = v.findViewById(R.id.settings);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProfileFragment.this.getActivity(), SettingsActivity.class);
                    startActivity(intent);
                }
            });

        imageView = v.findViewById(R.id.edit_profile);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), EditProfileActivity.class));
                }
            });

        tabLayout = v.findViewById(R.id.Tab_Layout);
        recipesFragment = RecipesFragment.newInstance(2);
        saveFragment = SaveFragment.newInstance(2);
        followingFragment = FollowingFragment.newInstance("");
        tabLayout.setupWithViewPager(viewPager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(recipesFragment);
        fragments.add(saveFragment);
        fragments.add(followingFragment);
        List<String> titles = new ArrayList<>(Arrays.asList(TITLES));
        adapter = new ProfileViewPagerAdapter(getActivity().getSupportFragmentManager(),0,fragments,titles);
        viewPager.setAdapter(adapter);
        return v;
    }







}


