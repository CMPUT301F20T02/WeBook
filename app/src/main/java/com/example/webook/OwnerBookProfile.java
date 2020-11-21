package com.example.webook;

/**
 * This activity is created by OwnerHomePage when the owner clicks on a book to view its details
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
    private TextView owned_by;
    private TextView borrowed_by;
    private TextView book_status_text;
    private String status;
    private Owner owner;
    private DataBaseManager dataBaseManager;
    private static final String TAG = "Sample";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_book_profile);

        Button requestButton = findViewById(R.id.owner_requests_list_button);
        Button ownerEditBookButton = findViewById(R.id.owner_edit_book_button);
        Button ownerDeleteBookButton = findViewById(R.id.owner_delete_book_button);

        dataBaseManager = new DataBaseManager();
        title_text = findViewById(R.id.book_profile_title);
        author_text = findViewById(R.id.book_profile_author);
        isbn_text = findViewById(R.id.book_profile_ISBN);

        owned_by = findViewById(R.id.book_profile_owner_text);
        borrowed_by = findViewById(R.id.book_profile_borrower_text);
        book_status_text = findViewById(R.id.book_profile_status_text);
        description_text = findViewById(R.id.book_profile_description);
        final Intent intent = getIntent();
        final Book selectBook = (Book) intent.getSerializableExtra("selectBook");
        owned_by.setText("Owned by " + selectBook.getOwner());
        if (selectBook.getBorrower() != null) {
            borrowed_by.setText("Borrowed by " + selectBook.getBorrower());
        } else {
            borrowed_by.setText("Borrowed by ");
        }
        book_status_text.setText("Book Status: " + selectBook.getStatus());
        owner = (Owner) intent.getSerializableExtra("user");

        title_text.setText(selectBook.getTitle());
        author_text.setText(selectBook.getAuthor());
        isbn_text.setText(selectBook.getISBN());
        description_text.setText(selectBook.getDescription());
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final DocumentReference docRef = db.collection("books").document(selectBook.getISBN());
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
                    overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                }
            }
        });

        final Intent intent3 = new Intent(this, OwnerBookProfileBookEdit.class);
        ownerEditBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!status.equals("available")){
                    Context context = getApplicationContext();
                    CharSequence text = "This book currently unavailable to change!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    intent3.putExtra("selectBook", selectBook);
                    intent3.putExtra("user",owner);
                    startActivity(intent3);
                    overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                }
            }
        });
        ownerDeleteBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OwnerBookProfile.this)
                        .setMessage("Are you sure you want to delete this book?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dataBaseManager.deleteBook(OwnerBookProfile.this, selectBook.getISBN());
                                finish();
                                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
                            }
                        })
                        .setNegativeButton("Cancel", null);
                alertDialog.show();
            }
        });
        dataBaseManager.BookProfileAddUserSnapShotListener(OwnerBookProfile.this, isbn_text.getText().toString());
    }



    public void setTitle_text(String title_text) {
        this.title_text.setText(title_text);
    }

    public void setAuthor_text(String author_text) {
        this.author_text.setText(author_text);
    }

    public void setIsbn_text(String isbn_text) {
        this.isbn_text.setText(isbn_text);
    }

    public void setDescription_text(String description_text) {
        this.description_text.setText(description_text);
    }



}