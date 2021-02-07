package com.androidhw.chenyn.lab5.Bilibili;

import com.androidhw.chenyn.lab5.R;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ArrayList<RecycleInfo> data = new ArrayList<>();
    private RecyclerViewAdapter recyclerViewAdapter;
    private EditText editText;

    //RxJAVA变量
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Observable<RecycleInfo> observable = Observable.create(new ObservableOnSubscribe<RecycleInfo>() {
        @Override
        public void subscribe(ObservableEmitter<RecycleInfo> observableEmitter) throws Exception {
            String baseUrl = "https://space.bilibili.com/ajax/top/showTop?mid=";
            String requestUrl = baseUrl + editText.getText().toString();
            // 新建一个URL对象
            URL url = new URL(requestUrl);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接主机超时时间
            urlConn.setConnectTimeout(5 * 1000);
            //设置从主机读取数据超时
            urlConn.setReadTimeout(5 * 1000);
            // 设置是否使用缓存  默认是true
            urlConn.setUseCaches(true);
            // 设置为Post请求
            urlConn.setRequestMethod("GET");
            //urlConn设置请求头信息
            //设置请求中的媒体类型信息。
            urlConn.setRequestProperty("Content-Type", "application/json");
            //设置客户端与服务连接类型
            urlConn.addRequestProperty("Connection", "Keep-Alive");
            // 开始连接
            urlConn.connect();
            // 判断请求是否成功
            //InputStream转String
            if (urlConn.getResponseCode() == 200) {
                BufferedInputStream bis = new BufferedInputStream((InputStream) urlConn.getContent());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int result = bis.read();
                while (result != -1) {
                    byteArrayOutputStream.write((byte) result);
                    result = bis.read();
                }
                observableEmitter.onNext(new Gson().fromJson(byteArrayOutputStream.toString(), RecycleInfo.class));
                RecycleInfo v = new Gson().fromJson(byteArrayOutputStream.toString(), RecycleInfo.class);
            }

            observableEmitter.onComplete();
        }

    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button search_but = findViewById(R.id.search_but);
        editText = findViewById(R.id.search);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter<RecycleInfo>(MainActivity.this, R.layout.item);
        recyclerView.setAdapter(recyclerViewAdapter);


        search_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "EditText is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    //订阅观察者
                    DisposableObserver<RecycleInfo> disposableObserver = new DisposableObserver<RecycleInfo>() {
                        @Override
                        public void onNext(RecycleInfo recycleInfo) {
                            if (recycleInfo.getStatus()) {
                                //添加到显示结果的列表中
                                recyclerViewAdapter.addItem(recycleInfo);
                            } else//不执行
                                Toast.makeText(MainActivity.this, "格式错误", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (e instanceof UnknownHostException)
                                Toast.makeText(MainActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                            else if (e instanceof com.google.gson.JsonSyntaxException) {
                                Toast.makeText(MainActivity.this, "当前用户不存在", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onComplete() {
                            Toast.makeText(MainActivity.this, "搜索完成", Toast.LENGTH_SHORT).show();
                        }
                    };
                    //在新线程监听
                    observable.subscribeOn(Schedulers.newThread())
                            //在主线程更新
                            .observeOn(AndroidSchedulers.mainThread())
                            //绑定
                            .subscribe(disposableObserver);
                    //管理DisposableObserver的容器
                    compositeDisposable.add(disposableObserver);
                }
            }
        });
    }
}
