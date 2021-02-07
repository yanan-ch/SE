package com.chenyn.androidproject1;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    private FoodModel selectedItem;
    private String inform[];
    private Map<String, Integer> colors = new LinkedHashMap<String, Integer>();
    private final String DYNAMICACTION = "com.chenyn.androidproject1.MyDynamicFilter";
    private DynamicReceiver dynamicReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_detail);
        //功能函数
        initDetail();
        setBottomListView();
        addFavorList();
        backClick();
        ifStarred();
        //注册动态广播
        IntentFilter dynamicFilter = new IntentFilter();
        dynamicFilter.addAction(DYNAMICACTION);    //添加动态广播的Action
        dynamicReceiver = new DynamicReceiver();
        registerReceiver(dynamicReceiver, dynamicFilter);    //注册自定义动态广播消息
        //Widget
        IntentFilter widget_dynamic_filter = new IntentFilter();
        widget_dynamic_filter.addAction("com.chenyn.androidproject1.MyWidgetDynamicFilter");
        DynamicReceiver widgetDynamicReceiver = new DynamicReceiver(); //添加动态广播的Action
        registerReceiver(widgetDynamicReceiver, widget_dynamic_filter); //注册自定义动态广播信息
    }
    //取消动态广播
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dynamicReceiver);
    }
    //初始化详情页面
    private void initDetail() {
        Bundle bundle = this.getIntent().getExtras();
        inform = bundle.getStringArray("foodMsg");
        selectedItem = new FoodModel(inform[0], inform[1], inform[2], inform[3], inform[4]);
        TextView foodName = findViewById(R.id.foodName);
        foodName.setText(selectedItem.getFoodName());
        TextView foofKind = findViewById(R.id.foodKind);
        foofKind.setText(selectedItem.getFoodKind());
        TextView nutriment = findViewById(R.id.nutriment);
        nutriment.setText("富含" + selectedItem.getNutriment());
        RelativeLayout bgColor = findViewById(R.id.top);
        bgColor.setBackgroundColor(Color.parseColor(selectedItem.getBgColor()));
        ImageView ifStar = findViewById(R.id.starIcon);
        if(inform[5] == "stared") {
            ifStar.setImageResource(R.drawable.full_star);
            selectedItem.setIfStar(true);
        } else {
            ifStar.setImageResource(R.drawable.empty_star);
            selectedItem.setIfStar(false);
        }
    }
    //返回事件
    private void backClick() {
        final ImageView backBtn = findViewById(R.id.backIcon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                String string[] = new String [7];
                string[0] = selectedItem.getFoodName();
                string[1] = selectedItem.getCircleContent();
                string[2] = selectedItem.getFoodKind();
                string[3] = selectedItem.getNutriment();
                string[4] = selectedItem.getBgColor();
                string[5] = selectedItem.getIfStar() ? "stared" : "notStared";
                string[6] = selectedItem.getIfFavor() ? "favored" : "notFavored";
                bundle.putStringArray("returnMsg", string);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                DetailActivity.this.finish();
            }
        });
    }
    //设置底部ListView
    private void setBottomListView() {
        ListView bottomList = findViewById(R.id.detailListView);
        String[] operations = {"分享信息", "不感兴趣", "查看更多信息", "出错反馈"};

        final List<Map<String, Object>> msgList = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            Map<String, Object> tmp = new LinkedHashMap<>();
            tmp.put("msg", operations[i]);
            msgList.add(tmp);
        }
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, msgList, R.layout.detail_listview_item, new String[] {"msg"}, new int[] {R.id.detailItemView});
        bottomList.setAdapter(simpleAdapter);
    }
    //点击加入收藏夹
    private void addFavorList() {
        final ImageView collectBtn = findViewById(R.id.collectIcon);
        collectBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                selectedItem.setIfFavor(true);
                Toast.makeText(DetailActivity.this, "已收藏",Toast.LENGTH_SHORT).show();
                //传递事件(点击收藏图标时候，传递食品信息)
                EventBus.getDefault().post(selectedItem);
                //发送动态广播
                Bundle bundle = new Bundle();
                bundle.putSerializable("dynamicBroad", selectedItem);
                Intent intentBroadcast = new Intent();   //定义Intent
                intentBroadcast.setAction(DYNAMICACTION);
                intentBroadcast.putExtras(bundle);
                sendBroadcast(intentBroadcast);
                //Widget
                Intent widgetBroadcast = new Intent();   //定义Intent
                widgetBroadcast.setAction("com.chenyn.androidproject1.MyWidgetDynamicFilter");
                widgetBroadcast.putExtras(bundle);
                sendBroadcast(widgetBroadcast);
            }
        });
    }
    //空星实星切换
    private void ifStarred(){
        final ImageView ifStar = findViewById(R.id.starIcon);
        ifStar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(selectedItem.getIfStar() == false){
                    ifStar.setImageResource(R.drawable.full_star);
                    selectedItem.setIfStar(true);
                }
                else{
                    ifStar.setImageResource(R.drawable.empty_star);
                    selectedItem.setIfStar(false);
                }
            }
        });
    }
    //重载系统返回键事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            String string[] = new String [7];
            string[0] = selectedItem.getFoodName();
            string[1] = selectedItem.getCircleContent();
            string[2] = selectedItem.getFoodKind();
            string[3] = selectedItem.getNutriment();
            string[4] = selectedItem.getBgColor();
            string[5] = selectedItem.getIfStar() ? "stared" : "notStared";
            string[6] = selectedItem.getIfFavor() ? "favored" : "notFavored";
            bundle.putStringArray("returnMsg", string);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            DetailActivity.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
