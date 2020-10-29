package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class OwnerHomepage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_homepage);
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
        final ListView bookListView = findViewById(R.id.owner_book_list);
        final BookList bookList = new BookList(this, owner.getBookList());
        bookListView.setAdapter(bookList);

        TextView me = findViewById(R.id.owner_me_tab);

        TextView all = findViewById(R.id.owner_all);
        TextView available = findViewById(R.id.owner_available);
        TextView requested = findViewById(R.id.owner_requested);
        TextView accepted = findViewById(R.id.owner_accepted);
        TextView borrowed = findViewById(R.id.owner_borrowed);

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