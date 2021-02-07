package com.android.chenyn.androidprojectweek11;

public class UserInfo {
    private String username;
    private String password;
    private String headshotUri;

    public UserInfo(String username, String password, String headshotUri) {
        this.username = username;
        this.password = password;
        this.headshotUri = headshotUri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadshotUri() {
        return headshotUri;
    }

    public void setHeadshotUri(String headshotUri) {
        this.headshotUri = headshotUri;
    }
}
