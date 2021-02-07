package com.android.chenyn.androidprojectweek11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "lab3DataBase";
    private static final int DATABASE_VERSION = 1;
    private static final String USER_TABLE = "Users";
    private static final String COMMENTS_TABLE = "Comments";
    private static final String AGREE_RELATION_TABLE = "agreeRelation";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE if not exists " + USER_TABLE
                + "(username TEXT PRIMARY KEY, "
                + "password TEXT, "
                + "headshot TEXT)";
        String CREATE_COMMENTS_TABLE = "CREATE TABLE if not exists " + COMMENTS_TABLE
                + "(commentId TEXT PRIMARY KEY, "
                + "creator TEXT, "
                + "commentTime DATETIME, "
                + "commentContent TEXT)";
        String CREATE_AGREE_RELATION_TABLE = "CREATE TABLE if not exists " + AGREE_RELATION_TABLE
                + "(commentId TEXT, "
                + "username TEXT)";
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_COMMENTS_TABLE);
        db.execSQL(CREATE_AGREE_RELATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + COMMENTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + AGREE_RELATION_TABLE);
        onCreate(db);*/
    }

    //添加用户
    public void addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("headshot", user.getHeadshotUri());
        db.insert(USER_TABLE, null, values);
    }

    //查询，判断当前是否存在
    public Boolean ifUserExist(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + USER_TABLE + " WHERE username = '"+username+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor.getCount() > 0 ? true : false;
    }

    //根据用户名得到用户密码
    public String getUserPassword(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + USER_TABLE + " WHERE username = '"+username+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String password = cursor.getString(cursor.getColumnIndex("password"));
        return password;
    }

    //根据用户名得到用户头像
    public String getUserHeadshot(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + USER_TABLE + " WHERE username = '"+username+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String headshot = cursor.getString(cursor.getColumnIndex("headshot"));
        return headshot;
    }

    //添加评论
    public void addComment(Comment comment) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("commentId", comment.getcommentId());
        values.put("creator", comment.getcreator());
        values.put("commentTime", comment.getCommentTime());
        values.put("commentContent", comment.getCommentContent());
        db.insert(COMMENTS_TABLE, null, values);
    }

    //删除评论
    public void removeComment(String commentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(COMMENTS_TABLE, "commentId = ?", new String[] {commentId});
    }

    //得到所有评论，在ListView显示
    public ArrayList<Comment> getAllComments() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Comment> comments = new ArrayList<Comment>();
        String selectQuery = "SELECT * FROM " + COMMENTS_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, new String[] {});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Comment comment = new Comment();
                comment.setcommentId(cursor.getString(cursor.getColumnIndex("commentId")));
                comment.setcreator(cursor.getString(cursor.getColumnIndex("creator")));
                comment.setCommentTime(cursor.getString(cursor.getColumnIndex("commentTime")));
                comment.setCommentContent(cursor.getString(cursor.getColumnIndex("commentContent")));
                comments.add(comment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return comments;
    }

    //添加点赞关系
    public void addAgreeRelation(String commentId, String username) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("commentId", commentId);
        values.put("username", username);
        db.insert(AGREE_RELATION_TABLE, null, values);
        Log.e("TAG = addAgreeRelation", username + " agreed " + commentId);
    }

    //取消点赞关系
    public void deleteAgreeRelation(String commentId, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AGREE_RELATION_TABLE, "commentId = ? and username = ?", new String[] {commentId, username});
        Log.e("TAG = deleteAgreeRelation", "Delete AgreeRelation");
    }

    //判断当前用户是否点赞过某条评论
    public Boolean ifAgreeRelationExist(String commentId, String username) {
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + AGREE_RELATION_TABLE + " WHERE commentId = '"+commentId+"' and username = '"+username+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Log.e("TAG=ifAgreeRelationExist", username + " have agreed " + commentId);
            return true;
        } else {
            Log.e("TAG=ifAgreeRelationExist", username + " not agreed " + commentId);
            return false;
        }
    }

    //获取当前评论的被点赞数
    public int getAgreeNumber(String commentId) {
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + AGREE_RELATION_TABLE + " WHERE commentId = '"+commentId+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Log.e("TAG = getAgreeNumber", Integer.toString(cursor.getCount()));
            return cursor.getCount();
        } else {
            return 0;
        }
    }

    //评论页面加载头像
    public Bitmap loadHeadshot(String path) {
        try {
            File file = new File(path);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            return bitmap;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
