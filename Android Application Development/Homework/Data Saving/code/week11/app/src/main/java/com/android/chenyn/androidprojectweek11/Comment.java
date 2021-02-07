package com.android.chenyn.androidprojectweek11;

import java.util.UUID;

public class Comment {
    private String commentId;
    private String creator;
    private String commentTime;
    private String commentContent;

    public Comment() { }

    public Comment(String creator, String commentTime, String commentContent) {
        this.commentId = UUID.randomUUID().toString();
        this.creator = creator;
        this.commentTime = commentTime;
        this.commentContent = commentContent;
    }

    public String getcommentId() {
        return commentId;
    }

    public void setcommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getcreator() {
        return creator;
    }

    public void setcreator(String creator) {
        this.creator = creator;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
