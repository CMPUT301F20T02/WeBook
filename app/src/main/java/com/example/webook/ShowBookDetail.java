package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowBookDetail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_book_profile);
        Intent intent = getIntent();
        final Book book = (Book)intent.getSerializableExtra(BorrowerSearchBookPage.EXTRA_MESSAGE);
        final TextView  tile = findViewById(R.id.book_profile_title);
        final TextView  author = findViewById(R.id.book_profile_author);
        final TextView  isbn = findViewById(R.id.book_profile_ISBN);
        final TextView  borrower = findViewById(R.id.book_rpofile_borrower_text);
        final TextView  status = findViewById(R.id.book_profile_status_text);
        final TextView  description = findViewById(R.id.book_profile_description);
        final TextView  owner = findViewById(R.id.book_profile_owner_text);
        tile.setText(book.getTitle());
        author.setText(book.getAuthor());
        isbn.setText(book.getISBN());
        borrower.setText(book.getBorrower());
        status.setText(book.getStatus());
        description.setText(book.getDescription());
        owner.setText(book.getOwner());
    }
}