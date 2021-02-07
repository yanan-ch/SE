package com.example.baixiaoqi.myapplication;

        import android.content.Context;
        import android.net.Uri;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;
        import java.util.List;

public class MyAdaptor extends BaseAdapter {
    private List<Herolist> list;
    private LayoutInflater layoutInflater;
    private HeroDataBase db;


    public MyAdaptor(Context context, HeroDataBase initDB) {
        layoutInflater = LayoutInflater.from(context);
        db = initDB;
        list = db.getAllComments();
    }

    public void setListItems(List<Herolist> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Herolist getItem(int i) {
        if (list == null) {
            return null;
        }
        return list.get(i);
    }

    public void removeItem(int i) {
        db.deleteHero(list.get(i).getName());
        list.remove(i);
        notifyDataSetChanged();
    }

    public void addItem(Herolist hero) {
        if(list != null && hero!= null){
            list.add(hero);
            notifyDataSetChanged();
        }
        db.insertHero(hero);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // 新声明一个View变量和ViewHoleder变量,ViewHolder类在下面定义。
        View convertView;
        ViewHolder viewHolder;
        // 当view为空时才加载布局，否则，直接修改内容
        if (view == null) {
            // 通过inflate的方法加载布局，context需要在使用这个Adapter的Activity中传入。
            view = layoutInflater.inflate(R.layout.item, null);
            viewHolder = new ViewHolder();
            viewHolder.heroName = (TextView) view.findViewById(R.id.heroName);
            viewHolder.heroPosition = (TextView) view.findViewById(R.id.heroPosition);
            viewHolder.head = (ImageView) view.findViewById(R.id.head);
            convertView = view;
            convertView.setTag(viewHolder); // 用setTag方法将处理好的viewHolder放入view中
        } else { // 否则，让convertView等于view，然后从中取出ViewHolder即可
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 从viewHolder中取出对应的对象，然后赋值给他们
        viewHolder.heroName.setText(list.get(i).getName());
        viewHolder.heroPosition.setText(list.get(i).getPosition());
        viewHolder.head.setImageURI(Uri.parse(list.get(i).getDefaultImage()));

        // 将这个处理好的view返回
        return convertView;
    }

    private class ViewHolder {
        public TextView heroName;
        public TextView heroPosition;
        public ImageView head;
    }

}
