package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * This is an activity shows homepage for usertype = borrower
 */
public class BorrowerHomepage extends AppCompatActivity {
    private ListView bookListView;
    private BookList bookList;
    private static ArrayList<Book> dataList;
    private Borrower borrower;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_homepage);
        bookListView = findViewById(R.id.borrower_book_list);

        Intent intent = getIntent();
        borrower = (Borrower) intent.getSerializableExtra("user");

        final Button searchButton = findViewById(R.id.borrower_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BorrowerHomepage.this, BorrowerSearch.class);
                intent.putExtra("borrower",borrower);
                startActivity(intent);
            }
        });
    }
}