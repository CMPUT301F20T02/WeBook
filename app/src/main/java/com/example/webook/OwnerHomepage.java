package com.example.webook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
/**
 * This is an activity shows homepage for usertype = owner
 */
public class OwnerHomepage extends AppCompatActivity {
    private Owner owner;
    private ArrayList<Book> bookArrayList = new ArrayList<Book>();
    private ListView bookListView;
    private BookList bookList;
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
    private Integer before;
    private Button search;
    private ListenerRegistration listenerRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_owner_homepage);

        TextView me = findViewById(R.id.owner_me_tab);
        TextView books = findViewById(R.id.owner_books_tab);
        TextView request = findViewById(R.id.owner_requests_tab);
        search = findViewById(R.id.owner_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomepage.this,OwnerSearch.class);
                startActivity(intent);
            }
        });
        before = 0;
        Intent intent = getIntent();
        owner = (Owner) intent.getSerializableExtra("user");

        bookListView = findViewById(R.id.owner_book_list);


        TextView all = findViewById(R.id.owner_all);
        TextView available = findViewById(R.id.owner_available);
        TextView requested = findViewById(R.id.owner_requested);
        TextView accepted = findViewById(R.id.owner_accepted);
        TextView borrowed = findViewById(R.id.owner_borrowed);

        dataBaseManager.OwnerHomePageAddBookSnapShotListener(this, owner.getUsername());
        dataBaseManager.OwnerHomePageAddUserSnapShotListener(this, owner.getUsername());

        bookList = new BookList( OwnerHomepage.this, bookArrayList);
        bookListView.setAdapter(bookList);
        bookListAvailable = new BookList(OwnerHomepage.this, availableBookArrayList);
        bookListRequested = new BookList(OwnerHomepage.this, requestedBookArrayList);
        bookListAccepted = new BookList(OwnerHomepage.this, acceptedBookArrayList);
        bookListBorrowed = new BookList(OwnerHomepage.this, borrowedBookArrayList);

        requested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomepage.this, OwnerRequestPageActivity.class);
                intent.putExtra("user", owner);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        });


        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomepage.this, OwnerProfileActivity.class);
                intent.putExtra("user", owner);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomepage.this, OwnerRequestPageActivity.class);
                intent.putExtra("user", owner);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
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
                intent.putExtra("user", owner);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }

        });
        dataBaseManager.OwnerHomePageNotify(this, owner.getUsername());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        listenerRegistration.remove();
        finish();
    }

    /**
     * This sets the resets the bookList, This step is required for the "all" listView to behave correctly
     */
    public void setBookList(){
        this.bookList = new BookList(OwnerHomepage.this, this.bookArrayList);
        this.bookListView.setAdapter(bookList);
    }


    /**
     * This updates the listView
     */
    public void dataChanged(){
        this.bookList.notifyDataSetChanged();
    }


    /**
     * This returns the owner's book list
     * @return
     * this is the current owner book list
     */
    public ArrayList<String> getOwnerBookList(){
        return this.owner.getBookList();
    }


    /**
     * This adds a book to the owner's book list
     * @param isbn
     * this is teh isbn of the book to be added
     */
    public void ownerAddBook(String isbn){
        this.owner.addBook(isbn);
    }


    /**
     * This sets the owner's book list
     * @param bookList
     * this is the candidate book list
     */
    public void ownerSetBookList(ArrayList<String> bookList){
        this.owner.setBookList(bookList);
    }


    /**
     * This sets the bookArrayList variable
     * @param bookArrayList
     * this is the candidate bookArrayList
     */
    public void setBookArrayList(ArrayList<Book> bookArrayList){
        this.bookArrayList = bookArrayList;
    }


    /**
     * This adds a book to the bookArrayList
     * @param book
     * this is the candidate book
     */
    public void addBookArrayList(Book book){
        this.bookArrayList.add(book);
    }


    /**
     * This gets the books that are available
     * then add them to bookListAvailable
     */
    public void getAvailable() {
        availableBookArrayList.clear();
        for (int i = 0; i < bookArrayList.size(); i++) {
            if (bookArrayList.get(i).getStatus().equals("available")) {
                availableBookArrayList.add(bookArrayList.get(i));
            }
        }
        bookListAvailable.notifyDataSetChanged();
    }


    /**
     * This gets the books that are requested
     * then add them to bookListRequested
     */
    public void getRequested() {
        requestedBookArrayList.clear();
        for (int i = 0; i < bookArrayList.size(); i++) {
            if (bookArrayList.get(i).getStatus().equals("requested")) {
                requestedBookArrayList.add(bookArrayList.get(i));
            }
        }
        bookListRequested.notifyDataSetChanged();
    }


    /**
     * This gets the books that are accepted
     * then add them to bookListAccepted
     */
    public void getAccepted() {
        acceptedBookArrayList.clear();
        for (int i = 0; i < bookArrayList.size(); i++) {
            if (bookArrayList.get(i).getStatus().equals("accepted")) {
                acceptedBookArrayList.add(bookArrayList.get(i));
            }
        }
        bookListAccepted.notifyDataSetChanged();
    }


    /**
     * This gets the books that are borrowed
     * then add them to bookListBorrowed
     */
    public void getBorrowed() {
        borrowedBookArrayList.clear();
        for (int i = 0; i < bookArrayList.size(); i++) {
            if (bookArrayList.get(i).getStatus().equals("borrowed")) {
                borrowedBookArrayList.add(bookArrayList.get(i));
            }
        }
        bookListBorrowed.notifyDataSetChanged();
    }

    public Integer getBefore() {
        return before;
    }

    public void setBefore(int before){
        this.before = before;
    }

    public void setListenerRegistration(ListenerRegistration l){
        this.listenerRegistration = l;
    }
}