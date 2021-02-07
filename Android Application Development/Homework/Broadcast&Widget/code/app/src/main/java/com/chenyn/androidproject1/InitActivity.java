package com.chenyn.androidproject1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class InitActivity extends AppCompatActivity {
    //变量声明与定义
    private String checkedRadioBtnText = "图片";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        final Button searchBtn = findViewById(R.id.searchBtn);
        final EditText searchInput = findViewById(R.id.searchText);
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        //执行搜索功能之前先要监听RadioGroup；
        listenForRadios(radioGroup);
        //点击搜索按钮要执行的逻辑判断
        if (searchBtn != null) {
            searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenForRadios(radioGroup);
                    handleSearchBtn(searchInput, alertDialog);
                }
            });
        }
        //点击fab跳转到食品列表界面
        navigateToFoodlist();
    }
    //Radios监听函数
    private void listenForRadios(RadioGroup radioGroup) {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.pictureRadio:
                        checkedRadioBtnText = "图片";
                        break;
                    case R.id.videoRadio:
                        checkedRadioBtnText = "视频";
                        break;
                    case R.id.faqsRadio:
                        checkedRadioBtnText = "问答";
                        break;
                    case R.id.infoRadio:
                        checkedRadioBtnText = "资讯";
                        break;
                }
                Toast.makeText(getApplication(), checkedRadioBtnText + "被选中", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //搜索按钮逻辑处理函数
    private void handleSearchBtn(EditText searchInput, AlertDialog.Builder alertDialog) {
        if (searchInput.getText().length() == 0) {
            Toast.makeText(getApplication(),"搜索内容不能为空", Toast.LENGTH_SHORT).show();
        }
        else if (searchInput.getText().toString().equals("Health")) {
            alertDialog.setTitle("提示").setMessage(checkedRadioBtnText + "搜索成功").setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplication(),"对话框“确定”按钮被点击", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplication(),"对话框“取消”按钮被点击", Toast.LENGTH_SHORT).show();
                        }
                    }).create();
        }
        else {
            alertDialog.setTitle("提示").setMessage("搜索失败").setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplication(),"对话框“确定”按钮被点击", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplication(),"对话框“取消”按钮被点击", Toast.LENGTH_SHORT).show();
                        }
                    }).create();
        }
        alertDialog.show();
    }
    //fab页面跳转函数
    private void navigateToFoodlist() {
        FloatingActionButton fab = findViewById(R.id.fabOnInit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InitActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}