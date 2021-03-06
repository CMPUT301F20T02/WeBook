package com.example.webook;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This is an activity shows a book's profile
 * selectBook books selected to be shown in previous activity
 */
public class BorrowerBookProfile extends AppCompatActivity {
    private TextView title;
    private TextView author;
    private TextView isbn;
    private TextView status;
    private TextView owned_by;
    private TextView borrowed_by;
    private TextView description;
    private ImageView book_pic;
    private Button requestButton;
    private ArrayList<String> requesterList;
    private static final String TAG = "Sample";
    private Borrower borrower;
    private  DataBaseManager dataBaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_book_profile);

        book_pic = findViewById(R.id.book_profile_icon);
        title = findViewById(R.id.book_profile_title);
        author = findViewById(R.id.book_profile_author);
        isbn = findViewById(R.id.book_profile_ISBN);
        owned_by = findViewById(R.id.book_profile_owner_text);
        borrowed_by = findViewById(R.id.book_profile_borrower_text);
        status = findViewById(R.id.book_profile_status_text);
        description = findViewById(R.id.book_profile_description);
        dataBaseManager = new DataBaseManager();
        requestButton = findViewById(R.id.borrower_book_request_button);

        Intent intent = getIntent();
        final Book selectBook = (Book) intent.getSerializableExtra("selectBook");

        title.setText(selectBook.getTitle());
        author.setText(selectBook.getAuthor());
        isbn.setText(selectBook.getISBN());
        owned_by.setText("Owned by " + selectBook.getOwner());
        if (selectBook.getBorrower() != null) {
            borrowed_by.setText("Borrowed by " + selectBook.getBorrower());
        } else {
            borrowed_by.setText("Borrowed by ");
        }
        status.setText("Book Status: " + selectBook.getStatus());
        description.setText(selectBook.getDescription());

        borrower = (Borrower) intent.getSerializableExtra("borrower");
        requesterList = new ArrayList<>();
        requesterList.add(borrower.getUsername());
        requestButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
            final BookRequest newRequest = new BookRequest(selectBook, selectBook.getOwner(), requesterList, null, null);
            dataBaseManager.sendBookRequest(newRequest,borrower);
            Intent intent = new Intent();
            intent.putExtra("key", "request");
            setResult(RESULT_OK, intent);
            finish();
            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });

        owned_by.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dataBaseManager.getUserBorrowBookProfile(BorrowerBookProfile.this, selectBook.getOwner());
            }
        });

        if (selectBook.getBorrower() != null) {
            borrowed_by.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataBaseManager.getUserBorrowBookProfile(BorrowerBookProfile.this, selectBook.getBorrower());
                }
            });
        }

        if (selectBook.getImage() == null){
            book_pic.setImageResource(R.drawable.book_icon);
        }else{
            Glide.with(getApplicationContext())
                    .load(selectBook.getImage())
                    .into(book_pic);
        }
    }
}
