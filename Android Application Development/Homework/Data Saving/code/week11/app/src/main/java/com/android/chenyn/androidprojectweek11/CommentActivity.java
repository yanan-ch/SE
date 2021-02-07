package com.android.chenyn.androidprojectweek11;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.idescout.sql.SqlScoutServer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CommentActivity extends AppCompatActivity {
    private String currentUser;
    private Button sendBtn;
    private EditText commentInput;

    private ListView commentListView;
    private ListViewAdapter commentAdapter;

    private ArrayList<Comment> commentList;

    private DataBase myDataBase;

    private SqlScoutServer sqlScoutServer;

    private static final int CONTACT_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        //获取Intent，得到当前登陆的用户
        Intent intent = getIntent();
        currentUser = intent.getStringExtra("currentUser");

        sendBtn = findViewById(R.id.sendBtn);
        commentInput = findViewById(R.id.commentInput);

        //sqlite debug
        sqlScoutServer = SqlScoutServer.create(this, getPackageName());

        myDataBase = new DataBase(getApplicationContext());

        commentList = myDataBase.getAllComments();
        commentListView = findViewById(R.id.comments);
        commentAdapter = new ListViewAdapter(getApplicationContext(), commentList, currentUser, myDataBase);
        commentListView.setAdapter(commentAdapter);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentInput.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Comment cannot be empty.", Toast.LENGTH_SHORT).show();
                } else {
                    //评论时间格式化
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String commentTime = df.format(calendar.getTime());
                    String commentContent = commentInput.getText().toString();
                    Comment newComment = new Comment(currentUser, commentTime, commentContent);
                    //添加评论道数据库，ListView，清空评论输入
                    commentList.add(newComment);
                    myDataBase.addComment(newComment);
                    commentAdapter.notifyDataSetChanged();
                    commentInput.setText("");
                }
            }
        });

        //ListView单点击函数，获取用户电话号码
        commentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Comment selectedComment = commentAdapter.getItem(position);
                requestContactPermission();
                String phoneNumber = "";
                String displayInfo = "";
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = \"" + selectedComment.getcreator() + "\"",
                        null, null, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //电话号码格式化
                    Log.e("TAG=read contact", "number" + phoneNumber);
                    displayInfo = "Phone: " + phoneNumber;
                } else {
                    displayInfo = "Phone number not exist.";
                }
                //弹出对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                builder.setTitle("Info");
                builder.setMessage("Username: " + selectedComment.getcreator() + "\n" + displayInfo);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
            }
        });

        //长按事件处理
        commentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Comment selectedComment = commentAdapter.getItem(position);
                //是当前登录用户，删除评论
                if (selectedComment.getcreator().equals(currentUser)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                    builder.setTitle("Delete or not?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    myDataBase.removeComment(selectedComment.getcommentId());
                                    commentList.remove(position);
                                    commentAdapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) { }
                            });
                    builder.create().show();
                }
                //不是当前登录用户，举报评论
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                    builder.setTitle("Report or not?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getApplicationContext(), "Report Successfully.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) { }
                            });
                    builder.create().show();
                }
                return true;
            }
        });
    }

    //检查读取联系人权限
    protected Boolean checkForContactPermission() {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS);
    }

    //申请权限
    protected void requestContactPermission() {
        if (!checkForContactPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.READ_CONTACTS}, CONTACT_REQUEST_CODE);
            Log.e("TAG=requestContactPermission", "no permitted and requested");
        } else {
            Log.e("TAG=requestContactPermission", "have permitted");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == CONTACT_REQUEST_CODE) {
            Log.e("TAG=onRequestPermissionsResult", "request contact");
        }
    }

    @Override
    protected void onDestroy() {
        myDataBase.close();
        sqlScoutServer.destroy();
        super.onDestroy();
    }
}
