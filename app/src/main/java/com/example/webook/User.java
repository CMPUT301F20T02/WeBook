package com.example.webook;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.Serializable;

public abstract class User implements Serializable {
    private String username;
    private String email;
    private String phoneNumber;
    private String pwd;
    private String userType;
    private String description;
    private String user_image;

    public User(){}

    public User(String username, String email, String phoneNumber, String pwd, String description, String user_image){
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.pwd = pwd;
        this.description = description;
        this.user_image = user_image;
    }

    public void editInformation(String email, String phoneNumber, String description, String user_image){
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.user_image = user_image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
