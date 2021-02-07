package com.androidhw.chenyn.lab5.Github;

import com.androidhw.chenyn.lab5.MyViewHolder;
import com.androidhw.chenyn.lab5.R;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class GithubRepoRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder>{
    private ArrayList<GithubRepoObj> repos;
    private GithubRepoRecyclerAdapter.OnItemClickListener onItemClickListener;

    public GithubRepoRecyclerAdapter(){
        repos = new ArrayList<>();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item, parent, false);
        // 实例化viewholder
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position){
        // 绑定数据
        ((TextView)holder.getView(R.id.repo_name)).setText(repos.get(position).getName());
        //int转String
        ((TextView)holder.getView(R.id.repo_issues_count)).setText(String.valueOf(repos.get(position).getOpen_issues_count()));
        ((TextView)holder.getView(R.id.repo_description)).setText(repos.get(position).getDescription());
        ((TextView)holder.getView(R.id.repo_id)).setText(repos.get(position).getId());
        //listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });
    }
    public void addItem(GithubRepoObj githubRepoObj){
        if(repos != null){
            repos.add(githubRepoObj);
            notifyItemInserted(repos.size() - 1);
        }
    }
    @Override
    public int getItemCount() {
        return repos.size();
    }

    //清空RecyclerView列表
    public void reset(){
        int count = repos.size();
        repos.clear();
        notifyItemRangeRemoved(0,count);
    }

    public String getRepoName(int position){
        return repos.get(position).getName();
    }

    public void setOnItemClickListener(GithubRepoRecyclerAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}

