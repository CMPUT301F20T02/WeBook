package com.example.webook;

public class Owner extends User {
    private CustomList<Book> bookList;
    private CustomList<BookRequest> requestList;


    public Owner(String username, String email, String phoneNumber, String pwd, String userType) {
        super(username, email, phoneNumber, pwd, userType);
    }
}
