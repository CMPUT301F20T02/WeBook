package com.example.webook;

import android.graphics.drawable.Drawable;
import android.media.Image;

import java.util.ArrayList;

public class Owner extends User {
    private ArrayList<Book> bookList;
    private ArrayList<Request> requestList;


    public Owner(String username, String email, String phoneNumber, String pwd){
        super(username, email, phoneNumber, pwd);
        this.setUserType("owner");
        bookList = new ArrayList<Book>();
        requestList = new ArrayList<Request>();
    }
    public void addBook(String title, String ISBN, String author, Drawable image, String description, String status){
        Book book = new Book(title, ISBN, author, status, this.getUsername(), image, description);

        bookList.add(book);
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    public ArrayList<Request> getRequestList() {
        return requestList;
    }

    public void setRequestList(ArrayList<Request> requestList) {
        this.requestList = requestList;
    }

    public ArrayList<Book> getAvailable() {
        ArrayList<Book> available = new ArrayList<Book>();
        for (int i = 0; i < this.bookList.size(); i++) {
            if (bookList.get(i).getStatus() == "available") {
                available.add(bookList.get(i));
            }

        }

        return available;
    }

    public ArrayList<Book> getRequested() {
        ArrayList<Book> requested = new ArrayList<Book>();
        for (int i = 0; i < this.bookList.size(); i++) {
            if (bookList.get(i).getStatus() == "requested") {
                requested.add(bookList.get(i));
            }

        }

        return requested;
    }

    public ArrayList<Book> getAccepted() {
        ArrayList<Book> accepted = new ArrayList<Book>();
        for (int i = 0; i < this.bookList.size(); i++) {
            if (bookList.get(i).getStatus() == "accepted") {
                accepted.add(bookList.get(i));
            }

        }

        return accepted;
    }

    public ArrayList<Book> getBorrowed() {
        ArrayList<Book> borrowed = new ArrayList<Book>();
        for (int i = 0; i < this.bookList.size(); i++) {
            if (bookList.get(i).getStatus() == "borrowed") {
                borrowed.add(bookList.get(i));
            }

        }
        return borrowed;
    }
}
