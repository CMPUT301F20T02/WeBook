package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * This is activity shows a book's profile
 * @pram EXTRA_MESSAGE Books item to show in profile, clicked by user in formal actvity
 */
public class ShowBookDetail extends AppCompatActivity {

    private  DataBaseManager dataBaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_book_profile);
        Intent intent = getIntent();
        final Book book = (Book)intent.getSerializableExtra("book");
        final TextView  tile = findViewById(R.id.book_profile_title);
        final TextView  author = findViewById(R.id.book_profile_author);
        final TextView  isbn = findViewById(R.id.book_profile_ISBN);
        final TextView  borrower = findViewById(R.id.book_profile_borrower_text);
        final TextView  status = findViewById(R.id.book_profile_status_text);
        final TextView  description = findViewById(R.id.book_profile_description);
        final TextView  owner = findViewById(R.id.book_profile_owner_text);
        ImageView book_pic = findViewById(R.id.book_profile_icon);
        Button show = findViewById(R.id.borrower_book_request_button);
        show.setVisibility(View.INVISIBLE);
        tile.setText(book.getTitle());
        author.setText(book.getAuthor());
        isbn.setText(book.getISBN());
        borrower.setText(book.getBorrower());
        status.setText(book.getStatus());
        description.setText(book.getDescription());
        owner.setText(book.getOwner());

        dataBaseManager = new DataBaseManager();
        owner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dataBaseManager.getUserShowBookDetail(ShowBookDetail.this, book.getOwner());
            }
        });

        if (book.getBorrower() != null) {
            borrower.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataBaseManager.getUserShowBookDetail(ShowBookDetail.this, book.getBorrower());
                }
            });
        }
        if (book.getImage() == null){
            book_pic.setImageResource(R.drawable.book_icon);
        }else{
            Glide.with(this)
                    .load(book.getImage())
                    .into(book_pic);
        }
    }
}