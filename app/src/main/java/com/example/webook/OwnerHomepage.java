package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class OwnerHomepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner__homepage);
        ListView app = findViewById(R.id.book_list);
        Owner owner = new Owner("asd","asadsda","asdsad","qwewqe","owner");
        owner.addBook("TITLE","ISBN","ASD",null,"sdassd");
        owner.addBook("TITLE","ISBN","ASD",null,"sdassd");
        owner.addBook("TITLE","ISBN","ASD",null,"sdassd");
        owner.addBook("TITLE","ISBN","ASD",null,"sdassd");
        owner.addBook("TITLE","ISBN","ASD",null,"sdassd");
        owner.addBook("TITLE","ISBN","ASD",null,"sdassd");
        owner.addBook("TITLE","ISBN","ASD",null,"sdassd");
        owner.addBook("TITLE","ISBN","ASD",null,"sdassd");
        owner.addBook("TITLE","ISBN","ASD",null,"sdassd");
        BookList adaptor = new BookList(this, owner.getBookList());
        app.setAdapter(adaptor);
    }

}