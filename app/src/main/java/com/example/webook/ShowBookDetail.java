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
        final TextView  username = findViewById(R.id.user_username);
        final TextView  author = findViewById(R.id.user_userType);
        final TextView  isbn = findViewById(R.id.user_phone);
        final TextView  borrower = findViewById(R.id.borrower_book_borrower);
        final TextView  status = findViewById(R.id.borrower_book_status);
        final TextView  description = findViewById(R.id.user_description);
        final TextView  owner = findViewById(R.id.user_email);
        username.setText(book.getTitle());
        author.setText(book.getAuthor());
        isbn.setText(book.getISBN());
        borrower.setText(book.getBorrower());
        status.setText(book.getStatus());
        description.setText(book.getDescription());
        owner.setText(book.getOwner());
    }
}