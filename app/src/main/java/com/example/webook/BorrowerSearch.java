package com.example.webook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class BorrowerSearch extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.BorrowerSearch.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        final EditText newSearch = findViewById(R.id.search_input);
        // User press back in search page, back to main activity
        // User choose to search books
        final Button searchBook = findViewById(R.id.search_choose_books);

        searchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BorrowerSearch.this, BorrowerSearchBookPage.class);
                String search = newSearch.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, search);
                startActivity(intent);
            }
        });

        // User choose to search users
        final Button searchUser = findViewById(R.id.search_choose_users);
        searchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BorrowerSearch.this, BorrowerSearchUserPage.class);
                String search = newSearch.getText().toString();

                intent.putExtra(EXTRA_MESSAGE, search);
                startActivity(intent);
            }
        });

    }
}
