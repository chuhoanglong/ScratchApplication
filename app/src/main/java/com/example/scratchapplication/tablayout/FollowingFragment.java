package com.example.scratchapplication.tablayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.scratchapplication.R;
import com.example.scratchapplication.adapter.FollowingAdapter;
import com.example.scratchapplication.model.profile.FollowingList;
import java.util.ArrayList;
import java.util.List;

public class FollowingFragment extends Fragment {
    // TODO: Customize parameter argument names
    // TODO: Customize parameters
    private static final String ARG_PARAM1 = "param1";
    List<FollowingList> mFollowingList;
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FollowingFragment newInstance(String param1) {
        FollowingFragment fragment = new FollowingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mFollowingList= new ArrayList<>();
        for (int i=0; i<10; i++){
            mFollowingList.add(new FollowingList( R.drawable.other_avatar, "Ariana Barros", "Pancake Lover", "696 followers  |  45k likes"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following_list, container, false);

        // Set the adapter

        recyclerView = view.findViewById(R.id.following_list);
        FollowingAdapter adapter = new FollowingAdapter(this.getChildFragmentManager(), mFollowingList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}
