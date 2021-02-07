package com.example.baixiaoqi.myapplication;


import android.content.ContentResolver;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;


public class DetailActivity extends AppCompatActivity{
    private ImageButton headAddImage;
    private String defaultImage;
    private TextView nameText, titleText, positionText, SurvivabilityValueText, AttackDamageValueText, SkillEffectivenessValueText, StartingAbilityValueText, BestPartnerHeroText, SuppressHeroesText;
    private EditText edit_nameEdit, edit_titleEdit, edit_positionEdit, edit_SurvivabilityValueEdit, edit_AttackDamageValueEdit;
    private EditText edit_SkillEffectivenessValueEdit, edit_StartingAbilityValueEdit, edit_BestPartnerHeroEdit, edit_SuppressHeroesEdit;
     private Button okBut;

    DynamicReceiver dynamicReceiver = null;
    DynamicReceiver widget_dynamic_receiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final String DYNAMICACTION = "com.example.baixiaoqi.myapplication.MyDynamicFilter";
        IntentFilter dynamic_filter = new IntentFilter();
        dynamic_filter.addAction(DYNAMICACTION);    //添加动态广播的Action
        dynamicReceiver = new DynamicReceiver();
        registerReceiver(dynamicReceiver, dynamic_filter);    //注册自定义动态广播消息

        IntentFilter widget_dynamic_filter = new IntentFilter();
        widget_dynamic_filter.addAction("com.example.baixiaoqi.myapplication.WidgetDynamicFilter");
        widget_dynamic_receiver = new DynamicReceiver();
        registerReceiver(widget_dynamic_receiver, widget_dynamic_filter);

        headAddImage = (ImageButton) findViewById(R.id.headAdd);
        okBut = (Button) findViewById(R.id.ok);
        ImageView imgBack = (ImageView) findViewById(R.id.back);
        nameText = (TextView) findViewById(R.id.name);
        titleText = (TextView) findViewById(R.id.title);
        positionText = (TextView) findViewById(R.id.position);
        SurvivabilityValueText = (TextView) findViewById(R.id.SurvivabilityValue);
        AttackDamageValueText = (TextView) findViewById(R.id.AttackDamageValue);
        SkillEffectivenessValueText = (TextView) findViewById(R.id.SkillEffectivenessValue);
        StartingAbilityValueText = (TextView) findViewById(R.id.StartingAbilityValue);
        BestPartnerHeroText = (TextView) findViewById(R.id.BestPartnerHero);
        SuppressHeroesText = (TextView) findViewById(R.id.SuppressHeroes);
        edit_nameEdit = (EditText) findViewById(R.id.edit_name);
        edit_titleEdit = (EditText) findViewById(R.id.edit_title);
        edit_positionEdit = (EditText) findViewById(R.id.edit_position);
        edit_SurvivabilityValueEdit = (EditText) findViewById(R.id.edit_SurvivabilityValue);
        edit_AttackDamageValueEdit = (EditText) findViewById(R.id.edit_AttackDamageValue);
        edit_SkillEffectivenessValueEdit = (EditText) findViewById(R.id.edit_SkillEffectivenessValue);
        edit_StartingAbilityValueEdit = (EditText) findViewById(R.id.edit_StartingAbilityValue);
        edit_BestPartnerHeroEdit = (EditText) findViewById(R.id.edit_BestPartnerHero);
        edit_SuppressHeroesEdit = (EditText) findViewById(R.id.edit_SuppressHeroes);

        defaultImage = "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.add;
        final HeroDataBase myNewDB = new HeroDataBase(this);
//        Herolist temp = (Herolist)getIntent().getSerializableExtra("add");
        if (getIntent().getExtras().getString("add").equals("true")) { //Main跳转
//            Log.i("SSSSSSSSSSSSSSSSa",getIntent().getExtras().getString("add"));
            addHero();
        }
        else {
            heroDetail();
        }


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        okBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_nameEdit.getText().toString().isEmpty() || edit_AttackDamageValueEdit.getText().toString().isEmpty() || edit_BestPartnerHeroEdit.getText().toString().isEmpty()
                        || edit_positionEdit.getText().toString().isEmpty() || edit_SkillEffectivenessValueEdit.getText().toString().isEmpty()
                        || edit_StartingAbilityValueEdit.getText().toString().isEmpty() || edit_SuppressHeroesEdit.getText().toString().isEmpty()
                        || edit_SurvivabilityValueEdit.getText().toString().isEmpty() || edit_titleEdit.getText().toString().isEmpty()
                        || defaultImage.equals("android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.add)) {
                    Toast.makeText(DetailActivity.this, "信息不完整，请继续添加", Toast.LENGTH_SHORT).show();
                } else if (!myNewDB.ifHeroExist(edit_nameEdit.getText().toString())) {
                    Herolist hero = new Herolist(defaultImage, edit_nameEdit.getText().toString(), edit_titleEdit.getText().toString(), edit_positionEdit.getText().toString(),
                            edit_SurvivabilityValueEdit.getText().toString(), edit_AttackDamageValueEdit.getText().toString(), edit_SkillEffectivenessValueEdit.getText().toString(),
                            edit_StartingAbilityValueEdit.getText().toString(), edit_BestPartnerHeroEdit.getText().toString(), edit_SuppressHeroesEdit.getText().toString());
//                    myNewDB.insertHero(hero);
                    Toast.makeText(DetailActivity.this, "英雄添加成功", Toast.LENGTH_SHORT).show();

                    //发送广播
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("recommend2", hero);
                    Intent intentBroadcast = new Intent(DYNAMICACTION); //定义Intent

                    intentBroadcast.putExtras(bundle);
                    sendBroadcast(intentBroadcast);

                    Intent widgetIntentBroadcast = new Intent(); //定义Intent
                    widgetIntentBroadcast.setAction("com.example.baixiaoqi.myapplication.WidgetDynamicFilter");
                    widgetIntentBroadcast.putExtras(bundle);
                    sendBroadcast(widgetIntentBroadcast);
                    EventBus.getDefault().post(hero);
                } else {
                    Log.i("CCCCCCCCCC", String.valueOf(myNewDB.ifHeroExist(edit_nameEdit.getText().toString())));
                    Toast.makeText(DetailActivity.this, "英雄已存在，请不要重复添加", Toast.LENGTH_SHORT).show();
                }

            }

        });

        headAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //激活系统图库，选择一张照片
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("SSSSSSSSSSSSS", String.valueOf(requestCode));
        Log.i("XXXXXXXXXXX", String.valueOf(resultCode));
        if (requestCode == 1) { //获取图片
            if (data != null) {
                //获得图片路径
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

                    Rect rect = new Rect(0,0,1000,1000);
                    Bitmap newBitmap = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);//创建和目标相同大小的空Bitmap
                    Canvas canvas = new Canvas(newBitmap);
                    Paint paint = new Paint();
                    Bitmap temp = bitmap;

                    //针对绘制bitmap添加抗锯齿
                    PaintFlagsDrawFilter pfd= new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
                    paint.setFilterBitmap(true); //对Bitmap进行滤波处理
                    paint.setAntiAlias(true);//设置抗锯齿
                    canvas.setDrawFilter(pfd);
                    canvas.drawBitmap(temp, null, rect, paint);


                    String imageName = UUID.randomUUID().toString() + ".jpg";
                    FileOutputStream out = openFileOutput(imageName, MODE_PRIVATE);
                    newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    Uri imageFileUri = Uri.fromFile(getFileStreamPath(imageName));
                    //通过路径加载图片
                    this.headAddImage.setImageURI(imageFileUri);

                    defaultImage = imageFileUri.toString();
                    out.flush();
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addHero() {
        headAddImage.setImageURI(Uri.parse("android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.add));
        edit_nameEdit.setVisibility(View.VISIBLE);
        edit_titleEdit.setVisibility(View.VISIBLE);
        edit_positionEdit.setVisibility(View.VISIBLE);
        edit_SurvivabilityValueEdit.setVisibility(View.VISIBLE);
        edit_AttackDamageValueEdit.setVisibility(View.VISIBLE);
        edit_SkillEffectivenessValueEdit.setVisibility(View.VISIBLE);
        edit_StartingAbilityValueEdit.setVisibility(View.VISIBLE);
        edit_BestPartnerHeroEdit.setVisibility(View.VISIBLE);
        edit_SuppressHeroesEdit.setVisibility(View.VISIBLE);
        okBut.setVisibility(View.VISIBLE);
        edit_nameEdit.setText("");
        edit_titleEdit.setText("");
        edit_positionEdit.setText("");
        edit_SurvivabilityValueEdit.setText("");
        edit_AttackDamageValueEdit.setText("");
        edit_SkillEffectivenessValueEdit.setText("");
        edit_StartingAbilityValueEdit.setText("");
        edit_BestPartnerHeroEdit.setText("");
        edit_SuppressHeroesEdit.setText("");


    }

    public void heroDetail() {
        Herolist hero = (Herolist) getIntent().getSerializableExtra("query");
        headAddImage.setImageURI(Uri.parse(hero.getDefaultImage()));
        nameText.setText("英雄名字：" + hero.getName());
        titleText.setText("英雄称号：" + hero.getTitle());
        positionText.setText("英雄位置：" + hero.getPosition());
        SurvivabilityValueText.setText("生存能力值：" + hero.getSurvivabilityValue());
        AttackDamageValueText.setText("攻击伤害值：" + hero.getAttackDamageValue());
        SkillEffectivenessValueText.setText("技能效果值：" + hero.getSkillEffectivenessValue());
        StartingAbilityValueText.setText("上手难度值：" + hero.getStartingAbilityValue());
        BestPartnerHeroText.setText("最佳搭档英雄：" + hero.getBestPartnerHero());
        SuppressHeroesText.setText("压制英雄：" + hero.getSuppressHeroes());

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dynamicReceiver);
        unregisterReceiver(widget_dynamic_receiver);
        EventBus.getDefault().unregister(this);
    }
}
