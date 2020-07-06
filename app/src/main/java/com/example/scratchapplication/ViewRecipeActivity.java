package com.example.scratchapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.scratchapplication.adapter.PagerViewRecipeAdapter;
import com.example.scratchapplication.fragment.viewrecipe.CookFragment;
import com.example.scratchapplication.fragment.viewrecipe.GalleryFragment;
import com.example.scratchapplication.fragment.viewrecipe.IngredientsFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewRecipeActivity extends AppCompatActivity {
    private static final String[] TITLES = new String[]{"Galllery","Ingredients","How to Cook"};
    private TabLayout tabLayout;
    private ViewPager pager;
    private Toolbar toolbar;
    private GalleryFragment galleryFragment;
    private IngredientsFragment ingredientsFragment;
    private CookFragment cookFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_recipe);

        Bundle bundle = getIntent().getExtras();
        String rId = bundle.getString("RID");
        init(rId);
    }

    private void init(String id) {
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar_layout);
        collapsingToolbarLayout.setTitle("Recipe Name");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pager = findViewById(R.id.pager_view_recipe);
        tabLayout = findViewById(R.id.tabs_view_recipe);

        galleryFragment = GalleryFragment.newInstance(id,"");
        ingredientsFragment = IngredientsFragment.newInstance(id,"");
        cookFragment = CookFragment.newInstance(id,"");

        tabLayout.setupWithViewPager(pager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(galleryFragment);
        fragments.add(ingredientsFragment);
        fragments.add(cookFragment);
        List<String> titles = new ArrayList<>(Arrays.asList(TITLES));

        pager.setAdapter(new PagerViewRecipeAdapter(getSupportFragmentManager(),0,fragments,titles));
    }
}