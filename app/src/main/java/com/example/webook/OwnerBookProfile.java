package com.example.webook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class OwnerBookProfile extends AppCompatActivity {
    private TextView title_text;
    private TextView author_text;
    private TextView isbn_text;
    private Button requestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_book_profile);

        title_text = findViewById(R.id.owner_book_profile_title_text);
        author_text = findViewById(R.id.owner_book_profile_author_text);
        isbn_text = findViewById(R.id.owner_book_profile_isbn_text);
        requestButton = findViewById(R.id.owner_request_button);

        final Intent intent = getIntent();
        final Book selectBook = (Book) intent.getSerializableExtra("selectBook");

        title_text.setText(selectBook.getTitle());
        author_text.setText(selectBook.getAuthor());
        isbn_text.setText(selectBook.getISBN());

        final Intent intent2 = new Intent(this, SameBookRequestList.class);
        requestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent2.putExtra("selectBook", selectBook);
                startActivity(intent2);
            }
        });

    }
}