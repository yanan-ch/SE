package com.chenyn.miaowu.ui.home;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chenyn.miaowu.data.post.Post;
import com.chenyn.miaowu.data.post.PostRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private PostRepository mPostRepository;
    // Using LiveData and caching what getAllPosts returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    // - the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<Post>> mAllPosts;

    public HomeViewModel(Application application) {
        super();
        mPostRepository = new PostRepository(application);
        mAllPosts = mPostRepository.getAllPosts();
    }

    public LiveData<List<Post>> getAllPosts() {
        return mAllPosts;
    }

    public void insertPost(Post post) {
        mPostRepository.insertPost(post);
    }

    public void deletePostById(Post post) {
        mPostRepository.deletePostById(post);
    }

}