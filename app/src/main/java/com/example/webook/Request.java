package com.example.webook;

import android.location.Location;

import java.util.Date;

public abstract class Request {
    private String requester;
    private String requestee;
    private String book;
    private Location geoLocation;
    private Date date;

    public String getRequester() {
        return requester;
    }

    public String getRequestee() {
        return requestee;
    }

    public String getBook() {
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
