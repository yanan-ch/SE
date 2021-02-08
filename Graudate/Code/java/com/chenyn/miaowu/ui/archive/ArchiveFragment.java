package com.chenyn.miaowu.ui.archive;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chenyn.miaowu.R;
import com.chenyn.miaowu.data.pet.Pet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ArchiveFragment extends Fragment {
    public static final int NEW_PET_ACTIVITY_REQUEST_CODE = 4;

    private ArchiveViewModel archiveViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_archive_list, container, false);

        RecyclerView archiveRecyclerView = root.findViewById(R.id.petList);
        final ArchiveRecyclerViewAdapter archiveRecyclerViewAdapter = new ArchiveRecyclerViewAdapter(this.getContext());
        archiveRecyclerView.setAdapter(archiveRecyclerViewAdapter);
        archiveRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        archiveViewModel = ViewModelProviders.of(this).get(ArchiveViewModel.class);
        archiveViewModel.getAllPets().observe(this.getViewLifecycleOwner(), new Observer<List<Pet>>() {
            @Override
            public void onChanged(List<Pet> pets) {
                archiveRecyclerViewAdapter.setPets(pets);
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.petFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(getActivity(), AddPetActivity.class);
                startActivityForResult(intent, NEW_PET_ACTIVITY_REQUEST_CODE);
            }
        });
        return root;
    }
}
