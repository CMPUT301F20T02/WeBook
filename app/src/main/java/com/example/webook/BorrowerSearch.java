package com.example.webook;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
/**
 * This activity shows search interface when search is clicked on the borrower homepage
 * Borrower click on Books or Users to chose search type and jump to next activity
 */
public class BorrowerSearch extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.BorrowerSearch.MESSAGE";
    private Borrower borrower;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        final EditText newSearch = findViewById(R.id.search_input);
        // User press back in search page, back to main activity
        // User choose to search books
        final Button searchBook = findViewById(R.id.search_choose_books);
        Intent intent = getIntent();
        borrower = (Borrower)intent.getSerializableExtra("borrower");

        searchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BorrowerSearch.this, BorrowerSearchBookPage.class);
                String search = newSearch.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, search);
                intent.putExtra("borrower", borrower);
                startActivity(intent);
            }
        });

        // User choose to search users
        final Button searchUser = findViewById(R.id.search_choose_users);
        searchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BorrowerSearch.this, BorrowerRequestDelivery.class);
                String search = newSearch.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, search);
                startActivity(intent);
                //startActivity(intent);
            }
        });


    }

}
