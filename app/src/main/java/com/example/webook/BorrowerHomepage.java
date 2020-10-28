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
    //ListView bookList;
    //ArrayAdapter<Book> bookAdapter;
    //public HashMap<String, String> data = new HashMap<>();
    //static ArrayList<Book> dataList; //for test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // Button requestButton;
        //final FirebaseFirestore db;
        //requestButton = findViewById(R.id.Request_button);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_homepage);
        //Book book1;
        //db = FirebaseFirestore.getInstance();

         // test data
        //book1 = new Book("Book1", "12345","rain","No-requested","Rain",null,"testbook");
        //dataList = new ArrayList<>();                           //for test
                                    // for test

        //bookList.setAdapter(bookAdapter);

        //bookList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

           // @Override
            //public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {


            //    };


        //});



    }






}