package com.example.webook;

/**
 * This activity is created by OwnerHomePage when the owner clicks on a book to view its details
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class OwnerBookProfile extends AppCompatActivity {
    private TextView title_text;
    private TextView author_text;
    private TextView isbn_text;
    private TextView description_text;
    private Button requestButton;
    private String status;
    private static final String TAG = "Sample";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_book_profile);

        title_text = findViewById(R.id.book_profile_title);
        author_text = findViewById(R.id.book_profile_author);
        isbn_text = findViewById(R.id.book_profile_ISBN);
        requestButton = findViewById(R.id.owner_requests_list_button);
        description_text = findViewById(R.id.book_profile_description);
        final Intent intent = getIntent();
        final Book selectBook = (Book) intent.getSerializableExtra("selectBook");

        title_text.setText(selectBook.getTitle());
        author_text.setText(selectBook.getAuthor());
        isbn_text.setText(selectBook.getISBN());
        description_text.setText(selectBook.getDescription());
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

// move to database manager ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        DocumentReference docRef = db.collection("books").document(selectBook.getISBN());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    status = document.getString("status");
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        final Intent intent2 = new Intent(this, SameBookRequestList.class);
        requestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (status.equals("accepted") || status.equals("borrowed")){
                    System.out.println(status);
                    Context context = getApplicationContext();
                    CharSequence text = "This book's request is already accepted/borrowed, no waiting requests";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    intent2.putExtra("selectBook", selectBook);
                    startActivity(intent2);
                }
            }
        });

    }
}