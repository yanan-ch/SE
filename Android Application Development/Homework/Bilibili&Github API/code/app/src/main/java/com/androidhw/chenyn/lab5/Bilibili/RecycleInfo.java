package com.androidhw.chenyn.lab5.Bilibili;

import com.google.gson.annotations.SerializedName;

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
        //评论
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
