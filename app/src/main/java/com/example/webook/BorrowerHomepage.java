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
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        all = findViewById(R.id.borrower_all);
        accepted = findViewById(R.id.borrower_accepted);
        requested = findViewById(R.id.borrower_requested);
        borrowed = findViewById(R.id.borrower_borrowed);
        me = findViewById(R.id.borrower_me_tab);
        request = findViewById(R.id.borrower_requests_tab);

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
                Intent intent = new Intent(BorrowerHomepage.this, OwnerRequestPageActivity.class);
                intent.putExtra("user", borrower);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        });

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        /*db.collection("requests").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    QuerySnapshot querySnapshot = task.getResult();
                    List<DocumentSnapshot> list = querySnapshot.getDocuments();
                    for(int i = 0; i < list.size();i++){
                        final BookRequest bookRequest = list.get(i).toObject(BookRequest.class);
                        final String isbnHere = bookRequest.getBook().getISBN();
                        if(bookRequest.getRequester().contains(borrower.getUsername())){
                            System.out.println("goosdsasdasdd");
                            ListenerRegistration listenerRegistration = db.collection("requests").document(bookRequest.getBook().getISBN()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(value.get("time") != null){
                                        if(((ArrayList<String>)value.get("requester")).contains(borrower.getUsername())){
                                            if(value.getString("status").equals("accepted")) {
                                                String sentence = "Owner have already set the deliver data Isbn \n ISBN = " + bookRequest.getBook().getISBN();
                                                Toast toast = Toast.makeText(BorrowerHomepage.this,
                                                        sentence, Toast.LENGTH_LONG);
                                                toast.show();
                                            }
                                        }
                                    }
                                    if(value.getString("status").equals("accepted")){
                                        if(value.get("time") == null){
                                            if(((ArrayList<String>)value.get("requester")).contains(borrower.getUsername())){
                                                String sentence = "Owner have accepted you request \n ISBN = " + bookRequest.getBook().getISBN();
                                                Toast toast = Toast.makeText(BorrowerHomepage.this,sentence , Toast.LENGTH_LONG);
                                                toast.show();
                                            }
                                        }
                                    }
                                }
                            });
                            listenerRegistrations.add(listenerRegistration);
                            Isbns.add(isbnHere);
                        }
                    }
                }
            }
        });*/
        db.collection("requests").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    System.err.println("Listen failed: " + e);
                    return;
                }

                for (final DocumentChange dc : value.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            final String isbnHere = dc.getDocument().getId();
                            if (((ArrayList<String>)dc.getDocument().get("requester")).contains(borrower.getUsername())){
                                ListenerRegistration listenerRegistration = db.collection("requests").document(isbnHere).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                        if(value.get("time") != null){
                                            if(((ArrayList<String>)value.get("requester")).contains(borrower.getUsername())){
                                                if(value.getString("status").equals("accepted")) {
                                                    BookRequest requestHere = dc.getDocument().toObject(BookRequest.class);
                                                    Book bookHere = requestHere.getBook();
                                                    String sentence = "Owner have already set the deliver data \n Book: " + bookHere.getTitle();
                                                    Toast toast = Toast.makeText(BorrowerHomepage.this,
                                                            sentence, Toast.LENGTH_LONG);
                                                    toast.show();
                                                }
                                            }
                                        }
                                        if(Objects.equals(value.getString("status"), "accepted")){
                                            if(value.get("time") == null){
                                                if(((ArrayList<String>)value.get("requester")).contains(borrower.getUsername())){
                                                    BookRequest requestHere = dc.getDocument().toObject(BookRequest.class);
                                                    Book bookHere = requestHere.getBook();
                                                    String sentence = "Owner have accepted you request \n Book: " + bookHere.getTitle();
                                                    Toast toast = Toast.makeText(BorrowerHomepage.this,sentence , Toast.LENGTH_LONG);
                                                    toast.show();
                                                }
                                            }
                                        }
                                    }
                                });
                                listenerRegistrations.add(listenerRegistration);
                                Isbns.add(isbnHere);
                                System.out.println("ncie");
                            }
                            break;
                        case MODIFIED:
                            final String isbnHere1 = dc.getDocument().getId();
                            final BookRequest bookRequestHere = dc.getDocument().toObject(BookRequest.class);
                            final Book bookHere = bookRequestHere.getBook();
                            if(Isbns.contains(isbnHere1)) {
                                if (!((ArrayList<String>) dc.getDocument().get("requester")).contains(borrower.getUsername())) {
                                    int index = Isbns.indexOf(isbnHere1);
                                    ListenerRegistration listenerRegistrationHere = listenerRegistrations.get(index);
                                    listenerRegistrationHere.remove();
                                    Isbns.remove(index);
                                    listenerRegistrations.remove(index);
                                }
                            }else{
                                if (((ArrayList<String>) dc.getDocument().get("requester")).contains(borrower.getUsername())) {
                                    ListenerRegistration listenerRegistration = db.collection("requests").document(isbnHere1).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if(value.get("time") != null){
                                                if(((ArrayList<String>)value.get("requester")).contains(borrower.getUsername())){
                                                    if(value.getString("status").equals("accepted")) {
                                                        String sentence = "Owner have already set the deliver data  \n Book: " + bookHere.getTitle();
                                                        Toast toast = Toast.makeText(BorrowerHomepage.this,
                                                                sentence, Toast.LENGTH_LONG);
                                                        toast.show();
                                                    }
                                                }
                                            }
                                            if(value.getString("status").equals("accepted")){
                                                if(value.get("time") == null){
                                                    if(((ArrayList<String>)value.get("requester")).contains(borrower.getUsername())){
                                                        String sentence = "Owner have accepted you request \n Book: " + bookHere.getTitle();
                                                        Toast toast = Toast.makeText(BorrowerHomepage.this,sentence , Toast.LENGTH_LONG);
                                                        toast.show();
                                                    }
                                                }
                                            }
                                        }
                                    });
                                    System.out.println("good");
                                    listenerRegistrations.add(listenerRegistration);
                                    Isbns.add(isbnHere1);
                                }
                            }

                            break;
                        case REMOVED:
                            System.out.println("Removed city: " + dc.getDocument().getData());
                            break;
                        default:
                            break;
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