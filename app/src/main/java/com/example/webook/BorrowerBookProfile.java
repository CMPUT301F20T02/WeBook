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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BorrowerBookProfile extends AppCompatActivity {
    private TextView title_text;
    private TextView author_text;
    private TextView isbn_text;
    private Button requestButton;
    private static final String TAG = "Sample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_borrower_book_profile);

    title_text = findViewById(R.id.title_text);
    author_text = findViewById(R.id.author_text);
    isbn_text = findViewById(R.id.isbn_text);
    requestButton = findViewById(R.id.borrower_request_button);

    Intent intent = getIntent();
    final Request newRequest = (Request) intent.getSerializableExtra("request");

    title_text.setText(newRequest.getBook().getTitle());
    author_text.setText(newRequest.getBook().getAuthor());
    isbn_text.setText(newRequest.getBook().getISBN());

    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    final Map<String, Object> requests = new HashMap<>();
    requests.put("first", newRequest);

    requestButton.setOnClickListener(new View.OnClickListener(){
        public void onClick(View v) {
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
            }
        });
    }
}
