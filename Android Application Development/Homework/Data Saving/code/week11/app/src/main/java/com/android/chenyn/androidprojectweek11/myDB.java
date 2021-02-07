package com.android.chenyn.androidprojectweek11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.NavUtils;

import java.security.PublicKey;

public class myDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "lab3.db";
    public static final String USER_TABLE_NAME = "Users";
    public static final String COMMENTS_TABLE_NAME = "Comments";

    public myDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE if not exists " + USER_TABLE_NAME
                + "(username TEXT PRIMARY KEY, password TEXT, headshot TEXT)";
        db.execSQL(CREATE_USERS_TABLE);
        String CREATE_COMMENTS_TABLE = "CREATE TABLE if not exists " + COMMENTS_TABLE_NAME
                + "(username TEXT PRIMARY KEY, password TEXT, headshot TEXT)";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public void addUser(UserInfo user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("headshot", user.getHeadshotUri());
        db.insert(USER_TABLE_NAME, null, values);
        db.close();
    }

    /*public Cursor queryUser(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String[] Col = {"username", "password", "headshot"};
        String selection = "username = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(
                USER_TABLE_NAME,
                Col,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return cursor;
    }*/

    public Boolean ifUserExist(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String[] Col = {"username", "password", "headshot"};
        String selection = "username = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(
                USER_TABLE_NAME,
                Col,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null
        );
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getUserPw(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String[] Col = {"username", "password", "headshot"};
        String selection = "username = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(
                USER_TABLE_NAME,
                Col,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        String password = cursor.getString(0);
        cursor.close();
        return password;
    }



}
