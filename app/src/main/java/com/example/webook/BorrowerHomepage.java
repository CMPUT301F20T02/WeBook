package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;

public class BorrowerHomepage extends AppCompatActivity {
    ListView bookList;
    ArrayAdapter<Book> bookAdapter;
    static ArrayList<Book> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_homepage);
        final Button searchButton = findViewById(R.id.borrower_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BorrowerHomepage.this, BorrowerSearch.class);
                startActivity(intent);
            }
        });
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Book selectBook = dataList.get(position);
                Intent intent = new Intent(BorrowerHomepage.this, BorrowerBookProfile.class);
                intent.putExtra("selectBook", selectBook);
                startActivity(intent);
            }
        });
    }
}