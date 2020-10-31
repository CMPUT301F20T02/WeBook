package com.example.webook;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BorrowerBookProfile extends AppCompatActivity {
    private TextView title;
    private TextView author;
    private TextView isbn;
    private TextView status;
    private TextView owner;
    private TextView description;
    private Button requestButton;
    private static final String TAG = "Sample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_borrower_book_profile);

    title = findViewById(R.id.book_borrower_request_title_text);
    author = findViewById(R.id.book_borrower_request_author_text);
    isbn = findViewById(R.id.book_borrower_request_isbn_text);
    owner = findViewById(R.id.book_borrower_request_owner_text);
    status = findViewById(R.id.book_borrower_request_status_text);
    description = findViewById(R.id.book_borrower_request_description_text);

//    isbn_text = findViewById(R.id.isbn_text);
    requestButton = findViewById(R.id.request_button);

    Intent intent = getIntent();
    final Book selectBook = (Book) intent.getSerializableExtra("selectBook");

    title.setText(selectBook.getTitle());
    author.setText(selectBook.getAuthor());
    isbn.setText(selectBook.getISBN());
    status.setText(selectBook.getStatus());
    owner.setText(selectBook.getOwner());
    status.setText(selectBook.getStatus());

    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    final Map<String, Object> requests = new HashMap<>();

    requestButton.setOnClickListener(new View.OnClickListener(){
        public void onClick(View v) {
            BookRequest newRequest = new BookRequest(selectBook, selectBook.getOwner(), "Requester1", null, null);
            requests.put("first", newRequest);
            final CollectionReference collectionReference = db.collection("requests");
            collectionReference
                    .document(newRequest.getBook().getISBN())
                    .set(newRequest)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Data has been added successfully!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "Data addition failed" + e.toString());
                        }
                    });
            finish();
            }
        });
    }
}
