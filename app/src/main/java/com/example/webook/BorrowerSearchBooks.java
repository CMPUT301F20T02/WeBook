package com.example.webook;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BorrowerSearchBooks extends AppCompatActivity {
    ListView bookList;
    ArrayList<Book> dataList;
    ArrayAdapter<Book> bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrower_search_book_result);

        // User's key for search
        String searchKey = getIntent().getStringExtra("SEARCH");

        bookList = findViewById(R.id.borrower_search_book_list);
        dataList = new ArrayList<>();
        bookAdapter = new ArrayAdapter<>(this, R.layout.book_list_content, dataList);

        bookList.setAdapter(bookAdapter);

        final Button backButton = findViewById(R.id.search_book_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
