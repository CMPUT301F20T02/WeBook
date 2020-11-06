package com.example.webook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
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
/**
 * This is an activity shows homepage for usertype = owner
 */
public class OwnerHomepage extends AppCompatActivity {
    private Owner owner;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ArrayList<Book> bookArrayList = new ArrayList<Book>();
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

    private String currentListView = "all";
    private DataBaseManager dataBaseManager = new DataBaseManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_owner_homepage);

        TextView me = findViewById(R.id.owner_me_tab);
        TextView books = findViewById(R.id.owner_books_tab);

        Intent intent = getIntent();
        owner = (Owner) intent.getSerializableExtra("user");


        bookListView = findViewById(R.id.owner_book_list);


        all = findViewById(R.id.owner_all);
        available = findViewById(R.id.owner_available);
        requested = findViewById(R.id.owner_requested);
        accepted = findViewById(R.id.owner_accepted);
        borrowed = findViewById(R.id.owner_borrowed);

        dataBaseManager.OwnerHomePageAddBookSnapShotListener(this, owner.getUsername());
        dataBaseManager.OwnerHomePageAddUserSnapShotListener(this, owner.getUsername());

        bookList = new BookList( OwnerHomepage.this, bookArrayList);
        bookListView.setAdapter(bookList);
        bookListAvailable = new BookList(OwnerHomepage.this, availableBookArrayList);
        bookListRequested = new BookList(OwnerHomepage.this, requestedBookArrayList);
        bookListAccepted = new BookList(OwnerHomepage.this, acceptedBookArrayList);
        bookListBorrowed = new BookList(OwnerHomepage.this, borrowedBookArrayList);



        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("printed here "+ owner.getBookList().toString());
                for (int i = 0; i < 29; i++){
                    System.out.println(bookArrayList.get(i).getTitle());
                }
                bookList = new BookList( OwnerHomepage.this, bookArrayList);
                bookListView.setAdapter(bookList);
                bookList.notifyDataSetChanged();
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
                currentListView = "all";
            }
        });

        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookListView.setAdapter(bookListAvailable);
                currentListView = "available";
            }
        });

        requested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookListView.setAdapter(bookListRequested);
                currentListView = "requested";
            }
        });

        accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookListView.setAdapter(bookListAccepted);
                currentListView = "accepted";
            }
        });

        borrowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookListView.setAdapter(bookListBorrowed);
                currentListView = "borrowed";
            }
        });

        //This is a comment
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(OwnerHomepage.this, OwnerBookProfile.class);
                Book selectBook;
                if (currentListView.equals("all")) {
                    selectBook = bookArrayList.get(i);
                } else if (currentListView.equals("available")){
                    selectBook = availableBookArrayList.get(i);
                } else if (currentListView.equals("requested")) {
                    selectBook = requestedBookArrayList.get(i);
                } else if (currentListView.equals("accepted")) {
                    selectBook = acceptedBookArrayList.get(i);
                } else if (currentListView.equals("borrowed")) {
                    selectBook = borrowedBookArrayList.get(i);
                } else{
                    selectBook = null;
                    System.out.println("Error");
                }
                intent.putExtra("selectBook", selectBook);
                startActivity(intent);
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

    public void setBookList(){
        this.bookList = new BookList(OwnerHomepage.this, this.bookArrayList);
        this.bookListView.setAdapter(bookList);
    }

    public void dataChanged(){

        this.bookList.notifyDataSetChanged();
    }

    public ArrayList<String> getOwnerBookList(){
        return this.owner.getBookList();
    }

    public void ownerAddBook(String isbn){
        this.owner.addBook(isbn);
    }

    public void ownerSetBookList(ArrayList<String> bookList){
        this.owner.setBookList(bookList);
    }

    public void setBookArrayList(ArrayList<Book> bookArrayList){
        this.bookArrayList = bookArrayList;
    }

    public void addBookArrayList(Book book){
        this.bookArrayList.add(book);
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