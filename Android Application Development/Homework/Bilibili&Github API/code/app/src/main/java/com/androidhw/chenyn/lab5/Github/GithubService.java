package com.androidhw.chenyn.lab5.Github;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

// 不是Class 而是 interface
public interface GithubService  {
    // 这里的List<Repo>即为最终返回的类型，需要保持一致才可解析
    // 之所以使用一个List包裹是因为该接口返回的最外层是一个数组
    @GET("/users/{user_name}/repos")
    Observable<List<GithubRepoObj>> getRepo(@Path("user_name") String user_name);

    @GET("/repos/{user_name}/{repo}/issues")
    Observable<List<GithubIssueObj>> getIssue(@Path("user_name") String user_name, @Path("repo") String repo);
}
