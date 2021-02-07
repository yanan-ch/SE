package com.android.chenyn.androidprojectweek11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Comment> commentList;
    private DataBase myDataBase;
    private String currentUser;

    public ListViewAdapter(Context context, ArrayList<Comment> commentLists, String currentUser, DataBase dataBase) {
        this.context = context;
        this.commentList = commentLists;
        this.currentUser = currentUser;
        this.myDataBase = dataBase;
    }

    private class ViewHolder {
        public ImageView headshot;
        public TextView username;
        public TextView commentTime;
        public TextView commentContent;
        public ImageView agreeImage;
        public TextView agreeNumber;
    }

    @Override
    public int getCount() {
        if (commentList == null) {
            return 0;
        }
        return commentList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Comment getItem(int position) {
        if (commentList == null) {
            return null;
        }
        return commentList.get(position);
    }

    @Override
    public View getView(int position, final View view, ViewGroup viewGroup) {
        View cv;
        final ViewHolder viewHolder;
        if (view == null) {
            cv = LayoutInflater.from(context).inflate(R.layout.item, null);
            viewHolder = new ViewHolder();
            viewHolder.headshot = cv.findViewById(R.id.headshotInItem);
            viewHolder.username = cv.findViewById(R.id.usernameInItem);
            viewHolder.commentTime = cv.findViewById(R.id.commentTime);
            viewHolder.commentContent = cv.findViewById(R.id.commentContent);
            viewHolder.agreeImage = cv.findViewById(R.id.agreeImage);
            viewHolder.agreeNumber = cv.findViewById(R.id.agreeNumber);
            cv.setTag(viewHolder);
        } else {
            cv = view;
            viewHolder = (ViewHolder) cv.getTag();
        }

        final String commentId = commentList.get(position).getcommentId();
        String creator = commentList.get(position).getcreator();
        //填充数据
        //头像
        String headshotUri = myDataBase.getUserHeadshot(creator);
        if (headshotUri.equals("defaultHeadshot")) {
            viewHolder.headshot.setImageResource(R.drawable.me);
        } else {
            viewHolder.headshot.setImageBitmap(myDataBase.loadHeadshot(headshotUri));
        }
        //用户名
        viewHolder.username.setText(commentList.get(position).getcreator());
        //评论时间
        viewHolder.commentTime.setText(commentList.get(position).getCommentTime());
        //评论内容
        viewHolder.commentContent.setText(commentList.get(position).getCommentContent());
        //该评论的被点赞数量
        viewHolder.agreeNumber.setText(Integer.toString(myDataBase.getAgreeNumber(commentId)));
        //当前用户是否点赞了该评论
        final Boolean ifAgreed = myDataBase.ifAgreeRelationExist(commentId, currentUser);
        if (ifAgreed == true) {
            viewHolder.agreeImage.setImageResource(R.drawable.red);
        } else {
            viewHolder.agreeImage.setImageResource(R.drawable.white);
        }

        //点赞图片被点击处理事件
        viewHolder.agreeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView agreeImage = v.findViewById(R.id.agreeImage);
                if (myDataBase.ifAgreeRelationExist(commentId, currentUser)) {
                    myDataBase.deleteAgreeRelation(commentId, currentUser);
                    agreeImage.setImageResource(R.drawable.white);
                } else {
                    myDataBase.addAgreeRelation(commentId, currentUser);
                    agreeImage.setImageResource(R.drawable.red);
                }
                viewHolder.agreeNumber.setText(Integer.toString(myDataBase.getAgreeNumber(commentId)));
            }
        });

        return cv;
    }
}
