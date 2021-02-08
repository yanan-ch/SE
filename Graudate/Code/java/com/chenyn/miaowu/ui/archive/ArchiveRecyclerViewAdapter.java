package com.chenyn.miaowu.ui.archive;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenyn.miaowu.R;
import com.chenyn.miaowu.Utils.Utils;
import com.chenyn.miaowu.data.pet.Pet;

import java.util.List;

public class ArchiveRecyclerViewAdapter extends RecyclerView.Adapter<ArchiveRecyclerViewAdapter.ArchiveViewHolder> {
    class ArchiveViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mPetProfileImageView;
        private final ImageView mPetGenderImageView;
        private final TextView mPetNameTextView;

        private ArchiveViewHolder(View view) {
            super(view);
            mPetProfileImageView = view.findViewById(R.id.petProfile);
            mPetNameTextView = view.findViewById(R.id.petName);
            mPetGenderImageView = view.findViewById(R.id.petGender);
        }
    }

    private List<Pet> mPets;
    private final LayoutInflater mInflater;
    private Utils utils = Utils.getUtilsInstance();


    public ArchiveRecyclerViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ArchiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_item, parent, false);
        return new ArchiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ArchiveViewHolder holder, int position) {
        if (mPets != null) {
            holder.mPetProfileImageView.setImageBitmap(utils.loadImage(mPets.get(position).getPetProfileUrl()));
            holder.mPetNameTextView.setText(mPets.get(position).getPetName());
            // gender img
            if (mPets.get(position).getPetGender() == "Male") {
                holder.mPetGenderImageView.setImageResource(R.drawable.male);
            }
            else {
                holder.mPetGenderImageView.setImageResource(R.drawable.female);
            }
        }
    }

    void setPets(List<Pet> pets) {
        mPets = pets;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mPets != null) {
            return mPets.size();
        }
        else return 0;
    }
}
