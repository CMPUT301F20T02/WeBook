package com.example.webook;

import java.util.ArrayList;
/**
 * This is a class for owner object which is a sub class of user
 * Indicates that userType is "owner"
 */
public class Owner extends User {
    //private ArrayList<Book> bookList;
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
