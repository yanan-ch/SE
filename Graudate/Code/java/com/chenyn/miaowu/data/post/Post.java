package com.chenyn.miaowu.data.post;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import java.util.List;

@Entity(tableName = "post_table")
public class Post {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "post_id")
    private int postId;

    @ColumnInfo(name = "post_time")
    private String postTime;

    @ColumnInfo(name = "post_text")
    private String postText;

    @ColumnInfo(name = "post_image_url")
    private String postImageUrl;

    public Post(String postTime, String postText, String postImageUrl) {
        this.postTime = postTime;
        this.postText = postText;
        this.postImageUrl = postImageUrl;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostText() {
        return postText;
    }

    public void setImageUrl(String imageUrl) {
        this.postImageUrl = imageUrl;
    }

    public String getImageUrl() {
        return postImageUrl;
    }

    public static interface PostDao {
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
}
