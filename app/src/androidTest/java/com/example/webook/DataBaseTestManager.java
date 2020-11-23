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
        Book TestBook11 = new Book("TestBook11", "1100000000000","TestBook11 Author", "accepted","TestOwner1",null,"This is TestBook11");
        Book TestBook12 = new Book("TestBook12", "1200000000000","TestBook12 Author", "accepted","TestOwner1",null,"This is TestBook12");

        requester1 = new ArrayList<>();
        requester1.add("Borrower1");
        requester1.add("Borrower2");
        requester1.add("Borrower3");

        BookRequest requestTest1 = new BookRequest(TestBook1,"TestOwner1", requester1, null, null);
        collectionReference = db.collection("requests");
        collectionReference
                .document("1000000000000")
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

        ArrayList<String> requester2;

        requester2 = new ArrayList<>();
        requester2.add("Borrower1");
        requester2.add("Borrower2");
        requester2.add("Borrower3");

        BookRequest requestTest2 = new BookRequest(TestBook2,"TestOwner1",requester2, null, null);
        collectionReference = db.collection("requests");
        collectionReference
                .document("2000000000000")
                .set(requestTest2)
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Add requestTest2","Add requestTest2 successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Add requestTest2", "Add requestTest2 failed");
                    }
                });

        ArrayList<String> requester3;

        requester3 = new ArrayList<>();
        requester3.add("Borrower1");
        requester3.add("Borrower2");
        requester3.add("Borrower3");

        BookRequest requestTest3 = new BookRequest(TestBook3,"TestOwner1",requester3, null, null);
        collectionReference = db.collection("requests");
        collectionReference
                .document("3000000000000")
                .set(requestTest3)
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Add requestTest3","Add requestTest3 successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Add requestTest3", "Add requestTest3 failed");
                    }
                });

        ArrayList<String> requester4;

        requester4 = new ArrayList<>();
        requester4.add("Borrower1");
        requester4.add("Borrower2");
        requester4.add("Borrower3");

        BookRequest requestTest4 = new BookRequest(TestBook4,"TestOwner1",requester4, null, null);
        collectionReference = db.collection("requests");
        collectionReference
                .document("4000000000000")
                .set(requestTest4)
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Add requestTest4","Add requestTest4 successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Add requestTest4", "Add requestTest4 failed");
                    }
                });

        ArrayList<String> requester5;

        requester5 = new ArrayList<>();
        requester5.add("Borrower1");
        requester5.add("Borrower2");
        requester5.add("Borrower3");

        BookRequest requestTest5 = new BookRequest(TestBook5,"TestOwner1",requester5, null, null);
        collectionReference = db.collection("requests");
        collectionReference
                .document("5000000000000")
                .set(requestTest5)
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Add requestTest5","Add requestTest5 successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Add requestTest5", "Add requestTest5 failed");
                    }
                });

        ArrayList<String> requester6;

        requester6 = new ArrayList<>();
        requester6.add("Borrower1");
        requester6.add("Borrower2");
        requester6.add("Borrower3");

        BookRequest requestTest6 = new BookRequest(TestBook6,"TestOwner1",requester6, null, null);
        collectionReference = db.collection("requests");
        collectionReference
                .document("6000000000000")
                .set(requestTest6)
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Add requestTest6","Add requestTest6 successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Add requestTest6", "Add requestTest6 failed");
                    }
                });

        ArrayList<String> requester7;
        ArrayList<Integer> Date7;
        ArrayList<Double> geoLocation7;

        requester7 = new ArrayList<>();
        requester7.add("Borrower1");

        TestBook7.setBorrower("Borrower1");

        Date7 = new ArrayList<>();
        Date7.add(2020);
        Date7.add(9);
        Date7.add(22);

        geoLocation7 = new ArrayList<>();
        geoLocation7.add(78.59963812631389);
        geoLocation7.add(123.08721370995045);

        BookRequest requestTest7 = new BookRequest(TestBook7,"TestOwner1",requester7, Date7, geoLocation7);
        collectionReference = db.collection("requests");
        collectionReference
                .document("7000000000000")
                .set(requestTest7)
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Add requestTest7","Add requestTest7 successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Add requestTest7", "Add requestTest7 failed");
                    }
                });

        ArrayList<String> requester8;
        ArrayList<Integer> Date8;
        ArrayList<Double> geoLocation8;

        requester8 = new ArrayList<>();
        requester8.add("Borrower1");

        TestBook8.setBorrower("Borrower1");

        Date8 = new ArrayList<>();
        Date8.add(2022);
        Date8.add(12);
        Date8.add(25);

        geoLocation8 = new ArrayList<>();
        geoLocation8.add(67.59963812631389);
        geoLocation8.add(77.08721370995045);

        BookRequest requestTest8 = new BookRequest(TestBook8,"TestOwner1",requester8, Date8, geoLocation8);
        collectionReference = db.collection("requests");
        collectionReference
                .document("8000000000000")
                .set(requestTest8)
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Add requestTest8","Add requestTest8 successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Add requestTest8", "Add requestTest8 failed");
                    }
                });

        ArrayList<String> requester9;
        ArrayList<Integer> Date9;
        ArrayList<Double> geoLocation9;

        requester9 = new ArrayList<>();
        requester9.add("Borrower1");

        TestBook9.setBorrower("Borrower1");

        Date9 = new ArrayList<>();
        Date9.add(2020);
        Date9.add(11);
        Date9.add(22);

        geoLocation9 = new ArrayList<>();
        geoLocation9.add(52.59963812631389);
        geoLocation9.add(98.08721370995045);

        BookRequest requestTest9 = new BookRequest(TestBook9,"TestOwner1",requester9, Date9, geoLocation9);
        collectionReference = db.collection("requests");
        collectionReference
                .document("9000000000000")
                .set(requestTest9)
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Add requestTest9","Add requestTest9 successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Add requestTest9", "Add requestTest9 failed");
                    }
                });

        ArrayList<String> requester10;
        ArrayList<Integer> Date10;
        ArrayList<Double> geoLocation10;

        requester10 = new ArrayList<>();
        requester10.add("Borrower1");

        TestBook10.setBorrower("Borrower1");

        Date10 = new ArrayList<>();
        Date10.add(2022);
        Date10.add(03);
        Date10.add(05);

        geoLocation10 = new ArrayList<>();
        geoLocation10.add(47.59963812631389);
        geoLocation10.add(133.08721370995045);

        BookRequest requestTest10 = new BookRequest(TestBook10,"TestOwner1",requester10, Date10, geoLocation10);
        collectionReference = db.collection("requests");
        collectionReference
                .document("0100000000000")
                .set(requestTest10)
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Add requestTest10","Add requestTest10 successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Add requestTest10", "Add requestTest10 failed");
                    }
                });

        ArrayList<String> requester11;
        ArrayList<Integer> Date11;
        ArrayList<Double> geoLocation11;

        requester11 = new ArrayList<>();
        requester11.add("Borrower1");

        TestBook11.setBorrower("Borrower1");

        Date11 = new ArrayList<>();
        Date11.add(2025);
        Date11.add(12);
        Date11.add(05);

        geoLocation11 = new ArrayList<>();
        geoLocation11.add(55.59963812631389);
        geoLocation11.add(55.08721370995045);

        BookRequest requestTest11 = new BookRequest(TestBook11,"TestOwner1",requester11, Date11, geoLocation11);
        collectionReference = db.collection("requests");
        collectionReference
                .document("1100000000000")
                .set(requestTest11)
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Add requestTest11","Add requestTest11 successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Add requestTest11", "Add requestTest11 failed");
                    }
                });

        ArrayList<String> requester12;
        ArrayList<Integer> Date12;
        ArrayList<Double> geoLocation12;

        requester12 = new ArrayList<>();
        requester12.add("Borrower1");

        TestBook12.setBorrower("Borrower1");

        Date12 = new ArrayList<>();
        Date12.add(2020);
        Date12.add(11);
        Date12.add(22);

        geoLocation12 = new ArrayList<>();
        geoLocation12.add(69.59963812631389);
        geoLocation12.add(35.08721370995045);

        BookRequest requestTest12 = new BookRequest(TestBook12,"TestOwner1",requester12, Date12, geoLocation12);
        collectionReference = db.collection("requests");
        collectionReference
                .document("1200000000000")
                .set(requestTest12)
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Add requestTest12","Add requestTest12 successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Add requestTest12", "Add requestTest12 failed");
                    }
                });

        ArrayList<String> bookList;
        bookList = new ArrayList<>();
        bookList.add("1000000000000");
        bookList.add("2000000000000");
        bookList.add("3000000000000");
        bookList.add("4000000000000");
        bookList.add("5000000000000");
        bookList.add("6000000000000");
        bookList.add("7000000000000");
        bookList.add("8000000000000");
        bookList.add("9000000000000");
        bookList.add("0100000000000");
        bookList.add("1100000000000");
        bookList.add("1200000000000");

        ArrayList<String> requestList;
        requestList = new ArrayList<>();
        requestList.add("1000000000000");
        requestList.add("1000000000000");
        requestList.add("1000000000000");
        requestList.add("2000000000000");
        requestList.add("2000000000000");
        requestList.add("2000000000000");
        requestList.add("3000000000000");
        requestList.add("3000000000000");
        requestList.add("3000000000000");
        requestList.add("4000000000000");
        requestList.add("4000000000000");
        requestList.add("4000000000000");
        requestList.add("5000000000000");
        requestList.add("5000000000000");
        requestList.add("5000000000000");
        requestList.add("6000000000000");
        requestList.add("6000000000000");
        requestList.add("6000000000000");
        requestList.add("7000000000000");
        requestList.add("7000000000000");
        requestList.add("7000000000000");
        requestList.add("8000000000000");
        requestList.add("8000000000000");
        requestList.add("8000000000000");
        requestList.add("9000000000000");
        requestList.add("9000000000000");
        requestList.add("9000000000000");
        requestList.add("0100000000000");
        requestList.add("0100000000000");
        requestList.add("0100000000000");
        requestList.add("1100000000000");
        requestList.add("1100000000000");
        requestList.add("1100000000000");
        requestList.add("1200000000000");
        requestList.add("1200000000000");
        requestList.add("1200000000000");

        Owner TestOwner1 = new Owner("TestOwner1","ThisIsATestEmail@gmail.com","6476854770","111","Hello, I'm TestOwner1",null);
        for(int i = 0; i < requestList.size(); i++){
            TestOwner1.addRequest(requestList.get(i));
        }
        for(int i = 0; i < bookList.size(); i++){
            TestOwner1.addBook(bookList.get(i));
        }

        collectionReference = db.collection("users");
        collectionReference
                .document("TestOwner1")
                .set(TestOwner1)
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Add TestOwner1","Add TestOwner1 successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Add TestOwner1", "Add Owner1 failed");
                    }
                });

        collectionReference = db.collection("books");
        collectionReference
                .document("1000000000000")
                .set(TestBook1);
        collectionReference
                .document("2000000000000")
                .set(TestBook2);
        collectionReference
                .document("3000000000000")
                .set(TestBook3);
        collectionReference
                .document("4000000000000")
                .set(TestBook4);
        collectionReference
                .document("5000000000000")
                .set(TestBook5);
        collectionReference
                .document("6000000000000")
                .set(TestBook6);
        collectionReference
                .document("7000000000000")
                .set(TestBook7);
        collectionReference
                .document("8000000000000")
                .set(TestBook8);
        collectionReference
                .document("9000000000000")
                .set(TestBook9);
        collectionReference
                .document("0100000000000")
                .set(TestBook10);
        collectionReference
                .document("1100000000000")
                .set(TestBook11);
        collectionReference
                .document("1200000000000")
                .set(TestBook12);

        Borrower borrower1 = new Borrower("borrower1","ThisIsATestEmailForBorrower1@gmail.com","6472409903","111","Hello, I'm TestBorrower1",null);
        Borrower borrower2 = new Borrower("borrower2","ThisIsATestEmailForBorrower2@gmail.com","6465546873","111","Hello, I'm TestBorrower2",null);
        Borrower borrower3 = new Borrower("borrower3","ThisIsATestEmailForBorrower3@gmail.com","7803664579","111","Hello, I'm TestBorrower3",null);
        collectionReference = db.collection("users");
        collectionReference
                .document("borrower1")
                .set(borrower1);
        collectionReference
                .document("borrower2")
                .set(borrower2);
        collectionReference
                .document("borrower3")
                .set(borrower3);
    }

    public void deleteTestData(){
        collectionReference = db.collection("requests");
        collectionReference
                .document("1000000000000")
                .delete();
        collectionReference
                .document("2000000000000")
                .delete();
        collectionReference
                .document("3000000000000")
                .delete();
        collectionReference
                .document("4000000000000")
                .delete();
        collectionReference
                .document("5000000000000")
                .delete();
        collectionReference
                .document("6000000000000")
                .delete();
        collectionReference
                .document("7000000000000")
                .delete();
        collectionReference
                .document("8000000000000")
                .delete();
        collectionReference
                .document("9000000000000")
                .delete();
        collectionReference
                .document("0100000000000")
                .delete();
        collectionReference
                .document("1100000000000")
                .delete();
        collectionReference
                .document("1200000000000")
                .delete();

        collectionReference = db.collection("books");
        collectionReference
                .document("1000000000000")
                .delete();
        collectionReference
                .document("2000000000000")
                .delete();
        collectionReference
                .document("3000000000000")
                .delete();
        collectionReference
                .document("4000000000000")
                .delete();
        collectionReference
                .document("5000000000000")
                .delete();
        collectionReference
                .document("6000000000000")
                .delete();
        collectionReference
                .document("7000000000000")
                .delete();
        collectionReference
                .document("8000000000000")
                .delete();
        collectionReference
                .document("9000000000000")
                .delete();
        collectionReference
                .document("0100000000000")
                .delete();
        collectionReference
                .document("1100000000000")
                .delete();
        collectionReference
                .document("1200000000000")
                .delete();

        collectionReference = db.collection("users");
        collectionReference
                .document("borrower1")
                .delete();
        collectionReference
                .document("borrower2")
                .delete();
        collectionReference
                .document("borrower3")
                .delete();

    }

    public void deleteUser() {
        collectionReference = db.collection("users");
        collectionReference
                .document("TestOwner1")
                .delete();
    }

    public void deleteSignedUpUsers() {
        collectionReference = db.collection("users");
        collectionReference
                .document("SignUpOwner")
                .delete();
        collectionReference
                .document("SignUpBorrower")
                .delete();
    }
}
