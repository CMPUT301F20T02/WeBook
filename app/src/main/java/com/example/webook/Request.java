package com.example.webook;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public abstract class Request implements Serializable{
    private ArrayList<String> requester;
    private String requestee;
    private Book book;
    private Location geoLocation;
    private Date date;

    /**
     * Constructor of the request with no parameter.
     * @param
     */
    public Request(){}

    /**
     *Constructor of the city with all parameters.
     * @param book
     * @param requestee
     * @param requester
     * @param date
     * @param geoLocation
     */
    public Request(Book book, String requestee, ArrayList<String> requester, Date date, Location geoLocation){
        this.book = book;
        this.requestee = requestee;
        this.requester = requester;
        this.date = date;
        this.geoLocation = geoLocation;
    }

    /**
     * getter of the requester.
     * @return name of the requester
     */
    public ArrayList<String> getRequester() {
        return requester;
    }

    /**
     * getter of the requestee
     * @return return the name of the requestee
     */
    public String getRequestee() {
        return requestee;
    }

    /**
     * getter of book.
     * @return return name of the book
     */
    public Book getBook() {
        return book;
    }

    /**
     * get the location where owner and borrower transfer the book.
     * this function will be achieve later.
     * @return return a location
     */
    public Location getGeoLocation() {
        return geoLocation;
    }

    /**
     * get the date when borrower and owner transfer the book.
     * @return return a date
     */
    public Date getDate() {
        return date;
    }

    /**
     * setter of the location where owner and borrower transfer the book.
     * @param geoLocation
     */
    public void setGeoLocation(Location geoLocation) {
        this.geoLocation = geoLocation;
    }

    /**
     * setter of the date when owner and borrower transfer the book.
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
