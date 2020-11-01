package com.example.webook;

import android.location.Location;

import java.util.Date;

public class BookRequest extends Request {
    private String status;


    BookRequest(){}

    BookRequest(Book book, String requestee, String requester, Date date, Location geoLocation) {
        super(book, requestee, requester, date, geoLocation);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
