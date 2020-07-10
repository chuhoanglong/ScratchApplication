package com.example.scratchapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.scratchapplication.adapter.ProfileViewPagerAdapter;

import com.example.scratchapplication.tablayout.FollowingFragment;
import com.example.scratchapplication.tablayout.RecipesFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OtherProfileActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FollowingFragment followingFragment;
    private RecipesFragment recipesFragment;
    private ProfileViewPagerAdapter adapter;

    private static final String[] TITLES = new String[]{"Recipes","Following"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_profile);
        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        viewPager = findViewById(R.id.pager_other_profile);
        tabLayout = findViewById(R.id.Tab_other_profile);
        recipesFragment = RecipesFragment.newInstance(2);
        followingFragment = FollowingFragment.newInstance("");
        tabLayout.setupWithViewPager(viewPager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(recipesFragment);
        fragments.add(followingFragment);
        List<String> titles = new ArrayList<>(Arrays.asList(TITLES));
        adapter = new ProfileViewPagerAdapter(getSupportFragmentManager(),0,fragments,titles);
        viewPager.setAdapter(adapter);
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}