package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class OwnerHomepage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner__homepage);

        final Owner owner = new Owner("asd", "asadsda", "asdsad", "qwewqe", "owner");
        owner.addBook("TITLE","ISBN","ASD",null,"sdassd", "available");
        owner.addBook("TITLE","ISBN","ASD",null,"sdassd", "available");
        owner.addBook("TITLE","ISBN","ASD",null,"sdassd", "available");
        owner.addBook("TITLE","ISBN","ASD",null,"sdassd", "borrowed");
        owner.addBook("TITLE","ISBN","ASD",null,"sdassd", "borrowed");
        owner.addBook("TITLE","ISBN","ASD",null,"sdassd", "borrowed");
        owner.addBook("TITLE","ISBN","ASD",null,"sdassd", "requested");
        owner.addBook("TITLE","ISBN","ASD",null,"sdassd", "accepted");
        owner.addBook("TITLE","ISBN","ASD",null,"sdassd", "accepted");
        final ListView bookListView = findViewById(R.id.book_list);
        BookList bookList = new BookList(this, owner.getBookList());
        bookListView.setAdapter(bookList);

        TextView me = findViewById(R.id.me);

        TextView all = findViewById(R.id.all);
        TextView available = findViewById(R.id.available);
        TextView requested = findViewById(R.id.requested);
        TextView accepted = findViewById(R.id.accepted);
        TextView borrowed = findViewById(R.id.borrowed);

        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomepage.this, OwnerProfileActivity.class);

                startActivity(intent);
            }
        });

        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookList bookList1 = new BookList(OwnerHomepage.this, owner.getAvailable());
                bookListView.setAdapter(bookList1);
            }
        });




    }

}