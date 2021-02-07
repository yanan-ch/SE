package com.androidhw.chenyn.lab5.Bilibili;

import com.androidhw.chenyn.lab5.R;
import com.androidhw.chenyn.lab5.MyViewHolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RecyclerViewAdapter<T> extends RecyclerView.Adapter<MyViewHolder>{
    private Context context;
    private int layoutId;
    private ArrayList<RecycleInfo> data;



    public RecyclerViewAdapter(Context _context, int _layoutId) {
        this.context = _context;
        this.layoutId = _layoutId;
        this.data = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //convert(holder, (T) data.get(position)); // convert函数需要重写，下面会讲
        ((TextView)holder.getView(R.id.result_play)).setText(data.get(position).getIdata().getPlay());
        ((TextView)holder.getView(R.id.result_review)).setText(data.get(position).getIdata().getVideo_review());
        ((TextView)holder.getView(R.id.result_duration)).setText(data.get(position).getIdata().getDuration());
        ((TextView)holder.getView(R.id.result_create)).setText(data.get(position).getIdata().getCreate());
        ((TextView)holder.getView(R.id.title)).setText(data.get(position).getIdata().getTitle());
        ((TextView)holder.getView(R.id.content)).setText(data.get(position).getIdata().getContent());

        //当加载完成时，设置拖动条可拖动
        @SuppressLint("HandlerLeak")
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        //设置封面
                        ((ImageView)holder.getView(R.id.cover)).setImageBitmap(holder.result_bitmap);
                        //隐藏进度条
                        holder.getView(R.id.progressBar).setVisibility(View.GONE);
                        //显示封面
                        holder.getView(R.id.cover).setVisibility(View.VISIBLE);
                }

            }
        };


        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(data.get(position).getIdata().getCover());
                    HttpURLConnection conn = null;
                    conn = (HttpURLConnection)url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    if(conn.getResponseCode() == 200){
                        InputStream inputStream = conn.getInputStream();
                        holder.result_bitmap = BitmapFactory.decodeStream(inputStream);
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    //利用Message把图片发给Handler
                    Message msg = Message.obtain();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }

            }
        }.start();
    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        return 0;
    }


    public Object getItem(int i) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        return data.get(i);
    }

    public void addItem(RecycleInfo recycleInfo){
        if(data != null){
            data.add(recycleInfo);
            notifyItemInserted(data.size() - 1);
        }
    }
}

