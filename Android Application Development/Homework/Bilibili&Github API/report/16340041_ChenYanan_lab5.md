# 中山大学数据科学与计算机学院本科生实验报告
## （2018年秋季学期）
| 课程名称 | 手机平台应用开发 | 任课老师 | 郑贵锋 |
| :------------: | :-------------: | :------------: | :-------------: |
| 年级 | 2016 | 专业（方向） | 数字媒体 |
| 学号 | 16340041 | 姓名 | 陈亚楠 |
| 电话 | 18709548602 | Email | chenyn97@outlook.com |
| 开始日期 | 2018.12.13 | 完成日期 |2018.12.21|

---

## 一、实验题目

#### 个人项目5——Web API 

- 第十四周实验目的：
  - 学会使用HttpURLConnection请求访问Web服务；
  - 学习Android线程机制，学会线程更新UI；
  - 学会解析JSON数据；
  - 学习CardView布局技术；
- 第十五周实验目的：
  - 理解Restful接口；
  - 学会使用Retrofit2；
  - 复习使用RxJava；
  - 学会使用OkHttp；

---

## 二、实现内容

1.实现一个bilibili的用户视频信息获取软件：

- 再次输入用户id = 7，接着上次结果继续展示以上内容；
- 当手机处于飞行模式或关闭wifi和移动数据的网络连接时，需要弹Toast提示；
- 在图片加载出来前需要有一个加载条，不要求与加载进度同步；
- 展示图片/播放数/评论/时长/创建时间/标题/简介的内容；
- 使用2个CardView；

2.实现一个github用户repos以及issues应用：

- 输入用户名搜索该用户所有可提交issue的repo，每个item可点击；
- 每次点击搜索按钮都会清空上次搜索结果再进行新一轮的搜索；
- 获取repos时需要处理以下异常：HTTP 404 以及 用户没有任何repo；
- 只显示 has_issues = true 的repo（即fork的他人的项目不会显示）；
- repo的item可以点击跳转至下一界面；
- 该repo不存在任何issue时需要弹Toast提示；

---

## 三、课堂实验结果
### (1)实验截图

1.第14周：

- 打开程序主页面：

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548304383.png" width=400px>

- 输入用户id，点击搜索，网络没打开弹Toast提示网络连接失败：

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548304509.png" width=400px>

- 网络打开情况下，输入用户id，不存在相应数据的弹Toast提示：

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548304539.png" width=400px>

- 输入用户id = 2，点击搜索，展示图片/播放数/评论/时长/创建时间/标题/简介内容：

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548304574.png" width=400px>

- 再次输入用户id = 7，接着上次结果继续展示以上内容：

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548304585.png" width=400px>

2.第15周：

- 主界面两个跳转按钮分别对应两次作业：

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548305320.png" width=400px>

- 输入用户名搜索该用户所有可提交issue的repo：

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548305411.png" width=400px>

- 显示该repo所有的issues：

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548305923.png" width=400px>

- repo不存在任何issue时需要弹Toast提示：

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548305978.png" width=400px>

- 输入不存在的用户名提示HTTP 404：

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548306115.png" width=400px>

- 用户没有任何repo：

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548306242.png" width=400px>

### (2)实验步骤以及关键代码

#### Bilibili：

##### 1.使用CardView

引入CardView依赖：

```java
compile 'com.android.support:cardview-v7:28.+'
```

使用CardView：

```xml
<android.support.v7.widget.CardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:cardUseCompatPadding="true"
    app:cardCornerRadius="8dp"
    app:cardBackgroundColor="#f8f8ff">
</android.support.v7.widget.CardView>
```

##### 2.使用ProgressBar

```xml
<ProgressBar
             android:id="@+id/progressBar"
             android:layout_width="wrap_content"
             android:layout_height="wrap_content"
             android:visibility="visible"
             android:layout_gravity="center"/>
```

##### 3.建立RecycleInfo解析类

我们根据b站所提供的api解析所得到的json数组需要用到Gson，引入依赖：

```java
compile 'com.squareup.retrofit2:converter-gson:2.1.0'
```

RecycleInfo类如下：

```java
public class RecycleInfo {
    @SerializedName("status")
    private Boolean status;
    @SerializedName("data")
    private data idata;
    public static class data {
        @SerializedName("aid")
        private String aid;
        @SerializedName("state")
        private String state;
        @SerializedName("title")
        private String title;
        @SerializedName("cover")
        private String cover;
        @SerializedName("play")
        private String play;
        @SerializedName("duration")
        private String duration;
        //评论
        @SerializedName("video_review")
        private String video_review;
        @SerializedName("create")
        private String create;
        @SerializedName("content")
        private String content;
        public String getAid() {
            return aid;
        }
        public String getState() {
            return state;
        }
        public String getTitle() {
            return title;
        }
        public String getPlay() {
            return play;
        }
        public String getVideo_review() {
            return video_review;
        }
        public String getContent() {
            return content;
        }
        public String getCover() {
            return cover;
        }
        public String getCreate() {
            return create;
        }
        public String getDuration() {
            return duration;
        }
    }
    public Boolean getStatus() {
        return status;
    }
    public data getIdata() {
        return idata;
    }
}
```

##### 4.RecyclerView显示

这一部分在之前已经学习过，不做赘述：

```java
@Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {        ((TextView)holder.getView(R.id.result_play)).setText(data.get(position).getIdata().getPlay());                                                                       ((TextView)holder.getView(R.id.result_review)).setText(data.get(position).getIdata().getVideo_review());                                                                          ((TextView)holder.getView(R.id.result_duration)).setText(data.get(position).getIdata().getDuration());                                                                            ((TextView)holder.getView(R.id.result_create)).setText(data.get(position).getIdata().getCreate());                                                                             ((TextView)holder.getView(R.id.title)).setText(data.get(position).getIdata().getTitle());                                                                           ((TextView)holder.getView(R.id.content)).setText(data.get(position).getIdata().getContent());
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
```

##### 5.用户id输入判断事件

在进行搜索时，我们要对输入的用户id进行判断：

```java
search_but.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (editText.getText().toString().isEmpty()) {
        	Toast.makeText(MainActivity.this, "EditText is empty!",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(MainActivity.this, "网络连接失败",Toast.LENGTH_SHORT).show();
                else if (e instanceof com.google.gson.JsonSyntaxException) {
                    Toast.makeText(MainActivity.this, "当前用户不存在",Toast.LENGTH_SHORT).show();
                } else {
                	Toast.makeText(MainActivity.this, "未知错误",Toast.LENGTH_SHORT).show();
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
```

##### 6.使用HttpURLConnection请求访问Web服务

HttpURLConnection可以对URL进行检查， 避免错误地传输过多的数据，它还获取一些有关URL对象所引用的资源信息，如： HTTP状态、 头信息、 内容的长度、 类型和日期时间等。

该类的基本使用模式为：

- 创建新线程，在线程中进行网络访问防止阻塞主线程；
- 创建一个新的URL对象；
- 为这个URL资源打开Connection；
- 读取数据；
- 访问网络时需要捕获异常；

注意，网络访问不能在主线程中进行，容易造成阻塞或者 UI 瘫痪。这里我们使用RxJava进行网络访问：

```java
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
```

#### GIthub：

##### 1.建立两个model类

Repo：

```java
public class GithubRepoObj {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("open_issues_count")
    private int open_issues_count;
    @SerializedName("has_issues")
    private boolean has_issues;
    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    public int getOpen_issues_count() {
        return open_issues_count;
    }
    public String getDescription() {
        return description;
    }
    public boolean isHas_issues() {
        return has_issues;
    }
}
```

Issue：

```java
public class GithubIssueObj {
    @SerializedName("title")
    private String title;
    @SerializedName("state")
    private String state;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("body")
    private String body;
    public String getTitle() {
        return title;
    }
    public String getBody() {
        return body;
    }
    public String getCreated_at() {
        return created_at;
    }
    public String getState() {
        return state;
    }
}
```

##### 2.建立Interface类

这是一个服务接口，用来请求`GithubAPI`；这里使用了`retrofit2`的注解方法，将参数`user_name`放进`url`，再通过`GET`请求，返回具体的`repository`列表，被观察者的对象是是一个`GithubRepoObj`的列表：

```java
public interface GithubService  {
    // 这里的List<Repo>即为最终返回的类型，需要保持一致才可解析
    // 之所以使用一个List包裹是因为该接口返回的最外层是一个数组
    @GET("/users/{user_name}/repos")
    Observable<List<GithubRepoObj>> getRepo(@Path("user_name") String user_name);

    @GET("/repos/{user_name}/{repo}/issues")
    Observable<List<GithubIssueObj>> getIssue(@Path("user_name") String user_name, @Path("repo") String repo);
}
```

##### 3.获取用户所有仓库

- 使用OkHttp主要目的是承载Retrofit的请求，本次实验只需要简单的设置超时时间等即可：

  ```java
  //先声明OkHttpClient，因为retrofit时基于okhttp的，在这可以设置一些超时参数等
  OkHttpClient build = new OkHttpClient.Builder()
      .connectTimeout(10, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .writeTimeout(10, TimeUnit.SECONDS)
      .build();
  ```

- 使用Retrofit，retrofit设置网络请求的Url基地址，添加支持RxJava的转换工厂：

  ```java
  Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(baseURL)
      // 设置json数据解析器
      .addConverterFactory(GsonConverterFactory.create())
      //这是RXJAVA2的版本
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .client(build)
      .build();
  ```

- 根据之前的接口定义来创建repoObservable，在在UI线程来改变UI，在io线程来进行网络访问：

  ```java
  DisposableObserver<List<GithubRepoObj>> disposableObserver = new DisposableObserver<List<GithubRepoObj>>() {
      @Override
      public void onNext(List<GithubRepoObj> githubRepoObjs) {
          //清空之前的搜索结果
          githubRecyclerAdapter.reset();
          //用户仓库为空
          if(githubRepoObjs.isEmpty()){
              Toast.makeText(RepoActivity.this, "该用户不存在任何Repository",Toast.LENGTH_SHORT).show();
          return;
      }
      //添加到显示结果的列表中
      for(GithubRepoObj g : githubRepoObjs){
          //过滤掉fork他人的项目
          if(g.isHas_issues())
          	githubRecyclerAdapter.addItem(g);
          }
      }
      @Override
      public void onComplete() {
      	Toast.makeText(RepoActivity.this, "搜索完成",Toast.LENGTH_SHORT).show();
      }
      @Override
      public void onError(Throwable e) {
          if(e instanceof UnknownHostException)
              Toast.makeText(RepoActivity.this, "网络连接失败",Toast.LENGTH_SHORT).show();
              //404错误
          else if(e.toString().equals("retrofit2.adapter.rxjava2.HttpException: HTTP 404 Not Found")){
          	Toast.makeText(RepoActivity.this, "404 Not Found",Toast.LENGTH_SHORT).show();
          } else{
          	Toast.makeText(RepoActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
          }
      e.printStackTrace();
      }
  };
  ```

  ##### 4.获取Issue

  获取Issue的方法与获取用户所有仓库的方法类似：

  ```java
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
                  Toast.makeText(IssuesActivity.this, "该仓库不存在任何Issue",Toast.LENGTH_SHORT).show();
              return;
          	}
              //添加到显示结果的列表中
              for(GithubIssueObj g : githubIssueObjs){
                  githubIssueRecyclerAdapter.addItem(g);
              }
          }
          @Override
          public void onComplete() { }
          @Override
          public void onError(Throwable e) {
          	e.printStackTrace();
          }
      };
      gitHubService.getIssue(username, repo)
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(disposableObserver);
  }
  ```


### (3)实验遇到的困难以及解决思路

在Github这个应用中，RecyclerView没有显示，在通过api获取完仓库列表后，我直接将获取后的list复制给adapter的list，结果UI显示不出来，输出list的数据却存在。这是因为改变了list的地址，使到adapter所传入的list直接丢失掉。应该将新数据一个个添加到adapter的list中：

```
if(repos != null){
    repos.add(githubRepoObj);
    notifyItemInserted(repos.size() - 1);
}
```

---

## 四、实验思考及感想

这两次实验学习的是Http访问api来获取数据，其中涉及了对json数据的转化，对字符串的规范化处理，以及对于图片的获取。另外还学习了Retrofit2、Okhttp，复习了RxJava的多线程处理，学习了新的访问网络获取数据的方式。比较而言使用Retrofit来获取网络的数据更加简便，而且接口简单，可读性强，配合RxJava可以解决获取数据与更新UI的矛盾。总之来说，这一次的实验相比以前的学习，是学习了新的技术，收获蛮大。