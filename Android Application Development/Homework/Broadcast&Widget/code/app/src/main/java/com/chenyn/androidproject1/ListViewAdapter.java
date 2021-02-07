package com.chenyn.androidproject1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
//自定义的Adapter需要集成BaseAdapter
public class ListViewAdapter extends BaseAdapter {
    //自定义Adapter需要提供一个数据列表才能填充数据，一般是一个List类型
    private List<FoodModel> favorList;
    private Context context;

    public ListViewAdapter(Context _context, List<FoodModel> _favorList) {
        this.context = _context;
        this.favorList = _favorList;
    }
    //获得数据项列表的长度，也就是一共有多少个数据项
    @Override
    public int getCount() {
        if (favorList == null) {
            return 0;
        }
        return favorList.size();
    }
    //获得一个数据项
    @Override
    public long getItemId(int i) {
        return i;
    }
    //获得数据项的位置
    @Override
    public Object getItem(int i) {
        if (favorList == null) {
            return null;
        }
        return favorList.get(i);
    }
    //获得数据项的布局样式
    //i指的是当前是在加载第几项的列表项
    //viewGroup是列表项View的父视图，调整列表项的宽高用的
    //view指的是一个列表项的视图，我们需要给view一个布局，然后在布局中放置我们需要的内容
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // 新声明一个View变量和ViewHoleder变量
        View convertView;
        ListViewHolder listViewHolder;
        // 当view为空时才加载布局，否则，直接修改内容
        if (view == null) {
            // 通过inflate的方法加载布局，context需要在使用这个Adapter的Activity中传入。
            convertView= LayoutInflater.from(context).inflate(R.layout.fooditem, null);
            listViewHolder = new ListViewHolder();
            listViewHolder.circleContent = (TextView) convertView.findViewById(R.id.circleContent);
            listViewHolder.foodName = (TextView) convertView.findViewById(R.id.foodName);
            convertView.setTag(listViewHolder); // 用setTag方法将处理好的viewHolder放入view中
        } else { // 否则，让convertView等于view，然后从中取出ViewHolder即可
            convertView = view;
            listViewHolder = (ListViewHolder) convertView.getTag();
        }
        // 从viewHolder中取出对应的对象，然后赋值给他们
        listViewHolder.circleContent.setText(favorList.get(i).getCircleContent());
        listViewHolder.foodName.setText(favorList.get(i).getFoodName());
        // 将这个处理好的view返回
        return convertView;
    }
    //ListViewHolder定义
    private class ListViewHolder {
        public TextView circleContent;
        public TextView foodName;
    }
}
