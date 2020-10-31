package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class OwnerHomepage extends AppCompatActivity {
    ListView bookList;
    ArrayAdapter<Book> bookAdapter;
    public HashMap<String, String> data = new HashMap<>();
    static ArrayList<Book> dataList; //for test
    private Request currentRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final FirebaseFirestore db;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner__homepage);

        Book book1 = new Book("Book1", "12345","rain","No-requested","Rain",null,"testbook1");
        Book book2 = new Book("book2","67890","peter","requested","peter",null,"testbook2");
        bookList = findViewById(R.id.owner_book_list);
        dataList = new ArrayList<>();
        dataList.add(book1);
        dataList.add(book2);
        bookAdapter = new CustomList(this, dataList);
        bookList.setAdapter(bookAdapter);

        final Intent intent = new Intent(this, OwnerBookProfile.class);
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book selectBook = dataList.get(i);
                intent.putExtra("selectBook", selectBook);
                startActivity(intent);
            }

        });
    }
}