package com.android.chenyn.androidprojectweek11;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.idescout.sql.SqlScoutServer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText newPw;
    private EditText confirmPw;
    private Button okBtn;
    private Button clearBtn;
    private ImageButton headshot;

    private String headshotUri;

    private SqlScoutServer sqlScoutServer;

    private DataBase myDataBase;

    static final int PICK_PHOTO_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //sqlite debug
        sqlScoutServer = SqlScoutServer.create(this, getPackageName());

        myDataBase = new DataBase(getApplicationContext());

        headshotUri = "defaultHeadshot";

        //初始登录
        loginBtnHandle();

        //RadioButton切换监听
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.loginbtn) {
                    loginBtnHandle();
                }
                else if (checkedId == R.id.registerbtn) {
                    registerBtnHandle();
                }
            }
        });

        //添加头像
        headshot = findViewById(R.id.headshot);
        headshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhotoFromGallay();
            }
        });
    }

    //从图库中选择图片
    protected void choosePhotoFromGallay() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_PHOTO_REQUEST);
    }

    //图库选择图片回调函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == PICK_PHOTO_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if(uri != null){
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        headshotUri = saveImage(bitmap);
                        headshot.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //保存图片到内存，并返回图片路径
    public String saveImage(Bitmap bitmap) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        //图片文件夹目录名字
        File imgDir = cw.getDir("savedImg", Context.MODE_PRIVATE);
        //图片命名
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imgName = "lab3Img" + timeStamp + ".jpg";
        File img = new File(imgDir, imgName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(img);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return img.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回标记，评论页面显示头像时进行判断
        return "defaultHeadshot";
    }

    //登录按钮处理函数
    protected void loginBtnHandle() {
        headshot = findViewById(R.id.headshot);
        username = findViewById(R.id.username);
        newPw = findViewById(R.id.newPw);
        confirmPw = findViewById(R.id.confirmPw);
        okBtn = findViewById(R.id.okBtn);
        clearBtn = findViewById(R.id.clearBtn);
        //设置组件可见性
        headshot.setVisibility(View.GONE);
        newPw.setVisibility(View.GONE);
        confirmPw.setHint("Password");
        //清空密码
        newPw.setText("");
        confirmPw.setText("");

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用户名为空
                if (username.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Username cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                //密码为空
                else if (confirmPw.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                //检查用户是否存在
                else if (!myDataBase.ifUserExist(username.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Username not existed.", Toast.LENGTH_SHORT).show();
                }
                //用户密码校验
                else if (!confirmPw.getText().toString().equals(myDataBase.getUserPassword(username.getText().toString()))) {
                    Toast.makeText(getApplicationContext(), "Invalid Password.", Toast.LENGTH_SHORT).show();
                }
                //成功，跳转页面，传递当前登陆的用户
                else {
                    String usernameBundleArg = username.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                    intent.putExtra("currentUser", usernameBundleArg);
                    startActivity(intent);
                }
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
                confirmPw.setText("");
            }
        });
    }

    protected void registerBtnHandle() {
        headshot = findViewById(R.id.headshot);
        username = findViewById(R.id.username);
        newPw = findViewById(R.id.newPw);
        confirmPw = findViewById(R.id.confirmPw);
        okBtn = findViewById(R.id.okBtn);
        clearBtn = findViewById(R.id.clearBtn);
        //设置组件可见性
        headshot.setVisibility(View.VISIBLE);
        newPw.setVisibility(View.VISIBLE);
        confirmPw.setHint("Confirm Password");
        //清空密码
        newPw.setText("");
        confirmPw.setText("");

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用户名为空
                if (username.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Username cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                //密码为空
                else if (newPw.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                //确认密码重复
                else if (!confirmPw.getText().toString().equals(newPw.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Password Mismatch.", Toast.LENGTH_SHORT).show();
                }
                //检查用户是否已注册
                else if (myDataBase.ifUserExist(username.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Username already existed.", Toast.LENGTH_SHORT).show();
                }
                //注册成功，并将用户加入数据库
                else {
                    User newUser = new User(username.getText().toString(),
                            confirmPw.getText().toString(),
                            headshotUri
                            );
                    myDataBase.addUser(newUser);
                    Toast.makeText(getApplicationContext(), "Register Successfully.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
                newPw.setText("");
                confirmPw.setText("");
                headshot.setImageResource(R.drawable.add);
                headshotUri = "defaultHeadshot";
            }
        });
    }

    @Override
    protected void onDestroy() {
        myDataBase.close();
        sqlScoutServer.destroy();
        super.onDestroy();
    }
}
