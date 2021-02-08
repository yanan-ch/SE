package com.chenyn.miaowu.data.post;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.chenyn.miaowu.data.AppRoomDatabase;
import com.chenyn.miaowu.data.post.Post;

import java.util.List;

public class PostRepository {
    private PostDao mPostDao;
    private LiveData<List<Post>> mAllPosts;

    public PostRepository(Application application) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        this.mPostDao = db.postDao();
        this.mAllPosts = mPostDao.getAllPosts();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Post>> getAllPosts() {
        return mAllPosts;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertPost(final Post post) {
        AppRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mPostDao.insertPost(post);
            }
        });
    }

    public void deletePostById(final Post post) {
        AppRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mPostDao.deletePostById(post.getPostId());
            }
        });
    }
}
