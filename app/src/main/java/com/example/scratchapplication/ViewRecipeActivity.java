package com.example.scratchapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.example.scratchapplication.adapter.PagerViewRecipeAdapter;
import com.example.scratchapplication.api.JsonApi;
import com.example.scratchapplication.api.RestClient;
import com.example.scratchapplication.fragment.viewrecipe.CookFragment;
import com.example.scratchapplication.fragment.viewrecipe.CommentsFragment;
import com.example.scratchapplication.fragment.viewrecipe.IngredientsFragment;
import com.example.scratchapplication.model.ProfilePojo;
import com.example.scratchapplication.model.RecipePojo;
import com.example.scratchapplication.model.home.ModelRecipe;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        JsonApi api = RestClient.createService(JsonApi.class);
        Call<RecipePojo> call = api.getRecipeDetail(new JsonApi.Rid(id));
        call.enqueue(new Callback<RecipePojo>() {
            @Override
            public void onResponse(Call<RecipePojo> call, Response<RecipePojo> response) {
                if (!response.isSuccessful()){
                    Log.e("Code",response.code()+" "+call.request().url().toString());
                    return;
                }
                ModelRecipe modelRecipe = response.body().getModelRecipe();
                CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapse_toolbar_layout);
                collapsingToolbarLayout.setTitle(modelRecipe.getName());

                ImageView imageView = findViewById(R.id.image_cover_collapse);
                Picasso.with(getApplicationContext()).load(modelRecipe.getUrlCover()).into(imageView);


                toolbar = findViewById(R.id.recipe_toolbar);
                toolbar.setNavigationIcon(R.drawable.ic_back);
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("Back","cliked");
                        finish();
                    }
                });

                pager = findViewById(R.id.pager_view_recipe);
                tabLayout = findViewById(R.id.tabs_view_recipe);

//                commentsFragment = CommentsFragment.newInstance(snapshot.getKey(),"");
//                ingredientsFragment = IngredientsFragment.newInstance(recipeCreate.getIngredients(),ViewRecipeActivity.this);
//                cookFragment = CookFragment.newInstance(recipeCreate.getDirections(),ViewRecipeActivity.this);
//
//                tabLayout.setupWithViewPager(pager);
//                List<Fragment> fragments = new ArrayList<>();
//
//                fragments.add(ingredientsFragment);
//                fragments.add(cookFragment);
//                fragments.add(commentsFragment);
//                List<String> titles = new ArrayList<>(Arrays.asList(TITLES));
//
//                pager.setAdapter(new PagerViewRecipeAdapter(getSupportFragmentManager(),0,fragments,titles));
            }

            @Override
            public void onFailure(Call<RecipePojo> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}