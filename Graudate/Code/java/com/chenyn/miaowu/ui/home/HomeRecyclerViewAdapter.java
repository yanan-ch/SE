package com.chenyn.miaowu.ui.home;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chenyn.miaowu.R;
import com.chenyn.miaowu.Utils.Utils;
import com.chenyn.miaowu.data.post.Post;

import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeViewHolder> {

    class HomeViewHolder extends RecyclerView.ViewHolder {

        private final TextView mPostDateView;
        private final TextView mPostTimeView;
        private final ImageView mPostImageView;
        private final TextView mPostTextView;
        private final ImageView mDeleteImageView;

        private HomeViewHolder(View view) {
            super(view);
            mPostDateView = view.findViewById(R.id.postDate);
            mPostTimeView = view.findViewById(R.id.postTime);
            mPostImageView = view.findViewById(R.id.postImage);
            mPostTextView = view.findViewById(R.id.postText);
            mDeleteImageView = view.findViewById(R.id.deletePost);
        }
    }

    private List<Post> mPosts;
    private final LayoutInflater mInflater;
    private Utils utils = Utils.getUtilsInstance();


    public HomeRecyclerViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_item, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HomeViewHolder holder, int position) {
        if (mPosts != null) {
            Post current = mPosts.get(position);
            // handle time format
            // example:2020-01-18 11:11:11
            String postTime = current.getPostTime();
            holder.mPostDateView.setText(postTime.substring(5, 9));
            holder.mPostTimeView.setText(postTime.substring(11, 15));
            holder.mPostImageView.setImageBitmap(utils.loadImage(mPosts.get(position).getImageUrl()));
            holder.mPostTextView.setText(mPosts.get(position).getPostText());

            holder.mDeleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {}
            });
        }
    }

    void setPosts(List<Post> posts) {
        mPosts = posts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mPosts != null) {
            return mPosts.size();
        }
        else return 0;
    }
}
