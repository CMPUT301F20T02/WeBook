package com.example.webook;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DataBaseTestManager {
    FirebaseFirestore db;
    StorageReference storageReference;
    CollectionReference collectionReference;
    String TAG;


    public DataBaseTestManager() {
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public void createTestData() {
        ArrayList<String> requester1;
        ArrayList<Integer> Date1;
        ArrayList<Double> geoLocation1;

        Book TestBook1 = new Book("TestBook1", "1000000000000","TestBook1 Author", "available","TestOwner1",null,"This is TestBook1");
        Book TestBook2 = new Book("TestBook2", "2000000000000","TestBook2 Author", "available","TestOwner1",null,"This is TestBook2");
        Book TestBook3 = new Book("TestBook3", "3000000000000","TestBook3 Author", "available","TestOwner1",null,"This is TestBook3");
        Book TestBook4 = new Book("TestBook4", "4000000000000","TestBook4 Author", "requested","TestOwner1",null,"This is TestBook4");
        Book TestBook5 = new Book("TestBook5", "5000000000000","TestBook5 Author", "requested","TestOwner1",null,"This is TestBook5");
        Book TestBook6 = new Book("TestBook6", "6000000000000","TestBook6 Author", "requested","TestOwner1",null,"This is TestBook6");
        Book TestBook7 = new Book("TestBook7", "7000000000000","TestBook7 Author", "borrowed","TestOwner1",null,"This is TestBook7");
        Book TestBook8 = new Book("TestBook8", "8000000000000","TestBook8 Author", "borrowed","TestOwner1",null,"This is TestBook8");
        Book TestBook9 = new Book("TestBook9", "9000000000000","TestBook9 Author", "borrowed","TestOwner1",null,"This is TestBook9");
        Book TestBook10 = new Book("TestBook10", "0100000000000","TestBook10 Author", "accepted","TestOwner1",null,"This is TestBook10");
        Book TestBook11 = new Book("TestBook11", "0200000000000","TestBook11 Author", "accepted","TestOwner1",null,"This is TestBook11");
        Book TestBook12 = new Book("TestBook12", "0300000000000","TestBook12 Author", "accepted","TestOwner1",null,"This is TestBook12");

        requester1 = new ArrayList<>();
        requester1.add("Borrower1");
        requester1.add("Borrower2");
        requester1.add("Borrower3");

        Date1 = new ArrayList<>();
        Date1.add(2020);
        Date1.add(10);
        Date1.add(20);

        geoLocation1 = new ArrayList<>();
        geoLocation1.add(27.59963812631389);
        geoLocation1.add(120.08721370995045);

        BookRequest requestTest1 = new BookRequest(TestBook1,"TestOwner1",requester1, Date1, geoLocation1);
        collectionReference = db.collection("requests");
        collectionReference
                .document(requestTest1.getBook().getISBN())
                .set(requestTest1)
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Add requestTest1","Add requestTest1 successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Add requestTest1", "Add requestTest1 failed");
                    }
                });

    }
}
