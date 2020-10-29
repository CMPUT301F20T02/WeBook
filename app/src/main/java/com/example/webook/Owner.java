package com.example.webook;

import android.media.Image;

public class Owner extends User {
    private BookList bookList;
    private RequestList requestList;


    public Owner(String username, String email, String phoneNumber, String pwd){
        super(username, email, phoneNumber, pwd);
    }
    public void addBook(String title, String ISBN, String author, Image image, String description){
        Book book = new Book(title, ISBN, author, "available", this.getUsername(), image, description);

        bookList.add(book);
    }


}
