package com.androidhw.chenyn.lab5.Github;

import com.androidhw.chenyn.lab5.MyViewHolder;
import com.androidhw.chenyn.lab5.R;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class GithubIssueRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder>{
    private ArrayList<GithubIssueObj> issues;

    public GithubIssueRecyclerAdapter(){
        issues = new ArrayList<>();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_item, parent, false);
        // 实例化viewholder
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position){
        // 绑定数据
        ((TextView)holder.getView(R.id.issue_body)).setText(issues.get(position).getBody());
        ((TextView)holder.getView(R.id.issue_create_date)).setText(issues.get(position).getCreated_at());
        ((TextView)holder.getView(R.id.issue_state)).setText(issues.get(position).getState());
        ((TextView)holder.getView(R.id.issue_title)).setText(issues.get(position).getTitle());
    }
    public void addItem(GithubIssueObj githubIssueObj){
        if(issues != null){
            issues.add(githubIssueObj);
            notifyItemInserted(issues.size() - 1);
        }
    }
    //同样有个清空列表的操作
    public void reset(){
        int count = issues.size();
        issues.clear();
        notifyItemRangeRemoved(0,count);
    }
    @Override
    public int getItemCount() {
        return issues.size();
    }
}
