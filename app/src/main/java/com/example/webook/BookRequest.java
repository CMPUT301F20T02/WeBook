package com.example.webook;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

public class BookRequest extends Request {
    private String status;
    private Integer position;

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

    public Integer getPosition() {return position;}

    public void setPosition(Integer position) {
        this.position = position;
    }
}
