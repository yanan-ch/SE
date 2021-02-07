package com.chenyn.androidproject1;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

public class MainActivity extends AppCompatActivity {
    private List<FoodModel> foodList = new ArrayList<FoodModel>();
    private List<FoodModel> favorList = new ArrayList<FoodModel>();
    private ListViewAdapter listViewAdapter;
    boolean tag = true;
    RecyclerView foodListView;
    ListView favorListView;
    private FloatingActionButton fab;

    private int INTENT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);
        //功能函数
        initFoodList();
        initFavorList();
        fabClickHandle();
        sendStaticBroadcast(foodList.size());
        //注册订阅者(注册收藏列表所在Activity为订阅者)
        EventBus.getDefault().register(this);
    }
    //发送静态广播
    private void sendStaticBroadcast(int n) {
        final String STATICACTION = "com.chenyn.androidproject1.MyStaticFilter";
        Random random = new Random();
        //返回一个0到n-1的整数
        int position = random.nextInt(n);
        Intent intentBroadcast = new Intent(STATICACTION); //定义Intent
        intentBroadcast.setComponent(new ComponentName(getPackageName(),getPackageName()+".StaticReceiver"));
        Bundle bundles = new Bundle();
        bundles.putSerializable("broad", foodList.get(position));
        intentBroadcast.putExtras(bundles);
        sendBroadcast(intentBroadcast);
        //发送widget广播
        Intent widgetIntentBroadcast = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        widgetIntentBroadcast.putExtras(bundles);
        sendBroadcast(widgetIntentBroadcast);
    }
    //声明并注释订阅方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FoodModel foodModel) {
/*        FoodModel newfoodItem = new FoodModel(foodModel.getFoodName(), foodModel.getCircleContent(), foodModel.getFoodKind(), foodModel.getNutriment(), foodModel.getBgColor());
        newfoodItem.setIfFavor(true);*/
        favorList.add(foodModel);
        /*tag = false;*/
        foodListView.setVisibility(View.INVISIBLE);
        favorListView.setVisibility(View.VISIBLE);
        fab = findViewById(R.id.fabOnMain);
        fab.setImageResource(R.drawable.mainpage);
        listViewAdapter.notifyDataSetChanged();
    }
    //食品列表初始化
    private void initFoodList() {
        foodListView = (RecyclerView)findViewById(R.id.myRecyclerView);
        foodListView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        foodList.add(new FoodModel("大豆", "粮", "粮食", "蛋白质", "#BB4C3B"));
        foodList.add(new FoodModel("十字花科蔬菜", "蔬", "蔬菜", "维生素C", "#C48D30"));
        foodList.add(new FoodModel("牛奶", "饮", "饮品", "钙", "#4469B0"));
        foodList.add(new FoodModel("海鱼", "肉", "肉食", "蛋白质", "#20A17B"));
        foodList.add(new FoodModel("菌菇类", "蔬", "蔬菜", "微量元素", "#BB4C3B"));
        foodList.add(new FoodModel("番茄", "蔬", "蔬菜", "番茄红素", "#4469B0"));
        foodList.add(new FoodModel("胡萝卜", "蔬", "蔬菜", "胡萝卜素", "#20A17B"));
        foodList.add(new FoodModel("荞麦", "粮", "粮食", "膳食纤维", "#BB4C3B"));
        foodList.add(new FoodModel("鸡蛋", "杂", "杂", "几乎所有营养物质", "#C48D30"));
        //添加适配器
        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter<FoodModel>(MainActivity.this, R.layout.fooditem, foodList) {
            @Override
            public void convert(RecyclerViewHolder holder, FoodModel s) {
                TextView circleContent = holder.getView(R.id.circleContent);
                circleContent.setText(s.getCircleContent());
                TextView foodName = holder.getView(R.id.foodName);
                foodName.setText(s.getFoodName());
            }
        };
        //添加动画效果与填充数据
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(recyclerViewAdapter);
        scaleInAnimationAdapter.setDuration(1000);
        foodListView.setAdapter((scaleInAnimationAdapter));
        foodListView.setItemAnimator(new OvershootInLeftAnimator());
        //Item单击与长按事件处理
        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                navigateToDetail(position, foodList);
            }
            @Override
            public void onLongClick(int position) {
                String deleteMsg = ((FoodModel)recyclerViewAdapter.getItem(position)).getFoodName();
                foodList.remove(position);
                recyclerViewAdapter.notifyItemRemoved(position);
                Toast.makeText(MainActivity.this, "删除" + deleteMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }
    //初始化收藏夹
    private void initFavorList() {
        favorListView = (ListView)findViewById(R.id.myListView);
        favorList.add(new FoodModel("收藏夹", "*", "", "", ""));
        listViewAdapter = new ListViewAdapter(MainActivity.this, favorList);
        favorListView.setAdapter(listViewAdapter);
        //处理单击事件
        favorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    navigateToDetail(i, favorList);
                }
            }
        });
        // 处理长按事件
        favorListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                if (position != 0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("删除");
                    dialog.setMessage("确定删除" + favorList.get(position).getFoodName()+"?");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            favorList.remove(position);
                            listViewAdapter.notifyDataSetChanged();
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { }
                    });
                    dialog.show();
                }
                return true;
            }
        });
    }
    //跳转到详情页面
    private void navigateToDetail(int position, List<FoodModel> list) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, DetailActivity.class);
        Bundle bundle = new Bundle();
        String string[] = new String [7];
        string[0] = list.get(position).getFoodName();
        string[1] = list.get(position).getCircleContent();
        string[2] = list.get(position).getFoodKind();
        string[3] = list.get(position).getNutriment();
        string[4] = list.get(position).getBgColor();
        string[5] = list.get(position).getIfStar() ? "stared" : "notStared";
        string[6] = list.get(position).getIfFavor() ? "favored" : " notFavored";
        bundle.putStringArray("foodMsg", string);
        intent.putExtras(bundle);
        INTENT_REQUEST_CODE = position;
        startActivityForResult(intent, INTENT_REQUEST_CODE);
    }
    //点击fab切换页面
    private void fabClickHandle() {
        fab = findViewById(R.id.fabOnMain);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(tag == true){
                    findViewById(R.id.myRecyclerView).setVisibility(View.GONE);
                    findViewById(R.id.myListView).setVisibility(View.VISIBLE);
                    tag = false;
                    fab.setImageResource(R.drawable.mainpage);
                }
                else{
                    findViewById(R.id.myRecyclerView).setVisibility(View.VISIBLE);
                    findViewById(R.id.myListView).setVisibility(View.GONE);
                    tag = true;
                    fab.setImageResource(R.drawable.collect);
                }
            }
        });
    }
    //跳转返回处理
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String returnMsg[];
        if (resultCode == RESULT_OK) {
            if (requestCode == INTENT_REQUEST_CODE) {
                Bundle bundle = data.getExtras();
                returnMsg = bundle.getStringArray("returnMsg");
                if (returnMsg[6].equals("favored")) {
                    FoodModel newfoodItem = new FoodModel(returnMsg[0], returnMsg[1], returnMsg[2], returnMsg[3], returnMsg[4]);
                    newfoodItem.setIfFavor(true);
                    favorList.add(newfoodItem);
                    listViewAdapter.notifyDataSetChanged();
                }
            }
        }
    }*/
    //注销订阅者(退出时要注销订阅者)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
