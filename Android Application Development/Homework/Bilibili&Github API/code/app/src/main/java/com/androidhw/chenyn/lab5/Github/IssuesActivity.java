package com.androidhw.chenyn.lab5.Github;

import com.androidhw.chenyn.lab5.R;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class IssuesActivity extends AppCompatActivity {
    private final String baseURL = "https://api.github.com";
    private RecyclerView recycler_view_list;
    private GithubIssueRecyclerAdapter githubIssueRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        //recyclerView的设置
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_view_list = findViewById(R.id.issues_recycler_view_list);
        githubIssueRecyclerAdapter = new GithubIssueRecyclerAdapter();
        recycler_view_list.setAdapter(githubIssueRecyclerAdapter);
        recycler_view_list.setLayoutManager(mLayoutManager);
        //获取ISSUES
        getGithubIssues(getIntent().getStringExtra("username"),
                getIntent().getStringExtra("repo"));

    }
    //获取用户某一个仓库所有问题的方法
    public void getGithubIssues(String username, String repo) {
        //先声明OkHttpClient，因为retrofit时基于okhttp的，在这可以设置一些超时参数等
        OkHttpClient build = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                // 设置json数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(build)
                .build();
        GithubService gitHubService = retrofit.create(GithubService.class);
        DisposableObserver<List<GithubIssueObj>> disposableObserver = new DisposableObserver<List<GithubIssueObj>>() {
            @Override
            public void onNext(List<GithubIssueObj> githubIssueObjs) {
                //清除掉之前的列表
                githubIssueRecyclerAdapter.reset();
                if(githubIssueObjs.isEmpty()){
                    Toast.makeText(IssuesActivity.this, "该仓库不存在任何Issue", Toast.LENGTH_SHORT).show();
                    return;
                }
                //添加到显示结果的列表中
                for(GithubIssueObj g : githubIssueObjs){
                    githubIssueRecyclerAdapter.addItem(g);
                }
            }
            @Override
            public void onComplete() {
                //Toast.makeText(IssuesActivity.this, "搜索完成", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        };
        gitHubService.getIssue(username, repo)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                //相当重要，rxjava:2.2.4不是Subscriber而是DisposableObserver
                .subscribe(disposableObserver);
    }
}
