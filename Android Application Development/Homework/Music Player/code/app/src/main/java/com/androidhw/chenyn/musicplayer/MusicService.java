package com.androidhw.chenyn.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

public class MusicService extends Service {
    public MusicService() { }

    private final int PLAY_CODE = 1,
            STOP_CODE = 2,
            SEEKBAR_CODE = 3,
            CURRRENT_TIME_CODE = 4,
            TOTAL_TIME_CODE = 5,
            NEW_MUSIC_CODE = 6;

    private int ifComplete = -1;

    public final IBinder binder = new MyBinder();

    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        try {
            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory() + "/data/山高水长.mp3");
            Log.i("Init Music Sourse: ", Environment.getExternalStorageDirectory().toString());
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    ifComplete = -1;
                }
            });
        } catch (IOException e) {
            Log.e("Media prepare failed, ", "Error: " + e.toString());
            Log.i("Init music path: ", Environment.getExternalStorageDirectory().toString());
        }
        return binder;
    }

    // 通过Binder来保持Activity和Service的通信
    public class MyBinder extends Binder {
        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case PLAY_CODE:
                	playOrPause();
                    break;
                case STOP_CODE:
                    stopMusic();
                    break;
                case SEEKBAR_CODE:
                    mediaPlayer.seekTo(data.readInt());
                    break;
                case CURRRENT_TIME_CODE:
                    reply.writeInt(mediaPlayer.getCurrentPosition());
                    reply.writeInt(ifComplete);
                    break;
                case TOTAL_TIME_CODE:
                    reply.writeInt(mediaPlayer.getDuration());
                    break;
                case NEW_MUSIC_CODE:
                    newMusic(Uri.parse(data.readString()));
                    reply.writeInt(mediaPlayer.getDuration());
                    break;
                default:
                    break;
            }
            return super.onTransact(code, data, reply, flags);
        }
    }

    public void playOrPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
            ifComplete = -1;
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare();
                mediaPlayer.seekTo(0);
            } catch (Exception e) {
                Log.e("Media has stopped, New prepare failed, Error: ", e.toString());
            }
        }
    }

    public void newMusic(Uri uri) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(this, uri);
            mediaPlayer.prepare();
        } catch (Exception e) {
            Log.e("New media prepare failed, Error: ", e.toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
        }
    }

}
