package com.example.webook;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;

import java.util.ArrayList;

public class Owner extends User {
    private ArrayList<String> bookList;
    private ArrayList<String> requestList;

    public Owner(){}

    public Owner(String username, String email, String phoneNumber, String pwd, String description, String image){
        super(username, email, phoneNumber, pwd, description, image);
        this.setUserType("owner");
        bookList = new ArrayList<>();
        requestList = new ArrayList<>();
    }
    public void addBook(String book){
        this.bookList.add(book);
    }

    public ArrayList<String> getBookList() {
        return this.bookList;
    }

    public void setBookList(ArrayList<String> bookList) {
        this.bookList = bookList;
    }

    public ArrayList<String> getRequestList() {
        return requestList;
    }

    public void setRequestList(ArrayList<String> requestList) {
        this.requestList = requestList;
    }


}
