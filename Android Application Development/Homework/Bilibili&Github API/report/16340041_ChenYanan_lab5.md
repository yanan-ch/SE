# ��ɽ��ѧ���ݿ�ѧ������ѧԺ������ʵ�鱨��
## ��2018���＾ѧ�ڣ�
| �γ����� | �ֻ�ƽ̨Ӧ�ÿ��� | �ο���ʦ | ֣��� |
| :------------: | :-------------: | :------------: | :-------------: |
| �꼶 | 2016 | רҵ������ | ����ý�� |
| ѧ�� | 16340041 | ���� | ����� |
| �绰 | 18709548602 | Email | chenyn97@outlook.com |
| ��ʼ���� | 2018.12.13 | ������� |2018.12.21|

---

## һ��ʵ����Ŀ

#### ������Ŀ5����Web API 

- ��ʮ����ʵ��Ŀ�ģ�
  - ѧ��ʹ��HttpURLConnection�������Web����
  - ѧϰAndroid�̻߳��ƣ�ѧ���̸߳���UI��
  - ѧ�����JSON���ݣ�
  - ѧϰCardView���ּ�����
- ��ʮ����ʵ��Ŀ�ģ�
  - ���Restful�ӿڣ�
  - ѧ��ʹ��Retrofit2��
  - ��ϰʹ��RxJava��
  - ѧ��ʹ��OkHttp��

---

## ����ʵ������

1.ʵ��һ��bilibili���û���Ƶ��Ϣ��ȡ�����

- �ٴ������û�id = 7�������ϴν������չʾ�������ݣ�
- ���ֻ����ڷ���ģʽ��ر�wifi���ƶ����ݵ���������ʱ����Ҫ��Toast��ʾ��
- ��ͼƬ���س���ǰ��Ҫ��һ������������Ҫ������ؽ���ͬ����
- չʾͼƬ/������/����/ʱ��/����ʱ��/����/�������ݣ�
- ʹ��2��CardView��

2.ʵ��һ��github�û�repos�Լ�issuesӦ�ã�

- �����û����������û����п��ύissue��repo��ÿ��item�ɵ����
- ÿ�ε��������ť��������ϴ���������ٽ�����һ�ֵ�������
- ��ȡreposʱ��Ҫ���������쳣��HTTP 404 �Լ� �û�û���κ�repo��
- ֻ��ʾ has_issues = true ��repo����fork�����˵���Ŀ������ʾ����
- repo��item���Ե����ת����һ���棻
- ��repo�������κ�issueʱ��Ҫ��Toast��ʾ��

---

## ��������ʵ����
### (1)ʵ���ͼ

1.��14�ܣ�

- �򿪳�����ҳ�棺

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548304383.png" width=400px>

- �����û�id���������������û�򿪵�Toast��ʾ��������ʧ�ܣ�

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548304509.png" width=400px>

- ���������£������û�id����������Ӧ���ݵĵ�Toast��ʾ��

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548304539.png" width=400px>

- �����û�id = 2�����������չʾͼƬ/������/����/ʱ��/����ʱ��/����/������ݣ�

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548304574.png" width=400px>

- �ٴ������û�id = 7�������ϴν������չʾ�������ݣ�

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548304585.png" width=400px>

2.��15�ܣ�

- ������������ת��ť�ֱ��Ӧ������ҵ��

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548305320.png" width=400px>

- �����û����������û����п��ύissue��repo��

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548305411.png" width=400px>

- ��ʾ��repo���е�issues��

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548305923.png" width=400px>

- repo�������κ�issueʱ��Ҫ��Toast��ʾ��

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548305978.png" width=400px>

- ���벻���ڵ��û�����ʾHTTP 404��

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548306115.png" width=400px>

- �û�û���κ�repo��

  <img src="https://gitee.com/chenyn227/PersonalProject5/raw/master/report/Thursday/16340041ChenYanan/img/Screenshot_1548306242.png" width=400px>

### (2)ʵ�鲽���Լ��ؼ�����

#### Bilibili��

##### 1.ʹ��CardView

����CardView������

```java
compile 'com.android.support:cardview-v7:28.+'
```

ʹ��CardView��

```xml
<android.support.v7.widget.CardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:cardUseCompatPadding="true"
    app:cardCornerRadius="8dp"
    app:cardBackgroundColor="#f8f8ff">
</android.support.v7.widget.CardView>
```

##### 2.ʹ��ProgressBar

```xml
<ProgressBar
             android:id="@+id/progressBar"
             android:layout_width="wrap_content"
             android:layout_height="wrap_content"
             android:visibility="visible"
             android:layout_gravity="center"/>
```

##### 3.����RecycleInfo������

���Ǹ���bվ���ṩ��api�������õ���json������Ҫ�õ�Gson������������

```java
compile 'com.squareup.retrofit2:converter-gson:2.1.0'
```

RecycleInfo�����£�

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
        //����
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

##### 4.RecyclerView��ʾ

��һ������֮ǰ�Ѿ�ѧϰ��������׸����

```java
@Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {        ((TextView)holder.getView(R.id.result_play)).setText(data.get(position).getIdata().getPlay());                                                                       ((TextView)holder.getView(R.id.result_review)).setText(data.get(position).getIdata().getVideo_review());                                                                          ((TextView)holder.getView(R.id.result_duration)).setText(data.get(position).getIdata().getDuration());                                                                            ((TextView)holder.getView(R.id.result_create)).setText(data.get(position).getIdata().getCreate());                                                                             ((TextView)holder.getView(R.id.title)).setText(data.get(position).getIdata().getTitle());                                                                           ((TextView)holder.getView(R.id.content)).setText(data.get(position).getIdata().getContent());
    //���������ʱ�������϶������϶�
    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 1:
                //���÷���
((ImageView)holder.getView(R.id.cover)).setImageBitmap(holder.result_bitmap);
                //���ؽ�����
                holder.getView(R.id.progressBar).setVisibility(View.GONE);
                //��ʾ����
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
            //����Message��ͼƬ����Handler
                Message msg = Message.obtain();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }
    }.start();
}
```

##### 5.�û�id�����ж��¼�

�ڽ�������ʱ������Ҫ��������û�id�����жϣ�

```java
search_but.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (editText.getText().toString().isEmpty()) {
        	Toast.makeText(MainActivity.this, "EditText is empty!",Toast.LENGTH_SHORT).show();
        } else {
        //���Ĺ۲���
        DisposableObserver<RecycleInfo> disposableObserver = new DisposableObserver<RecycleInfo>() {
            @Override
            public void onNext(RecycleInfo recycleInfo) {
            if (recycleInfo.getStatus()) {
                //��ӵ���ʾ������б���
                recyclerViewAdapter.addItem(recycleInfo);
            } else//��ִ��
            	Toast.makeText(MainActivity.this, "��ʽ����", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Throwable e) {
                if (e instanceof UnknownHostException)
                    Toast.makeText(MainActivity.this, "��������ʧ��",Toast.LENGTH_SHORT).show();
                else if (e instanceof com.google.gson.JsonSyntaxException) {
                    Toast.makeText(MainActivity.this, "��ǰ�û�������",Toast.LENGTH_SHORT).show();
                } else {
                	Toast.makeText(MainActivity.this, "δ֪����",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onComplete() {
            	Toast.makeText(MainActivity.this, "�������", Toast.LENGTH_SHORT).show();
            }
        };
        //�����̼߳���
        observable.subscribeOn(Schedulers.newThread())
        //�����̸߳���
        .observeOn(AndroidSchedulers.mainThread())
        //��
        .subscribe(disposableObserver);
        //����DisposableObserver������
        compositeDisposable.add(disposableObserver);
        }
    }
});
```

##### 6.ʹ��HttpURLConnection�������Web����

HttpURLConnection���Զ�URL���м�飬 �������ش����������ݣ�������ȡһЩ�й�URL���������õ���Դ��Ϣ���磺 HTTP״̬�� ͷ��Ϣ�� ���ݵĳ��ȡ� ���ͺ�����ʱ��ȡ�

����Ļ���ʹ��ģʽΪ��

- �������̣߳����߳��н���������ʷ�ֹ�������̣߳�
- ����һ���µ�URL����
- Ϊ���URL��Դ��Connection��
- ��ȡ���ݣ�
- ��������ʱ��Ҫ�����쳣��

ע�⣬������ʲ��������߳��н��У���������������� UI ̱������������ʹ��RxJava����������ʣ�

```java
private CompositeDisposable compositeDisposable = new CompositeDisposable();
private Observable<RecycleInfo> observable = Observable.create(new ObservableOnSubscribe<RecycleInfo>() {
    @Override
    public void subscribe(ObservableEmitter<RecycleInfo> observableEmitter) throws Exception {
        String baseUrl = "https://space.bilibili.com/ajax/top/showTop?mid=";
        String requestUrl = baseUrl + editText.getText().toString();
        // �½�һ��URL����
        URL url = new URL(requestUrl);
        // ��һ��HttpURLConnection����
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        // ��������������ʱʱ��
        urlConn.setConnectTimeout(5 * 1000);
        //���ô�������ȡ���ݳ�ʱ
        urlConn.setReadTimeout(5 * 1000);
        // �����Ƿ�ʹ�û���  Ĭ����true
        urlConn.setUseCaches(true);
        // ����ΪPost����
        urlConn.setRequestMethod("GET");
        //urlConn��������ͷ��Ϣ
        //���������е�ý��������Ϣ��
        urlConn.setRequestProperty("Content-Type", "application/json");
        //���ÿͻ����������������
        urlConn.addRequestProperty("Connection", "Keep-Alive");
        // ��ʼ����
        urlConn.connect();
        // �ж������Ƿ�ɹ�
        //InputStreamתString
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

#### GIthub��

##### 1.��������model��

Repo��

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

Issue��

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

##### 2.����Interface��

����һ������ӿڣ���������`GithubAPI`������ʹ����`retrofit2`��ע�ⷽ����������`user_name`�Ž�`url`����ͨ��`GET`���󣬷��ؾ����`repository`�б����۲��ߵĶ�������һ��`GithubRepoObj`���б�

```java
public interface GithubService  {
    // �����List<Repo>��Ϊ���շ��ص����ͣ���Ҫ����һ�²ſɽ���
    // ֮����ʹ��һ��List��������Ϊ�ýӿڷ��ص��������һ������
    @GET("/users/{user_name}/repos")
    Observable<List<GithubRepoObj>> getRepo(@Path("user_name") String user_name);

    @GET("/repos/{user_name}/{repo}/issues")
    Observable<List<GithubIssueObj>> getIssue(@Path("user_name") String user_name, @Path("repo") String repo);
}
```

##### 3.��ȡ�û����вֿ�

- ʹ��OkHttp��ҪĿ���ǳ���Retrofit�����󣬱���ʵ��ֻ��Ҫ�򵥵����ó�ʱʱ��ȼ��ɣ�

  ```java
  //������OkHttpClient����Ϊretrofitʱ����okhttp�ģ������������һЩ��ʱ������
  OkHttpClient build = new OkHttpClient.Builder()
      .connectTimeout(10, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .writeTimeout(10, TimeUnit.SECONDS)
      .build();
  ```

- ʹ��Retrofit��retrofit�������������Url����ַ�����֧��RxJava��ת��������

  ```java
  Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(baseURL)
      // ����json���ݽ�����
      .addConverterFactory(GsonConverterFactory.create())
      //����RXJAVA2�İ汾
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .client(build)
      .build();
  ```

- ����֮ǰ�Ľӿڶ���������repoObservable������UI�߳����ı�UI����io�߳�������������ʣ�

  ```java
  DisposableObserver<List<GithubRepoObj>> disposableObserver = new DisposableObserver<List<GithubRepoObj>>() {
      @Override
      public void onNext(List<GithubRepoObj> githubRepoObjs) {
          //���֮ǰ���������
          githubRecyclerAdapter.reset();
          //�û��ֿ�Ϊ��
          if(githubRepoObjs.isEmpty()){
              Toast.makeText(RepoActivity.this, "���û��������κ�Repository",Toast.LENGTH_SHORT).show();
          return;
      }
      //��ӵ���ʾ������б���
      for(GithubRepoObj g : githubRepoObjs){
          //���˵�fork���˵���Ŀ
          if(g.isHas_issues())
          	githubRecyclerAdapter.addItem(g);
          }
      }
      @Override
      public void onComplete() {
      	Toast.makeText(RepoActivity.this, "�������",Toast.LENGTH_SHORT).show();
      }
      @Override
      public void onError(Throwable e) {
          if(e instanceof UnknownHostException)
              Toast.makeText(RepoActivity.this, "��������ʧ��",Toast.LENGTH_SHORT).show();
              //404����
          else if(e.toString().equals("retrofit2.adapter.rxjava2.HttpException: HTTP 404 Not Found")){
          	Toast.makeText(RepoActivity.this, "404 Not Found",Toast.LENGTH_SHORT).show();
          } else{
          	Toast.makeText(RepoActivity.this, "δ֪����", Toast.LENGTH_SHORT).show();
          }
      e.printStackTrace();
      }
  };
  ```

  ##### 4.��ȡIssue

  ��ȡIssue�ķ������ȡ�û����вֿ�ķ������ƣ�

  ```java
  //��ȡ�û�ĳһ���ֿ���������ķ���
  public void getGithubIssues(String username, String repo) {
      //������OkHttpClient����Ϊretrofitʱ����okhttp�ģ������������һЩ��ʱ������
      OkHttpClient build = new OkHttpClient.Builder()
          .connectTimeout(10, TimeUnit.SECONDS)
          .readTimeout(30, TimeUnit.SECONDS)
          .writeTimeout(10, TimeUnit.SECONDS)
          .build();
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(baseURL)
          // ����json���ݽ�����
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .client(build)
          .build();
      GithubService gitHubService = retrofit.create(GithubService.class);
      DisposableObserver<List<GithubIssueObj>> disposableObserver = new DisposableObserver<List<GithubIssueObj>>() {
          @Override
          public void onNext(List<GithubIssueObj> githubIssueObjs) {
              //�����֮ǰ���б�
              githubIssueRecyclerAdapter.reset();
              if(githubIssueObjs.isEmpty()){
                  Toast.makeText(IssuesActivity.this, "�òֿⲻ�����κ�Issue",Toast.LENGTH_SHORT).show();
              return;
          	}
              //��ӵ���ʾ������б���
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


### (3)ʵ�������������Լ����˼·

��Github���Ӧ���У�RecyclerViewû����ʾ����ͨ��api��ȡ��ֿ��б����ֱ�ӽ���ȡ���list���Ƹ�adapter��list�����UI��ʾ�����������list������ȴ���ڡ�������Ϊ�ı���list�ĵ�ַ��ʹ��adapter�������listֱ�Ӷ�ʧ����Ӧ�ý�������һ������ӵ�adapter��list�У�

```
if(repos != null){
    repos.add(githubRepoObj);
    notifyItemInserted(repos.size() - 1);
}
```

---

## �ġ�ʵ��˼��������

������ʵ��ѧϰ����Http����api����ȡ���ݣ������漰�˶�json���ݵ�ת�������ַ����Ĺ淶�������Լ�����ͼƬ�Ļ�ȡ�����⻹ѧϰ��Retrofit2��Okhttp����ϰ��RxJava�Ķ��̴߳���ѧϰ���µķ��������ȡ���ݵķ�ʽ���Ƚ϶���ʹ��Retrofit����ȡ��������ݸ��Ӽ�㣬���ҽӿڼ򵥣��ɶ���ǿ�����RxJava���Խ����ȡ���������UI��ì�ܡ���֮��˵����һ�ε�ʵ�������ǰ��ѧϰ����ѧϰ���µļ������ջ�����