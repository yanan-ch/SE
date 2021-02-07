package com.chenyn.androidproject1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
//Adapter扮演着两个角色，一是根据不同ViewType创建与之相应的Item-Layout，
// 二是访问数据集合并将数据绑定到正确的View上
public class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    private Context context;
    private int layoutId;
    private List<T> data = new ArrayList<T>();
    private OnItemClickListener onItemClickListener;

    public void convert(RecyclerViewHolder holder, T t) {}

    public RecyclerViewAdapter(Context _context, int _layoutId, List<T> _data){
        this.context = _context;
        this.layoutId = _layoutId;
        this.data = _data;
    }

    //RecyclerView没有OnItemClickListener方法，需要在Adapter中实现。
    // 方法为：在Adapter中设置一个监听器，当itemView被点击时，
    // 调用该监听器并且将itemView的position作为参数传递出去。
    //添加接口和函数
    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener _onItemClickListener) {
        this.onItemClickListener = _onItemClickListener;
    }

    public T getItem(int i) {
        if (data == null) {
            return null;
        }
        return data.get(i);
    }
    @Override
    public int getItemCount() {
        if(data == null)
            return 0;
        return data.size();
    }

    //创建Item视图，并返回相应的ViewHolder
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder holder = RecyclerViewHolder.get(context, parent, layoutId);
        return holder;
    }
    //绑定数据到正确的Item视图上
    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        // convert函数需要重写，下面会讲
        convert(holder, data.get(position));

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onLongClick(holder.getAdapterPosition());
                    return false;
                }
            });
        }
    }
}
