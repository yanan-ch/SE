package com.example.baixiaoqi.myapplication;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.time.chrono.HijrahEra;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private MyAdaptor myAdapter;
    private ListView listView;
    private HeroDataBase initDB;
//    public void initData() {
////        String li =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.李白icon;
////        String lu =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.鲁班七号icon;
////        String wang =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.王昭君icon;
////        String diao =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.貂蝉icon;
////        String zhuang =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.庄周icon;
////        String gong =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.宫本武藏icon;
////        String ne =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.哪吒icon;
////        String wu =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.武则天icon;
////        String sun =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.孙悟空icon;
////        String c =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.曹操icon;
//
//
////        initDB.insert2DB(li, "李白", "青莲剑仙", "刺客", "40", "70", "60", "90", "鬼谷子", "妲己");
////        initDB.insert2DB(lu, "鲁班七号", "机关造物", "射手", "10", "100", "30", "40", "太乙真人", "王昭君");
////        initDB.insert2DB(wang, "王昭君", "冰雪之华", "法师", "40", "40", "80", "40", "庄周", "鲁班七号");
////        initDB.insert2DB(diao, "貂蝉", "绝世舞姬", "法师", "40", "20", "70", "60", "庄周", "露娜");
////        initDB.insert2DB(zhuang, "庄周", "逍遥幻梦", "辅助", "80", "20", "40", "20", "不知火舞", "后羿");
////        initDB.insert2DB(gong, "宫本武藏", "剑圣", "战士", "50", "70", "40", "50", "太乙真人", "关羽");
////        initDB.insert2DB(ne, "哪吒", "桀骜炎枪", "战士", "80", "30", "60", "50", "刘邦", "后羿");
////        initDB.insert2DB(wu, "武则天", "女帝", "法师", "20", "10", "100", "60", "庄周", "关羽");
////        initDB.insert2DB(sun, "孙悟空", "齐天大圣", "战士", "50", "80", "50", "40", "墨子", "妲己");
////        initDB.insert2DB(c, "曹操", "鲜血枭雄", "战士", "60", "60", "50", "40", "孙膑", "王昭君");
//    }

    //EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Herolist h) {
        //add to collectionlist
        myAdapter.addItem(h);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);
        initDB = new HeroDataBase(this);

        SharedPreferences sharedPreferences = getSharedPreferences("FirstRun",0);
        Boolean first_run = sharedPreferences.getBoolean("First",true);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        if (first_run) {
            initDB.initData();
            editor.putBoolean("First", false);
            editor.commit();
        }
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.float_but);
        listView = (ListView) findViewById(R.id.listview);
        Button search_but = (Button) findViewById(R.id.search_but);
        final EditText search_edit = (EditText) findViewById(R.id.search);
        myAdapter = new MyAdaptor(this, initDB);
        listView.setAdapter(myAdapter);

        Random random = new Random();
        int num = random.nextInt(myAdapter.getCount());
        Bundle bundle = new Bundle();
        bundle.putSerializable("recommend1",myAdapter.getItem(num));
        Intent widgetBroadcast = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        widgetBroadcast.putExtras(bundle);
        widgetBroadcast.setComponent(new ComponentName("com.example.baixiaoqi.myapplication",
                "com.example.baixiaoqi.myapplication.NewAppWidget"));
        sendBroadcast(widgetBroadcast);

        final String STATICACTION = "com.example.baixiaoqi.myapplication.MyStaticFilter";
        Intent intentBroadcast = new Intent(STATICACTION);
        intentBroadcast.putExtras(bundle);
        intentBroadcast.setComponent(new ComponentName("com.example.baixiaoqi.myapplication",
                "com.example.baixiaoqi.myapplication.StaticReceiver"));
        sendBroadcast(intentBroadcast);
        

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DetailActivity.class);
//                intent.putExtra("add", "true");
                Bundle bundle = new Bundle();
//                Herolist hero1 = new Herolist("1","1","1","1","1","1","1","1","1","1");
//                bundle.putSerializable("add", hero1);
                bundle.putString("add", "true");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        search_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_name = search_edit.getText().toString();
                if (initDB.ifHeroExist(search_name)) {
                    Herolist serchHero = initDB.queryHero(search_name);
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("add", "false");
                    bundle.putSerializable("query", (Serializable) serchHero);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "您所查找的英雄不存在", Toast.LENGTH_SHORT).show();
                    search_edit.setText("");
                }

            }
        });

        //短按
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("add", "false");
                bundle.putSerializable("query", (Serializable) myAdapter.getItem(position));
                intent.putExtras(bundle);
                setResult(1, intent);
                startActivityForResult(intent, 0);
                Log.i("AAAAAAAAAAAAAAAAAAAAAAA", "IIIIIIIIIIIII");
            }
        });

        //长按
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this).setTitle("提示").setMessage("是否确定删除" + myAdapter.getItem(position).getName() + "？").setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    myAdapter.removeItem(position);
                                }
                            }).setNegativeButton("取消", null).create().show();

                return true;
            }
        });
    }

}
