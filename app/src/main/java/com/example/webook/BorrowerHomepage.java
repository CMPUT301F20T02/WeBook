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

import java.sql.SQLOutput;
import java.util.ArrayList;
/**
 * This is an activity shows homepage for usertype = borrower
 */
public class BorrowerHomepage extends AppCompatActivity {
    private ListView bookListView;
    private BookList bookList;
    private static ArrayList<Book> dataList;
    private Borrower borrower;
    private DataBaseManager db;
    private ArrayList<Book> allBooks;
    private ArrayList<Book> borrowedBooks;
    private ArrayList<Book> requestedBooks;
    private ArrayList<Book> acceptedBooks;
    private BookList allBookList;
    private BookList borrowedBookList;
    private BookList requestedBookList;
    private BookList acceptedBookList;
    private TextView all;
    private TextView borrowed;
    private TextView requested;
    private TextView accepted;
    private String currentView = "all";

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

        all = findViewById(R.id.borrower_all);
        accepted = findViewById(R.id.borrower_accepted);
        requested = findViewById(R.id.borrower_requested);
        borrowed = findViewById(R.id.borrower_borrowed);

        allBooks = new ArrayList<>();
        borrowedBooks = new ArrayList<>();
        requestedBooks = new ArrayList<>();
        acceptedBooks = new ArrayList<>();
        allBookList = new BookList(this, allBooks);
        borrowedBookList = new BookList(this, borrowedBooks);
        acceptedBookList = new BookList(this, acceptedBooks);
        requestedBookList = new BookList(this, requestedBooks);

        bookListView.setAdapter(allBookList);
        db = new DataBaseManager();
        db.BorrowerHomepageBookAddSnapShotListener(this, borrower.getUsername());

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookListView.setAdapter(allBookList);
                currentView = "all";
            }
        });

        requested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookListView.setAdapter(requestedBookList);
                currentView = "requested";
            }
        });

        accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookListView.setAdapter(acceptedBookList);
                currentView = "accepted";
            }
        });

        borrowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookListView.setAdapter(borrowedBookList);
                currentView = "borrowed";
            }
        });
    }

    public void setBorrowedBooks(ArrayList<Book> borrowedBooks) {
        this.borrowedBooks.clear();
        this.borrowedBooks.addAll(borrowedBooks);
        this.borrowedBookList.notifyDataSetChanged();
    }

    public void setRequestedBooks(ArrayList<Book> requestedBooks) {
        this.requestedBooks.clear();
        this.requestedBooks.addAll(requestedBooks);
        this.requestedBookList.notifyDataSetChanged();
    }

    public void setAcceptedBooks(ArrayList<Book> acceptedBooks) {
        this.acceptedBooks.clear();
        this.acceptedBooks.addAll(acceptedBooks);
        this.acceptedBookList.notifyDataSetChanged();
    }

    public void updateAllBooks(){
        this.allBooks.clear();
        this.allBooks.addAll(this.acceptedBooks);
        this.allBooks.addAll(this.requestedBooks);
        this.allBooks.addAll(this.borrowedBooks);
        allBookList.notifyDataSetChanged();
    }
}