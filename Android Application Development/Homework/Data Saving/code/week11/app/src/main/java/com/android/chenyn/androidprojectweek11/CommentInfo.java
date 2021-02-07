package com.android.chenyn.androidprojectweek11;

import java.util.ArrayList;

public class CommentInfo {
    private String username;
    private String commentTime;
    private String commentContent;
    private String headshotUri;
    private int agreeNumber;
    private ArrayList<String> agreedUsers;

    public CommentInfo(String username, String commentTime, String commentContent, String headshotUri, ArrayList<String> agreedUsers) {
        this.username = username;
        this.commentTime = commentTime;
        this.commentContent = commentContent;
        this.headshotUri = headshotUri;
        this.agreeNumber = agreedUsers.size();
        this.agreedUsers = agreedUsers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getHeadshotUri() {
        return headshotUri;
    }

    public void setHeadshotUri(String headshotUri) {
        this.headshotUri = headshotUri;
    }

    public int getAgreeNumber() {
        return agreeNumber;
    }

    public void setAgreeNumber(int agreeNumber) {
        this.agreeNumber = agreeNumber;
    }

    public ArrayList<String> getAgreedUsers() {
        return agreedUsers;
    }

    public void setAgreedUsers(ArrayList<String> agreedUsers) {
        this.agreedUsers = agreedUsers;
    }

}
