package com.chenyn.androidproject1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//静态广播
public class StaticReceiver extends BroadcastReceiver {
    private final String STATICACTION = "com.chenyn.androidproject1.MyStaticFilter";
    //当接收到对应广播时进行数据处理，产生通知
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(STATICACTION)){
            Bundle bundle = intent.getExtras();
            FoodModel foodModel = (FoodModel) bundle.getSerializable("broad");
            Intent intent1 = new Intent(context, DetailActivity.class);
            Bundle bundle1 = new Bundle();
            String string[] = new String [7];
            string[0] = ((FoodModel)bundle.getSerializable("broad")).getFoodName();
            string[1] = ((FoodModel)bundle.getSerializable("broad")).getCircleContent();
            string[2] = ((FoodModel)bundle.getSerializable("broad")).getFoodKind();
            string[3] = ((FoodModel)bundle.getSerializable("broad")).getNutriment();
            string[4] = ((FoodModel)bundle.getSerializable("broad")).getBgColor();
            string[5] = ((FoodModel)bundle.getSerializable("broad")).getIfStar() ? "stared" : "notStared";
            string[6] = ((FoodModel)bundle.getSerializable("broad")).getIfFavor() ? "favored" : " notFavored";
            bundle1.putStringArray("foodMsg", string);
            intent1.putExtras(bundle1);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            //获取状态通知栏管理
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            String CHANNEL_ID = "Channel_01";
            NotificationChannel mChannel = null;
            if (mChannel == null) {
                String name = "channel_01";//渠道名字
                //HIGH或者MAX才能弹出通知到屏幕上
                int importance = NotificationManager.IMPORTANCE_HIGH;
                mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                manager.createNotificationChannel(mChannel);
            }
            Notification.Builder builder = new Notification.Builder(context, CHANNEL_ID);
            //对Builder进行配置
            //设置通知栏标题
            builder.setContentTitle("今日推荐")
                    //设置通知栏显示内容
                    .setContentText(((FoodModel)bundle.getSerializable("broad")).getFoodName())
                    //设置通知小ICON（通知栏）
                    .setSmallIcon(R.drawable.empty_star)
                    //传递内容
                    .setContentIntent(contentIntent)
                    //设置这个标志当用户单击面板就可以让通知将自动取消
                    .setAutoCancel(true);
            //绑定Notification，发送通知请求
            Notification notify = builder.build();
            manager.notify(0,notify);
        }
    }
}
