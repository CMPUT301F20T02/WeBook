package com.example.webook;

/**
 * This is a class for borrower object which is a sub class of user
 * Indicates that userType is "borrower"
 */
public class Borrower extends User {

    public Borrower(){}

    public Borrower(String username, String email, String phoneNumber, String pwd, String description, String image) {
        super(username, email, phoneNumber, pwd, description, image);
        this.setUserType("borrower");
    }
}
