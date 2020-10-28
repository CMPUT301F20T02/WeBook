package com.example.webook;

import android.location.Location;

import java.util.Date;

public class Request {
    private String requester;
    private String requestee;
    private Book book;
    private Location geoLocation;
    private Date date;

    Request(Book book, String requestee, String requester, Date date, Location geoLocation){
        this.book = book;
        this.requestee = requestee;
        this.requester = requester;
        this.date = date;
        this.geoLocation = geoLocation;
    }

    public String getRequester() {
        return requester;
    }

    public String getRequestee() {
        return requestee;
    }

    public Book getBook() {
        return book;
    }

    public Location getGeoLocation() {
        return geoLocation;
    }

    public Date getDate() {
        return date;
    }

    public void setGeoLocation(Location geoLocation) {
        this.geoLocation = geoLocation;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
