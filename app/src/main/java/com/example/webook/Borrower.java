package com.example.webook;

import java.util.ArrayList;

/**
 * This is a class for borrower object which is a sub class of user
 * Indicates that userType is "borrower"
 */
public class Borrower extends User {

    private ArrayList<String> requestList;

    public Borrower(){}

    public Borrower(String username, String email, String phoneNumber, String pwd, String description, String image) {
        super(username, email, phoneNumber, pwd, description, image);
        this.setUserType("borrower");
        requestList = new ArrayList<>();
    }

    public void addRequest(String book){this.requestList.add(book);}

    public ArrayList<String> getRequestList() {
        return requestList;
    }

    public void setRequestList(ArrayList<String> requestList) {
        this.requestList = requestList;
    }
}
