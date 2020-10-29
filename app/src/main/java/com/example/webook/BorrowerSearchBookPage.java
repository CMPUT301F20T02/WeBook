package com.example.webook;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BorrowerSearchBookPage extends AppCompatActivity {
    ListView bookList;
    ArrayList<Book> dataList;
    ArrayAdapter<Book> bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrower_search_book_result);
        final String TAG = "User";
        // User's key for search
        Intent intent = getIntent();
        final String message = intent.getStringExtra(BorrowerSearch.EXTRA_MESSAGE);
        bookList = findViewById(R.id.borrower_search_book_list);
        dataList = new ArrayList<Book>();
        bookAdapter = new BookList(this, dataList);
        bookList.setAdapter(bookAdapter);
        final FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Books");

        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            dataList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId());
                                String status = (String) document.getData().get("title");
                                if(!status.equals("borrowed") && !status.equals("accepted")){
                                    String title = (String) document.getData().get("title");
                                    String author = (String) document.getData().get("author");
                                    String isbn = (String) document.getData().get("ISBN");
                                    String description = (String) document.getData().get("description");
                                    String owner = (String) document.getData().get("owner");

                                    if(title.contains(message)){
                                        dataList.add(new Book(title, isbn, author,  status,  owner, null,  description));
                                    }else if(author.contains(message)){
                                        dataList.add(new Book(title, isbn, author,  status,  owner, null,  description));
                                    }else if(isbn.contains(message)){
                                        dataList.add(new Book(title, isbn, author,  status,  owner, null,  description));
                                    }else if(description.contains(message)){
                                        dataList.add(new Book(title, isbn, author,  status,  owner, null,  description));
                                    }else if(owner.contains(message)){
                                        dataList.add(new Book(title, isbn, author,  status,  owner, null,  description));
                                    }
                                }
                            }
                            bookAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        collectionReference.whereEqualTo("UserName",message).get();
    }
}
