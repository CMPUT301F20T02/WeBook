package com.example.webook;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;
/**
 * This is a class for BookRequest object which is a sub class of Request
 */
public class BookRequest extends Request {
    private boolean waiting = false;

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

    BookRequest(Book book, String requestee, ArrayList<String> requester, ArrayList<Integer> date, ArrayList<Double> geoLocation) {

        super(book, requestee, requester, date, geoLocation);
    }

    /**
     * getter of the waiting of the request (pending, accepted or declined).
     * @return waiting of the request
     */
    public boolean getwaiting() {
        return waiting;
    }

    /**
     * setter of the waiting of the request (pending, accepted or declined).
     * @param waiting
     */
    public void setwaiting(boolean waiting) {
        this.waiting = waiting;
    }
}
