package com.example.scratchapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Profile {
    @SerializedName("follows")
    @Expose
    private List<String> follows = null;
    @SerializedName("saves")
    @Expose
    private List<String> saves = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userId")
    @Expose
    private String userId;

    public Profile() {
    }

    public Profile(List<String> follows, List<String> saves,
                   String id, String address, String avatar,
                   Integer likes, String userName, String userId) {
        this.follows = follows;
        this.saves = saves;
        this.id = id;
        this.address = address;
        this.avatar = avatar;
        this.likes = likes;
        this.userName = userName;
        this.userId = userId;
    }

    public List<String> getFollows() {
        return follows;
    }

    public void setFollows(List<String> follows) {
        this.follows = follows;
    }

    public List<String> getSaves() {
        return saves;
    }

    public void setSaves(List<String> saves) {
        this.saves = saves;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
