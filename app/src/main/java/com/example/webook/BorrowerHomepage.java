package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BorrowerHomepage extends AppCompatActivity {
    public static final Book EXTRA_BOOK = new Book("EXTRABOOK","EXTRAISBN","EXTRAAUTHOR","EXTRASTATE","EXTRAOWNER",null,"EXTRADES" );
    public static final Request EXTRA_REQUEST = new Request(EXTRA_BOOK,"extra Requestee","extra Requester",null,null);
    ListView bookList;
    ArrayAdapter<Book> bookAdapter;
    public HashMap<String, String> data = new HashMap<>();
    static ArrayList<Book> dataList; //for test
    private Request currentRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // Button requestButton;
        final FirebaseFirestore db;
        //requestButton = findViewById(R.id.Request_button);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_homepage);
        //Book book1;
        //db = FirebaseFirestore.getInstance();

         // test data
        Book book1 = new Book("Book1", "12345","rain","No-requested","Rain",null,"testbook1");
        Book book2 = new Book("book2","67890","peter","requested","peter",null,"testbook2");
        //dataList = new ArrayList<>();

        //bookList.setAdapter(bookAdapter);


        bookList = findViewById(R.id.borrowerBookList);

        dataList = new ArrayList<>();

        dataList.add(book1);
        dataList.add(book2);

        bookAdapter = new CustomList(this, dataList);

        bookList.setAdapter(bookAdapter);


        final Intent intent = new Intent(this,BorrowerRequestPage.class);
        bookList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                //Request newRequest = ()
                //intent.putExtra(EXTRA_REQUEST, message);
                startActivity(intent);
                return true;
                };


        });





    }






}