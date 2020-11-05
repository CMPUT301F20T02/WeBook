package com.example.webook;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

public class BookRequest extends Request {
    private String status;

    /**
     * constructor of the BookRequest with no parameter.
     */
    BookRequest(){}

    /**
     * constructor of the BookRequest with all parameters, super comes from Request class.
     * @param book
     * @param requestee
     * @param requester
     * @param date
     * @param geoLocation
     */
    BookRequest(Book book, String requestee, ArrayList<String> requester, Date date, Location geoLocation) {
        super(book, requestee, requester, date, geoLocation);
    }

    /**
     * getter of the status of the request (pending, accepted or declined).
     * @return status of the request
     */
    public String getStatus() {
        return status;
    }

    /**
     * setter of the status of the request (pending, accepted or declined).
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
