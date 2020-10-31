package com.example.webook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class SameBookRequestList extends AppCompatActivity {
    private ListView sameBookRequestList;
    private ArrayAdapter<BookRequest> bookAdapter;
    private static ArrayList<BookRequest> dataList;
    private static final String TAG = "Sample";
    private BookRequest newRequest;
    private Map<String, Object> dataMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_same_book_list);
        sameBookRequestList = findViewById(R.id.same_book_request_list);

        final Intent intent = getIntent();
        final Book selectBook = (Book) intent.getSerializableExtra("selectBook");

//        final BookRequest newRequest = new BookRequest(selectBook, selectBook.getOwner(), "requester1", null, null);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("requests").document("12345");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    dataMap = document.getData();
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

        dataList = new ArrayList<>();
        dataList.add(newRequest);
        bookAdapter = new BookRequestList(this, dataList);
        sameBookRequestList.setAdapter(bookAdapter);

        sameBookRequestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Object selectRequest = sameBookRequestList.getItemAtPosition(position);
                OwnerAcceptDeclineFragment ownerAcceptDeclineFragment = OwnerAcceptDeclineFragment.newInstance((BookRequest) selectRequest);
                ownerAcceptDeclineFragment.show(getSupportFragmentManager(), "Accept or Decline");
            }
        });
    }
}