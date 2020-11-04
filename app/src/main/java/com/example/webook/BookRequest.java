package com.example.webook;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

public class BookRequest extends Request {
    private String status;

    BookRequest(){}

    BookRequest(Book book, String requestee, ArrayList<String> requester, Date date, Location geoLocation) {
        super(book, requestee, requester, date, geoLocation);
    }

    BookRequest(Book book, String requestee, ArrayList<String> requester, Date date, Location geoLocation, Integer position) {
        super(book, requestee, requester, date, geoLocation);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
