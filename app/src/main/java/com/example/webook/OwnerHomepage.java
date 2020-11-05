package com.example.webook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class OwnerHomepage extends AppCompatActivity {
    private Owner owner;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final ArrayList<Book> bookArrayList = new ArrayList<Book>();
    private ListView bookListView;
    private BookList bookList;

    private TextView all;
    private TextView available;
    private TextView requested;
    private TextView accepted;
    private TextView borrowed;
    private BookList bookListAvailable;
    private BookList bookListRequested;
    private BookList bookListAccepted;
    private BookList bookListBorrowed;

    private ArrayList<Book> availableBookArrayList = new ArrayList<>();
    private ArrayList<Book> requestedBookArrayList = new ArrayList<>();
    private ArrayList<Book> acceptedBookArrayList = new ArrayList<>();
    private ArrayList<Book> borrowedBookArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_owner_homepage);

        TextView me = findViewById(R.id.owner_me_tab);
        TextView books = findViewById(R.id.owner_books_tab);

        Intent intent = getIntent();
        owner = (Owner) intent.getSerializableExtra("user");

        bookList = new BookList( OwnerHomepage.this, bookArrayList);
        bookListView = findViewById(R.id.owner_book_list);
        bookListView.setAdapter(bookList);

        String bookname = Integer.toString(bookArrayList.size());
        Toast toast = Toast.makeText(OwnerHomepage.this, bookname, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();

        all = findViewById(R.id.owner_all);
        available = findViewById(R.id.owner_available);
        requested = findViewById(R.id.owner_requested);
        accepted = findViewById(R.id.owner_accepted);
        borrowed = findViewById(R.id.owner_borrowed);

        CollectionReference bookRef = db.collection("books");
        bookRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                bookArrayList.clear();
                db.collection("users").document(owner.getUsername()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            bookArrayList.clear();
                            owner.setBookList(new ArrayList<String>());
                            DocumentSnapshot document = task.getResult();
                            ArrayList<String> bookisbn = (ArrayList<String>) document.get("bookList");
                            downloadBooks(bookisbn);
                        }

                    }
                });
            }
        });

        DocumentReference userRef = db.collection("users").document(owner.getUsername());
        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                bookArrayList.clear();
                owner.setBookList(new ArrayList<String>());
                ArrayList<String> bookisbn = (ArrayList<String>) value.get("bookList");
                downloadBooks(bookisbn);
            }
        });

        bookListAvailable = new BookList(OwnerHomepage.this, availableBookArrayList);
        bookListRequested = new BookList(OwnerHomepage.this, requestedBookArrayList);
        bookListAccepted = new BookList(OwnerHomepage.this, acceptedBookArrayList);
        bookListBorrowed = new BookList(OwnerHomepage.this, borrowedBookArrayList);



        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookname = Integer.toString(bookArrayList.size());
                Toast toast = Toast.makeText(OwnerHomepage.this, bookname, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
            }
        });

        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomepage.this, OwnerProfileActivity.class);
                intent.putExtra("user", owner);
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
                bookListView.setAdapter(bookListAvailable);
            }
        });

        requested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookListView.setAdapter(bookListRequested);
            }
        });

        accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookListView.setAdapter(bookListAccepted);
            }
        });

        borrowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookListView.setAdapter(bookListBorrowed);
            }
        });

    }

    public void downloadBooks(ArrayList<String> bookisbn) {

        CollectionReference bookRef = db.collection("books");
        for (int i = 0; i < bookisbn.size(); i++) {
            DocumentReference bookRef1 = bookRef.document(bookisbn.get(i));
            bookRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Book book = document.toObject(Book.class);

                            if (!owner.getBookList().contains(book.getISBN())){
                                owner.addBook(book.getISBN());
                                bookArrayList.add(book);
                                bookList.notifyDataSetChanged();
                            }
                            getAvailable();
                            getAccepted();
                            getRequested();
                            getBorrowed();
                        }
                    }
                }
            });
        }
    }

    public void getAvailable() {
        availableBookArrayList.clear();
        for (int i = 0; i < bookArrayList.size(); i++) {
            if (bookArrayList.get(i).getStatus().equals("available")) {
                availableBookArrayList.add(bookArrayList.get(i));
            }
        }
        bookListAvailable.notifyDataSetChanged();
    }

    public void getRequested() {
        requestedBookArrayList.clear();
        for (int i = 0; i < bookArrayList.size(); i++) {
            if (bookArrayList.get(i).getStatus().equals("requested")) {
                requestedBookArrayList.add(bookArrayList.get(i));
            }

        }
        bookListRequested.notifyDataSetChanged();
    }

    public void getAccepted() {
        acceptedBookArrayList.clear();
        for (int i = 0; i < bookArrayList.size(); i++) {
            if (bookArrayList.get(i).getStatus().equals("accepted")) {
                acceptedBookArrayList.add(bookArrayList.get(i));
            }

        }
        bookListAccepted.notifyDataSetChanged();

    }

    public void getBorrowed() {
        borrowedBookArrayList.clear();
        for (int i = 0; i < bookArrayList.size(); i++) {
            if (bookArrayList.get(i).getStatus().equals("borrowed")) {
                borrowedBookArrayList.add(bookArrayList.get(i));
            }

        }
        bookListBorrowed.notifyDataSetChanged();
    }

}