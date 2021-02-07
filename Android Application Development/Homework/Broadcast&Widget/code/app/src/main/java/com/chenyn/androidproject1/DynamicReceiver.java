package com.chenyn.androidproject1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

public class DynamicReceiver extends BroadcastReceiver {
    private final String DYNAMICACTION = "com.chenyn.androidproject1.MyDynamicFilter";
    private final String WIDGETDYNAMICACTION = "com.chenyn.androidproject1.MyWidgetDynamicFilter";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DYNAMICACTION)) {    //动作检测
            Bundle bundle = intent.getExtras();
            FoodModel foodModel = (FoodModel) bundle.getSerializable("dynamicBroad");
            Intent intent1 = new Intent(context, MainActivity.class);
            Bundle bundle1 = new Bundle();
            intent1.putExtras(bundle);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            //获取状态通知栏管理
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            String CHANNEL_ID = "Channel_02";
            NotificationChannel mChannel = null;
            if (mChannel == null) {
                String name = "channel_02";//渠道名字
                //HIGH或者MAX才能弹出通知到屏幕上
                int importance = NotificationManager.IMPORTANCE_HIGH;
                mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                manager.createNotificationChannel(mChannel);
            }
            Notification.Builder builder = new Notification.Builder(context, CHANNEL_ID);
            //对Builder进行配置
            //设置通知栏标题
            builder.setContentTitle("已收藏")
                    //设置通知栏显示内容
                    .setContentText(foodModel.getFoodName())
                    //设置通知小ICON（通知栏）
                    .setSmallIcon(R.drawable.back)
                    //传递内容
                    .setContentIntent(contentIntent)
                    //设置这个标志当用户单击面板就可以让通知将自动取消
                    .setAutoCancel(true);
            //绑定Notification，发送通知请求
            Notification notify = builder.build();
            manager.notify(0,notify);
        }
        else if (intent.getAction().equals(WIDGETDYNAMICACTION)) {
            Bundle bundle = intent.getExtras();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.new_app_widget);
            if (bundle.getSerializable("dynamicBroad") != null) {
                remoteViews.setTextViewText(R.id.appwidget_text, "已收藏 " + ((FoodModel) bundle.getSerializable("dynamicBroad")).getFoodName());
            }
            //跳回收藏夹
            Intent intent1 = new Intent(context, MainActivity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putString("tag","marked");
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent1.putExtras(bundle1);
            PendingIntent pendingIntent=PendingIntent.getActivity(context,0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
            ComponentName myWidget = new ComponentName(context,NewAppWidget.class);
            appWidgetManager.updateAppWidget(myWidget, remoteViews);
        }
    }
}
