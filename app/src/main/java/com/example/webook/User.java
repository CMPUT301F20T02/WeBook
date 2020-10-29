package com.example.webook;

public abstract class User {
    private String username;
    private String email;
    private String phoneNumber;
    private String pwd;
    private String userType;

    public User(String username, String email, String phoneNumber, String pwd, String userType) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.pwd = pwd;
        this.userType = userType;
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
}
