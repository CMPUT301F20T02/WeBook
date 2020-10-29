package com.example.webook;
import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class BookList extends ArrayAdapter<Book> {
    private ArrayList<Book> books;
    private Context context;

    public BookList(@NonNull Context context, ArrayList<Book> books){
        super(context, 0, books);
    }
}
