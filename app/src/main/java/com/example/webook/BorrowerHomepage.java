package com.example.webook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

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

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("requests").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    QuerySnapshot querySnapshot = task.getResult();
                    List<DocumentSnapshot> list = querySnapshot.getDocuments();
                    for(int i = 0; i < list.size();i++){
                        final BookRequest bookRequest = list.get(i).toObject(BookRequest.class);
                        if(bookRequest.getRequester().contains(borrower.getUsername())){
                             db.collection("requests").document(bookRequest.getBook().getISBN()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(value.get("time") != null){
                                        if(((ArrayList<String>)value.get("requester")).contains(borrower.getUsername())){
                                            if(value.getString("status").equals("accepted")) {
                                                Toast toast = Toast.makeText(BorrowerHomepage.this,
                                                        "Owner have already set the deliver data", Toast.LENGTH_LONG);
                                                toast.show();
                                            }
                                        }
                                    }
                                    if(value.getString("status").equals("accepted")){
                                        if(value.get("time") == null){
                                            if(((ArrayList<String>)value.get("requester")).contains(borrower.getUsername())){
                                                String sentence = "Owner have accepted you request  ISBN = " + bookRequest.getBook().getISBN();
                                                Toast toast = Toast.makeText(BorrowerHomepage.this,sentence , Toast.LENGTH_LONG);
                                                toast.show();
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
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