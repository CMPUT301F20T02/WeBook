package com.example.webook;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class RequestList extends ArrayAdapter<Request> {
    private ArrayList<Request> requests;
    private Context context;

    public RequestList(@NonNull Context context, @NonNull ArrayList<Request> requests) {
        super(context, 0, requests);
    }

}
