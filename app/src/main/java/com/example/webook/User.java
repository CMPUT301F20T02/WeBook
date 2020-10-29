package com.example.webook;

public abstract class User {
    private String username;
    private String email;
    private String phoneNumber;
    private String pwd;

    public User(String username, String email, String phoneNumber, String pwd){
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.pwd = pwd;
    }

    public void editContactInformation(String email, String phoneNumber){

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
}
