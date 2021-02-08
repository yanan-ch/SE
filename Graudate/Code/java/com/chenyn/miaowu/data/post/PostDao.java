package com.chenyn.miaowu.data.post;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao {
    @Insert
    void insertPost(Post post);

    @Delete()
    void deletePost(Post post);

    @Query("DELETE FROM post_table WHERE post_id = :postId")
    void deletePostById(int postId);

    @Query("DELETE FROM post_table")
    void deleteAllPosts();

    @Query("SELECT * FROM post_table")
    LiveData<List<Post>> getAllPosts();
}
