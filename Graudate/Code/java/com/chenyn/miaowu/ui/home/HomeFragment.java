package com.chenyn.miaowu.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chenyn.miaowu.MainActivity;
import com.chenyn.miaowu.R;
import com.chenyn.miaowu.data.post.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class HomeFragment extends Fragment {

    public static final int NEW_POST_ACTIVITY_REQUEST_CODE = 1;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_list, container, false);

        RecyclerView homeRecyclerView = root.findViewById(R.id.postList);
        final HomeRecyclerViewAdapter homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(this.getContext());
        homeRecyclerView.setAdapter(homeRecyclerViewAdapter);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.getAllPosts().observe(this.getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                homeRecyclerViewAdapter.setPosts(posts);
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.postFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(getActivity(), AddPostActivity.class);
                startActivityForResult(intent, NEW_POST_ACTIVITY_REQUEST_CODE);
            }
        });

        return root;
    }

}
