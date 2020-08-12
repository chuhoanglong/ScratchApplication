package com.example.scratchapplication.adapter;

import java.util.ArrayList;
    public class User {
        public String userName;
        public String avatar;
        public String address;
        public int likes;
        public ArrayList followers;
        public User(String userName, String avatar, String address, int likes, ArrayList followers){
            this.userName = userName;
            this.avatar = avatar;
            this.address = address;
            this.likes = likes;
            this.followers = followers;
        }
}
