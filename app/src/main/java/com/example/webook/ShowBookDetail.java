package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowBookDetail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book_detail);
        Intent intent = getIntent();
        final Book book = (Book)intent.getSerializableExtra(BorrowerSearchBookPage.EXTRA_MESSAGE);
        final TextView  username = findViewById(R.id.detail_UserName);
        final TextView  author = findViewById(R.id.detail_UserType);
        final TextView  isbn = findViewById(R.id.detail_Phone);
        final TextView  borrower = findViewById(R.id.detailed_borrower);
        final TextView  status = findViewById(R.id.detailed_status);
        final TextView  description = findViewById(R.id.detail_UserDescription);
        final TextView  owner = findViewById(R.id.detailed_UserEmail);
        username.setText(book.getTitle());
        author.setText(book.getAuthor());
        isbn.setText(book.getISBN());
        borrower.setText(book.getBorrower());
        status.setText(book.getStatus());
        description.setText(book.getDescription());
        owner.setText(book.getOwner());
    }
}