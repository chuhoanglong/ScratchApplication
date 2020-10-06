package com.example.scratchapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.scratchapplication.adapter.PagerViewRecipeAdapter;
import com.example.scratchapplication.fragment.viewrecipe.CookFragment;
import com.example.scratchapplication.fragment.viewrecipe.CommentsFragment;
import com.example.scratchapplication.fragment.viewrecipe.IngredientsFragment;
import com.example.scratchapplication.model.recipe.RecipeCreate;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

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

        Intent intent = getIntent();
        String rId = intent.getStringExtra("RID");
        init(rId);
    }

    private void init(String id) {


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("recipes");
        databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RecipeCreate recipeCreate = snapshot.getValue(RecipeCreate.class);
                CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar_layout);
                collapsingToolbarLayout.setTitle(recipeCreate.getName());
                ImageView imageView = findViewById(R.id.image_cover_collapse);
                Picasso.with(getApplicationContext()).load(recipeCreate.getUrlCover()).into(imageView);
                toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                pager = findViewById(R.id.pager_view_recipe);
                tabLayout = findViewById(R.id.tabs_view_recipe);

                commentsFragment = CommentsFragment.newInstance(snapshot.getKey(),"");
                ingredientsFragment = IngredientsFragment.newInstance(recipeCreate.getIngredients(),ViewRecipeActivity.this);
                cookFragment = CookFragment.newInstance(recipeCreate.getDirections(),ViewRecipeActivity.this);

                tabLayout.setupWithViewPager(pager);
                List<Fragment> fragments = new ArrayList<>();

                fragments.add(ingredientsFragment);
                fragments.add(cookFragment);
                fragments.add(commentsFragment);
                List<String> titles = new ArrayList<>(Arrays.asList(TITLES));

                pager.setAdapter(new PagerViewRecipeAdapter(getSupportFragmentManager(),0,fragments,titles));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}