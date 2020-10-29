package com.example.webook;

public class Borrower extends User {

    public Borrower(String username, String email, String phoneNumber, String pwd) {
        super(username, email, phoneNumber, pwd);
        this.setUserType("borrower");
    }
}
