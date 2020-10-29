package com.example.webook;

import android.graphics.drawable.Drawable;
import android.media.Image;

import java.util.ArrayList;

public class Owner extends User {
    private ArrayList<Book> bookList;
    private CustomList<BookRequest> requestList;


    public Owner(String username, String email, String phoneNumber, String pwd, String userType) {
        super(username, email, phoneNumber, pwd, userType);
        bookList = new ArrayList<Book>();
        requestList = new CustomList<BookRequest>();
    }
    public void addBook(String title, String ISBN, String author, Drawable image, String description){
        Book book = new Book(title, ISBN, author,  "good", description,image,"good");
        bookList.add(book);
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }
}
