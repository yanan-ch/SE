package com.chenyn.androidproject1;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews updateView = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);//实例化RemoteView,其对应相应的Widget布局
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        updateView.setOnClickPendingIntent(R.id.appwidget_star, pi); //设置点击事件
        ComponentName me = new ComponentName(context, NewAppWidget.class);
        appWidgetManager.updateAppWidget(me, updateView);
    }
    @Override
    public void onReceive(Context context, Intent intent ){
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if(intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE")){
            Bundle bundle = intent.getExtras();
            RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.new_app_widget);
            if (bundle.getSerializable("broad") != null) {
                remoteViews.setTextViewText(R.id.appwidget_text, "今日推荐 " + ((FoodModel) bundle.getSerializable("broad")).getFoodName());
            }
            //跳回主页面
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
            remoteViews.setOnClickPendingIntent(R.id.appwidget_text, contentIntent);
            ComponentName myWidget = new ComponentName(context, NewAppWidget.class);
            appWidgetManager.updateAppWidget(myWidget, remoteViews);
        }
    }
}

