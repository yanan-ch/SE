package com.example.baixiaoqi.myapplication;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.RemoteViews;

public class DynamicReceiver extends BroadcastReceiver {
    private static final String DYNAMICACTION = "com.example.baixiaoqi.myapplication.MyDynamicFilter";
    private static final String WIDGETDTNAMICACTION = "com.example.baixiaoqi.myapplication.WidgetDynamicFilter";
    @SuppressLint("WrongConstant")
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DYNAMICACTION)) {    //动作检测
            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            String CHANNEL_ID = "channel_02";
            NotificationChannel notificationChannel = null;
            if (notificationChannel == null) {
                String name = "my_channel_02";//渠道名字
                String description = "my_package_second_channel";//渠道解释说明
                //HIGH或者MAX才能弹出通知到屏幕上
                int importance = NotificationManager.IMPORTANCE_MAX;
                notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);
                notificationChannel.setDescription(description);
                notificationChannel.enableLights(true);
                manager.createNotificationChannel(notificationChannel);
            }
            Bundle bundle = intent.getExtras();
            Herolist hero = (Herolist) bundle.getSerializable("recommend2");
            //TODO:添加Notification部分
            Notification.Builder builder = new Notification.Builder(context,CHANNEL_ID);
            Intent intent1 = new Intent(context,DetailActivity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putString("add","false");
            bundle1.putSerializable("query", hero);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent1.putExtras(bundle1);
            PendingIntent contentIntent = PendingIntent.getActivities(context,0, new Intent[]{intent1},PendingIntent.FLAG_UPDATE_CURRENT);
            //Log.d("baiii","111");
            //Log.d("myuuuuuuuuu", myCollections.getName());
            builder.setContentTitle("已添加").setContentText(hero.getName()).setTicker("您有一条新消息").setSmallIcon(R.mipmap.empty_star).setContentIntent(contentIntent).setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL);
            Notification notification = builder.build();
            manager.notify(0,notification);
        }
        else if (intent.getAction().equals(WIDGETDTNAMICACTION)) {
            Bundle bundle = intent.getExtras();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.new_app_widget);
            if (bundle.getSerializable("recommend2") != null) {
                Log.d("dynamic widget send","success");
                remoteViews.setTextViewText(R.id.appwidget_text,"已添加"+((Herolist)bundle.getSerializable("recommend2")).getName());
            }
            Intent intent1 = new Intent(context,DetailActivity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putString("tag","recommend2");
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent1.putExtras(bundle1);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.appwidget_star,pendingIntent);
            ComponentName me = new ComponentName(context,NewAppWidget.class);
            appWidgetManager.updateAppWidget(me,remoteViews);
        }
    }
}