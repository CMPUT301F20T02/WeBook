package com.example.webook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private ArrayList<ListenerRegistration> listenerRegistrations;
    private ArrayList<String> Isbns;
    private BookList allBookList;
    private BookList borrowedBookList;
    private BookList requestedBookList;
    private BookList acceptedBookList;
    private TextView all;
    private TextView borrowed;
    private TextView requested;
    private TextView accepted;
    private TextView me;
    private TextView request;
    private String currentView = "all";
    private DataBaseManager dataBaseManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_homepage);
        bookListView = findViewById(R.id.borrower_book_list);
        final Intent intent = getIntent();
        borrower = (Borrower) intent.getSerializableExtra("user");
        final Button searchButton = findViewById(R.id.borrower_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BorrowerHomepage.this, BorrowerSearch.class);
                intent.putExtra("borrower",borrower);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        all = findViewById(R.id.borrower_all);
        accepted = findViewById(R.id.borrower_accepted);
        requested = findViewById(R.id.borrower_requested);
        borrowed = findViewById(R.id.borrower_borrowed);
        me = findViewById(R.id.borrower_me_tab);
        request = findViewById(R.id.borrower_requests_tab);
        dataBaseManager = new DataBaseManager();

        listenerRegistrations = new ArrayList<ListenerRegistration>();
        Isbns = new ArrayList<String>();

        allBooks = new ArrayList<>();
        borrowedBooks = new ArrayList<>();
        requestedBooks = new ArrayList<>();
        acceptedBooks = new ArrayList<>();
        allBookList = new BookList(this, allBooks);
        borrowedBookList = new BookList(this, borrowedBooks);
        acceptedBookList = new BookList(this, acceptedBooks);
        requestedBookList = new BookList(this, requestedBooks);

        bookListView.setAdapter(allBookList);
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("borrower homepage");
                Book book = (Book) bookListView.getItemAtPosition(position);
                Intent intent1 = new Intent(BorrowerHomepage.this, ShowBookDetail.class);
                intent1.putExtra("book", book);
                startActivity(intent1);
            }
        });

        db = new DataBaseManager();
        db.BorrowerHomepageBookAddSnapShotListener(this, borrower.getUsername());
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

        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BorrowerHomepage.this, BorrowerProfileActivity.class);
                intent.putExtra("user", borrower);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BorrowerHomepage.this, BorrowerRequestPageActivity.class);
                intent.putExtra("user", borrower);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        });

        dataBaseManager.BorrowerHomepageRequestListener(this, borrower.getUsername());

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

    public void addListenerRegistration(ListenerRegistration listenerRegistration){
        this.listenerRegistrations.add(listenerRegistration);
    }

    public void removeListenerRegistration(int index){
        ListenerRegistration listenerRegistration = this.listenerRegistrations.get(index);
        listenerRegistration.remove();
        this.listenerRegistrations.remove(index);
        this.Isbns.remove(index);
    }

    public void addIsbn(String isbn){
        this.Isbns.add(isbn);
    }

    public ArrayList<String> getIsbns(){
        return this.Isbns;
    }

}