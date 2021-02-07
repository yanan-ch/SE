package com.chenyn.androidproject1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//ViewHolder通常出现在适配器中，
// 为的是recyclerview滚动时快速设置值，
// 而不必每次都重新创建很多对象，从而提升性能
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    //使用一个SparseArray数组存储listItem中的子View
    private SparseArray<View> views;
    private View view;

    public RecyclerViewHolder(Context _context, View _view, ViewGroup _viewGroup){
        super(_view);
        view = _view;
        views = new SparseArray<View>();
    }
    //获取ViewHolder实例
    public static RecyclerViewHolder get(Context _context, ViewGroup _viewGroup, int _layoutId) {
        View _view = LayoutInflater.from(_context).inflate(_layoutId, _viewGroup, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(_context, _view, _viewGroup);
        return holder;
    }
    //ViewHolder尚未将子View缓存到SparseArray数组中时，
    // 仍然需要通过findViewById()创建View对象，如果已缓存，直接返回即可。
    public <T extends View> T getView(int _viewId) {
        View _view = views.get(_viewId);
        if (_view == null) {
            // 创建view
            _view = view.findViewById(_viewId);
            // 将view存入views
            views.put(_viewId, _view);
        }
        return (T)_view;
    }
}
