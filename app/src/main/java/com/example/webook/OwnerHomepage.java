package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
        Drawable image = getResources().getDrawable(R.drawable.book_icon);
        final Owner owner = new Owner("asd", "asadsda", "asdsad", "qwewqe");
        owner.addBook("TITLE","ISBN","ASD",image,"sdassd", "available");
        owner.addBook("TITLE","ISBN","ASD",image,"sdassd", "available");
        owner.addBook("TITLE","ISBN","ASD",image,"sdassd", "available");
        owner.addBook("TITLE","ISBN","ASD",image,"sdassd", "borrowed");
        owner.addBook("TITLE","ISBN","ASD",image,"sdassd", "borrowed");
        owner.addBook("TITLE","ISBN","ASD",image,"sdassd", "borrowed");
        owner.addBook("TITLE","ISBN","ASD",image,"sdassd", "requested");
        owner.addBook("TITLE","ISBN","ASD",image,"sdassd", "accepted");
        owner.addBook("TITLE","ISBN","ASD",image,"sdassd", "accepted");
        final ListView bookListView = findViewById(R.id.book_list);
        final BookList bookList = new BookList(this, owner.getBookList());
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

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookListView.setAdapter(bookList);
            }
        });

        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookList bookList1 = new BookList(OwnerHomepage.this, owner.getAvailable());
                bookListView.setAdapter(bookList1);
            }
        });

        requested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookList bookList2 = new BookList(OwnerHomepage.this, owner.getRequested());
                bookListView.setAdapter(bookList2);
            }
        });

        accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookList bookList3 = new BookList(OwnerHomepage.this, owner.getAccepted());
                bookListView.setAdapter(bookList3);
            }
        });

        borrowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookList bookList4 = new BookList(OwnerHomepage.this, owner.getBorrowed());
                bookListView.setAdapter(bookList4);
            }
        });



    }

}