package com.example.webook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BorrowerSearch extends AppCompatActivity {
    EditText newSearch = findViewById(R.id.borrower_search_input);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrower_search_page);

        // User press back in search page, back to main activity
        final Button backButton = findViewById(R.id.borrower_search_button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // User choose to search books
        final Button searchBook = findViewById(R.id.borrower_choose_books);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Search = newSearch.getText().toString();
            }
        });

        // User choose to search users
        final Button searchUser = findViewById(R.id.borrower_choose_users);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Search = newSearch.getText().toString();
                Intent intent = new Intent(getBaseContext(), BorrowerSearchBooks.class);
                intent.putExtra("SEARCH", Search);
                startActivity(intent);
            }
        });
    }
}
