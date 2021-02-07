package com.androidhw.chenyn.musicplayer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.DataFormatException;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private CircleImageView albumImage;
    private TextView songName, singerName, startTime, endTime;
    private SeekBar seekBar;
    private ImageView fileChooseBtn, playOrPauseBtn, stopBtn, backBtn;

    private IBinder mBinder;
    private final int PLAY_CODE = 1,
            STOP_CODE = 2,
            SEEKBAR_CODE = 3,
            CURRRENT_TIME_CODE = 4,
            TOTAL_TIME_CODE = 5,
            NEW_MUSIC_CODE = 6;

    boolean isPlay = false;
    boolean isStop = false;

    private int totalTime = 0;
    private SimpleDateFormat time = new SimpleDateFormat("mm:ss");

    private static int FILE_PERMISSION_REQUEST_CODE = 1;
    //读写权限
    private static String[] STORAGE_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };

    // 在Observable对象中查询歌曲的播放时间，用onNext方法传递给Observer。
    // Observer对象观察到Observable发送的播放时间后，完成UI的更新。
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
        @Override
        public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
            while (true) {
                //与服务通信
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    //获取信息
                    mBinder.transact(CURRRENT_TIME_CODE, data, reply, 0);
                    //每隔一毫秒查询一次歌曲的状态
                    Thread.sleep(1);
                } catch (Exception e){
                    Log.e("SERVICE CONNECTION", "onServiceConnected: " + e.toString());
                }
                //读取当前进度
                observableEmitter.onNext(reply.readInt());
                if(reply.readInt() == 1 || isStop)
                    break;
            }
            observableEmitter.onComplete();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playOrPauseBtn = findViewById(R.id.play_pause);
        stopBtn = findViewById(R.id.stop);
        fileChooseBtn = findViewById(R.id.choose_file);
        backBtn = findViewById(R.id.back);

        seekBar = findViewById(R.id.seekbar);

        albumImage = findViewById(R.id.album_image);
        albumImage.setPivotX(albumImage.getWidth() / 2);
        albumImage.setPivotY(albumImage.getHeight() / 2);

        songName = findViewById(R.id.song_name);
        singerName = findViewById(R.id.singer);
        startTime = findViewById(R.id.start_time);
        endTime = findViewById(R.id.end_time);

        ActivityCompat.requestPermissions(this, STORAGE_PERMISSIONS, FILE_PERMISSION_REQUEST_CODE);
        // 在Activity中调用bindService保持与Service的通信
        // Activity启动时绑定Service
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, sc, BIND_AUTO_CREATE);


        playOrPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //与服务通信
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try{
                    mBinder.transact(PLAY_CODE, data, reply, 0);
                } catch (RemoteException e){
                    Log.e("PLay or Pause Error: ", e.toString() );
                }
                if(isPlay == true){
                    isPlay = false;
                    playOrPauseBtn.setImageResource(R.drawable.play);
                } else {
                    isPlay = true;
                    isStop = false;
                    playOrPauseBtn.setImageResource(R.drawable.pause);
                    //订阅观察者
                    DisposableObserver<Integer> disposableObserver = new DisposableObserver<Integer>() {
                        @Override
                        //设置UI
                        public void onNext(Integer integer) {
                            Log.d("onNext", "" + integer);
                            seekBar.setProgress(integer);
                            startTime.setText(time.format(new Date(integer)));
                            albumImage.setPivotX(albumImage.getWidth()/2);
                            albumImage.setPivotY(albumImage.getHeight()/2);//支点在图片中心
                            albumImage.setRotation(integer/30);
                        }
                        //模拟点击停止按键
                        @Override
                        public void onComplete() {
                            stopBtn.performClick();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("onError", "" + e.toString());
                        }
                    };
                    //在新线程监听
                    observable.subscribeOn(Schedulers.newThread())
                            //在主线程更新
                            .observeOn(AndroidSchedulers.mainThread())
                            //绑定
                            .subscribe(disposableObserver);
                    //管理DisposableObserver的容器
                    mCompositeDisposable.add(disposableObserver);
                }
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlay = false;
                isStop = true;
                playOrPauseBtn.setImageResource(R.drawable.play);
                seekBar.setProgress(0);
                startTime.setText(time.format(0));
                albumImage.setRotation(0);
                //与服务通信
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    mBinder.transact(STOP_CODE, data, reply, 0);
                } catch (RemoteException e){
                    Log.e("Stop Error: ", e.toString() );
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser == true) {
                    albumImage.setPivotX(albumImage.getWidth() / 2);
                    albumImage.setPivotY(albumImage.getHeight() / 2);
                    albumImage.setRotation(progress / 30);
                    startTime.setText(time.format(progress));
                    //seekBar.setProgress(progress);
                    //与服务通信
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    data.writeInt(progress);
                    try {
                        mBinder.transact(SEEKBAR_CODE, data, reply, 0);
                    } catch (RemoteException e){
                        Log.e("SeekBar Error: ", e.toString() );
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        fileChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,FILE_PERMISSION_REQUEST_CODE);
            }
        });

        // 停止服务，解除绑定
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // bindService成功后回调onServiceConnected函数
    // 通过IBinder获取Service对象，实现Activity与Service的绑定
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = service;
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try {
                mBinder.transact(TOTAL_TIME_CODE, data, reply, 0);
            } catch (Exception e) {
                Log.e("Service Connection Failed", e.toString());
            }
            totalTime = reply.readInt();
            seekBar.setProgress(0);
            seekBar.setMax(totalTime);
            //毫秒转时间
            endTime.setText(time.format(new Date()));
            startTime.setText(time.format(new Date(0)));
        }

        @Override
        public void onServiceDisconnected(ComponentName name) { }
    };

    /*public Runnable  myThread = new Runnable() {
        @Override
        public void run() {
            Message msg = handler.obtainMessage();
            if(isStop){
                msg.what = -1;
                handler.sendMessage(msg);
                return;
            }
            try{
                //与服务通信
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                mBinder.transact(CURRRENT_TIME_CODE, data, reply, 0);
                msg.arg1 = reply.readInt();
            }catch (Exception e){
                Log.d("Run", "run: " + e.toString());
                return;
            }
            handler.sendMessage(msg);
        }
    };
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) { // 根据消息类型进行操作
                case -1:
                    handler.removeCallbacks(myThread);
                    seekBar.setProgress(0);
                    startTime.setText(time.format(0));
                    albumImage.setRotation(0);
                    break;
                default:
                    if(msg.arg1 >= totalTime)
                        stopBtn.performClick();
                    seekBar.setProgress(msg.arg1);
                    startTime.setText(time.format(new Date(msg.arg1)));
                    albumImage.setPivotX(albumImage.getWidth() / 2 );
                    albumImage.setPivotY(albumImage.getHeight() / 2);//支点在图片中心
                    albumImage.setRotation(msg.arg1 / 30);
                    handler.postDelayed(myThread, 1);
            }
        }
    };*/

    @Override
    public void onDestroy(){
        super.onDestroy();
        /*if(sc != null){
            unbindService(sc);
        }*/
        //清除所有的观察者
        mCompositeDisposable.clear();
        if(sc != null){
            unbindService(sc);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FILE_PERMISSION_REQUEST_CODE) {
            Intent intent = new Intent(this, MusicService.class);
            bindService(intent, sc, BIND_AUTO_CREATE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            try{
                //与服务通信
                Parcel serviceData = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                serviceData.writeString(data.getData().toString());
                try {
                    mBinder.transact(NEW_MUSIC_CODE, serviceData, reply, 0);
                } catch (RemoteException e){
                    Log.e("Choose new Music", "Error: " + e.toString() );
                }
                totalTime = reply.readInt();
                seekBar.setProgress(0);
                seekBar.setMax(totalTime);
                endTime.setText(time.format(new Date(totalTime)));
                startTime.setText(time.format(new Date(0)));

                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(MainActivity.this,data.getData());
                songName.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
                singerName.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
                byte[] picture = mmr.getEmbeddedPicture();
                if(picture.length!=0){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                    albumImage.setImageBitmap(bitmap);
                }
                mmr.release();

                isPlay = false;
                playOrPauseBtn.performClick();
            }catch (Exception e){
                Log.e("Choose new Music", "Error: " + e.toString() );
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
