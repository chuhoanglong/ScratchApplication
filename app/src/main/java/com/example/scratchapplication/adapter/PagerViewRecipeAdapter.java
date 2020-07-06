package com.example.scratchapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.scratchapplication.fragment.viewrecipe.CookFragment;
import com.example.scratchapplication.fragment.viewrecipe.GalleryFragment;
import com.example.scratchapplication.fragment.viewrecipe.IngredientsFragment;

import java.util.ArrayList;
import java.util.List;

public class PagerViewRecipeAdapter extends FragmentPagerAdapter  {
    List<Fragment> fragments;
    List<String> titles;
    public PagerViewRecipeAdapter(@NonNull FragmentManager fm, int behavior,List<Fragment> fragments, List<String> titles) {
        super(fm, behavior);
        this.fragments = fragments;
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
