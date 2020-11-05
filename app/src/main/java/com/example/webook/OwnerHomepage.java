package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class OwnerHomepage extends AppCompatActivity {
    private ListView bookListView;
    private BookList bookList;
    private final ArrayList<Book> bookArrayList = new ArrayList<Book>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_homepage);

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(OwnerHomepage.this, OwnerBookProfile.class);
                Book selectBook = bookArrayList.get(i);
                intent.putExtra("selectBook", selectBook);
                startActivity(intent);
            }

        });
    }
}