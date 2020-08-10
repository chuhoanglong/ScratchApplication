package com.example.scratchapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.scratchapplication.adapter.PagerViewRecipeAdapter;
import com.example.scratchapplication.fragment.viewrecipe.CookFragment;
import com.example.scratchapplication.fragment.viewrecipe.CommentsFragment;
import com.example.scratchapplication.fragment.viewrecipe.IngredientsFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewRecipeActivity extends AppCompatActivity {
    private static final String[] TITLES = new String[]{"Ingredients","How to Cook","Comments"};
    private TabLayout tabLayout;
    private ViewPager pager;
    private Toolbar toolbar;
    private CommentsFragment commentsFragment;
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

        commentsFragment = CommentsFragment.newInstance(id,"");
        ingredientsFragment = IngredientsFragment.newInstance(id,"");
        cookFragment = CookFragment.newInstance(id,"");

        tabLayout.setupWithViewPager(pager);
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(ingredientsFragment);
        fragments.add(cookFragment);
        fragments.add(commentsFragment);
        List<String> titles = new ArrayList<>(Arrays.asList(TITLES));

        pager.setAdapter(new PagerViewRecipeAdapter(getSupportFragmentManager(),0,fragments,titles));
    }
}