package com.example.scratchapplication.tablayout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scratchapplication.R;
import com.example.scratchapplication.adapter.RecipeAdapter;
import com.example.scratchapplication.model.profile.MyProfileRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class RecipesFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    List<MyProfileRecipe> mRecipeItems;
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RecipesFragment newInstance(int columnCount) {
        RecipesFragment fragment = new RecipesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        mRecipeItems= new ArrayList<>();
        for (int i=0; i<10; i++){
            mRecipeItems.add(new MyProfileRecipe(i+"", R.drawable.img1, "Food " + i));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_list, container, false);

        // Set the adapter

        recyclerView = view.findViewById(R.id.list);
        RecipeAdapter adapter = new RecipeAdapter(this.getChildFragmentManager(), mRecipeItems, getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setAdapter(adapter);

        return view;
    }
}