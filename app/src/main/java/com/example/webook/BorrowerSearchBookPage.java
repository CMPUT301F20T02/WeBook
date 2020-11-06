package com.example.webook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BorrowerSearchBookPage extends AppCompatActivity {
    ListView bookList;
    ArrayList<Book> dataList;
    ArrayAdapter<Book> bookAdapter;
    public static final String EXTRA_MESSAGE = "selectBook";
    private Borrower borrower;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        final String TAG = "Book";
        // User's key for search
        Intent intent = getIntent();
        final String message = intent.getStringExtra(BorrowerSearch.EXTRA_MESSAGE);
        bookList = findViewById(R.id.search_result_list);
        dataList = new ArrayList<Book>();
        bookAdapter = new BookList(this, dataList);
        bookList.setAdapter(bookAdapter);
        final FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("books");

        borrower = (Borrower)intent.getSerializableExtra("borrower");

        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(BorrowerSearchBookPage.this,BorrowerBookProfile.class);
                Book book = dataList.get(i);
                intent.putExtra(EXTRA_MESSAGE, book);
                intent.putExtra("borrower", borrower);
                startActivity(intent);
            }
        });

        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            dataList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId());
                                String status = (String) document.getData().get("status");
                                if(!status.equals("borrowed") && !status.equals("accepted")){
                                    String title = (String) document.getData().get("title");
                                    String author = (String) document.getData().get("author");
                                    String isbn = (String) document.getData().get("isbn");
                                    String description = (String) document.getData().get("description");
                                    String owner = (String) document.getData().get("owner");

                                    if(title.contains(message)) {
                                            dataList.add(new Book(title, isbn, author, status, owner, null, description));
                                    }
                                        else if (author.contains(message)) {
                                            dataList.add(new Book(title, isbn, author, status, owner, null, description));
                                    }
                                        else if(isbn.contains(message)) {
                                            dataList.add(new Book(title, isbn, author, status, owner, null, description));
                                    }else if(description!= null){
                                        if(description.contains(message)) {
                                            dataList.add(new Book(title, isbn, author, status, owner, null, description));
                                        }
                                    }
                                }
                            }
                            bookAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        collectionReference.get();

    }
}
