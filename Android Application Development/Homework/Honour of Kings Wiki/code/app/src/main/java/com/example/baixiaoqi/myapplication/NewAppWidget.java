package com.example.baixiaoqi.myapplication;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context,Intent intent) {//接收广播
        super.onReceive(context,intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE")) {
            Bundle bundle = intent.getExtras();
            Log.d("receive_widget",((Herolist)bundle.getSerializable("recommend1")).getName());
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.new_app_widget);
            if (bundle.getSerializable("recommend1") != null) {
                Log.d("setText_widget","sucess");
                remoteViews.setTextViewText(R.id.appwidget_text,"今日推荐"+((Herolist)bundle.getSerializable("recommend1")).getName());//发送广播
            }
            //跳回主页
            Intent intent1 = new Intent(context,MainActivity.class);
            Bundle bundle1 = new Bundle();
            String s[] = new String[8];
            if (bundle.getSerializable("recommend1") != null) {
                s[0] = ((Herolist)bundle.getSerializable("recommend1")).getName();
                //Log.d("yy",s[0]);
                s[1] = ((Herolist)bundle.getSerializable("recommend1")).getSurvivabilityValue();
                s[2] = ((Herolist)bundle.getSerializable("recommend1")).getSuppressHeroes();
                s[3] = ((Herolist)bundle.getSerializable("recommend1")).getStartingAbilityValue();
                s[4] = ((Herolist)bundle.getSerializable("recommend1")).getSkillEffectivenessValue();
                s[5] = ((Herolist)bundle.getSerializable("recommend1")).getTitle();
                s[6] = ((Herolist)bundle.getSerializable("recommend1")).getBestPartnerHero();
                s[7] = ((Herolist)bundle.getSerializable("recommend1")).getPosition();
            }
            bundle1.putStringArray("msg",s);
            intent1.putExtras(bundle1);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.appwidget_star,pendingIntent);
            Log.d("click","I have clicked");
            ComponentName me = new ComponentName(context,NewAppWidget.class);
            appWidgetManager.updateAppWidget(me,remoteViews);
        }
    }

    /**

     * 用来间隔的更新App Widget，间隔时间用AppWidgetProviderInfo里的updatePeriodMillis属性定义(单位为毫秒)。

     * 注意：SDK1.5之后此android:updatePeriodMillis就失效了，要自己创建service更新。

     * 这个方法也会在用户添加App Widget时被调用，因此它应该执行基础的设置，比如为视图定义事件处理器并启动一个临时的服务Service，如果需要的话。

     * 但是，如果你已经声明了一个配置活动，这个方法在用户添加App Widget时将不会被调用，

     * 而只在后续更新时被调用。配置活动应该在配置完成时负责执行第一次更新。

     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int id:appWidgetIds) {
            RemoteViews updateView = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);//实例化RemoteView,其对应相应的Widget布局
            Intent i = new Intent(context, DetailActivity.class);
            PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            updateView.setOnClickPendingIntent(R.id.appwidget_star, pi); //设置点击事件
            ComponentName me = new ComponentName(context, NewAppWidget.class);
            appWidgetManager.updateAppWidget(me, updateView);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

