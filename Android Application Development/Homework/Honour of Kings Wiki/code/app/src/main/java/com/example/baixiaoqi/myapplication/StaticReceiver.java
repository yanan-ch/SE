package com.example.baixiaoqi.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.Serializable;

public class StaticReceiver extends BroadcastReceiver {
    private static final String STATICACTION = "com.example.baixiaoqi.myapplication.MyStaticFilter";
    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Static","onreceive");
        if (intent.getAction().equals(STATICACTION)){
            //Log.d("222","comein");
            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

            Notification.Builder builder;

            if (Build.VERSION.SDK_INT >= 26) {
                String CHANNEL_ID = "channel_01";
                NotificationChannel notificationChannel = null;

                String name = "my_channel_01";//渠道名字
                String description = "my_package_first_channel";//渠道解释说明
                //HIGH或者MAX才能弹出通知到屏幕上
                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);
                notificationChannel.setDescription(description);
                notificationChannel.enableLights(true);
                manager.createNotificationChannel(notificationChannel);
                builder = new Notification.Builder(context,CHANNEL_ID);
            }
            else {
                builder = new Notification.Builder(context);
            }
            Bundle bundle = intent.getExtras();
            Herolist hero = (Herolist) bundle.getSerializable("recommend1");
            //TODO:添加Notification部分
            Intent intent1 = new Intent(context,DetailActivity.class);
            Bundle bundle1 = new Bundle();
//            String s[] = new String[8];
//            s[0] = ((Herolist)bundle.getSerializable("recommend1")).getName();
//            //Log.d("yy",s[0]);
//            s[1] = ((Herolist)bundle.getSerializable("recommend1")).getSurvivabilityValue();
//            s[2] = ((Herolist)bundle.getSerializable("recommend1")).getSuppressHeroes();
//            s[3] = ((Herolist)bundle.getSerializable("recommend1")).getStartingAbilityValue();
//            s[4] = ((Herolist)bundle.getSerializable("recommend1")).getSkillEffectivenessValue();
//            s[5] = ((Herolist)bundle.getSerializable("recommend1")).getTitle();
//            s[6] = ((Herolist)bundle.getSerializable("recommend1")).getBestPartnerHero();
//            s[7] = ((Herolist)bundle.getSerializable("recommend1")).getPosition();
//            s[8] = (Herolist)bundle.getSerializable("recommend1").get
            bundle1.putSerializable("query", (Serializable) hero);
            bundle1.putString("add", "false");
            intent1.putExtras(bundle1);
            PendingIntent contentIntent = PendingIntent.getActivities(context,0, new Intent[]{intent1},PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentTitle("今日推荐").setContentText(((Herolist)bundle.getSerializable("recommend1")).getName()).setTicker("您有一条新消息").setSmallIcon(R.mipmap.empty_star).setContentIntent(contentIntent).setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL);
            Notification notification = builder.build();
            manager.notify(0,notification);
        }
        //Log.d("333","comeout");
    }
}