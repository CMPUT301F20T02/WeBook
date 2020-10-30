package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SameBookRequestList extends AppCompatActivity {
    private ListView sameBookRequestList;
    private ArrayAdapter<BookRequest> bookAdapter;
    private static ArrayList<BookRequest> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_same_book_list);

        sameBookRequestList = findViewById(R.id.same_book_request_list);

        final Intent intent = getIntent();
        final Book selectBook = (Book) intent.getSerializableExtra("selectBook");

        BookRequest newRequest = new BookRequest(selectBook, selectBook.getOwner(), "requester1", null, null);

        dataList = new ArrayList<>();
        dataList.add(newRequest);
        bookAdapter = new BookRequestList(this, dataList);
        sameBookRequestList.setAdapter(bookAdapter);


    }
}