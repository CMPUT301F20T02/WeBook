package com.example.webook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * This is the class keeps functions handle database operation
 * Or handling information get from database
 */
public class DataBaseManager {
    FirebaseFirestore db;
    StorageReference storageReference;
    CollectionReference collectionReference;
    String TAG;


    public DataBaseManager(){
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }


    /**
     * This check for username and password consistency in database
     * If consist and login success, jump to homepage for users with different usertype
     * @param username
     * @param pwd
     * These are username and password you want to check
     */
    public void authenticate(final String username, final String pwd, final MainActivity mainActivity){
        final DocumentReference userRef = db.collection("users").document(username);
        userRef.get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    int duration = Toast.LENGTH_SHORT;
                    if (document.exists() && document.getString("pwd").equals(pwd)) {
                        Intent intent;
                        if (document.getString("userType").equals("owner")){
                            intent = new Intent(mainActivity, OwnerHomepage.class);
                            Owner owner = new Owner(username, document.getString("email"),
                                    document.getString("phoneNumber"), document.getString("pwd"), document.getString("description"), document.getString("user_image"));
                            owner.setBookList((ArrayList<String>) document.get("bookList"));
                            intent.putExtra("user", owner);
                            mainActivity.startActivity(intent);
                        }else if (document.getString("userType").equals("borrower")) {
                            intent = new Intent(mainActivity, BorrowerHomepage.class);
                            Borrower borrower = new Borrower(username, document.getString("email"),
                                    document.getString("phoneNumber"), document.getString("pwd"), document.getString("description"), document.getString("user_image"));
                            intent.putExtra("user", borrower);
                            mainActivity.startActivity(intent);
                        }
                    } else {
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        } );
    }
	

    /**
     * This search books by keyword in multiple fields in database
     * @param message
     * @param borrowerSearchBookPage
     * These are the keyword you want to search and the page you want to show the data
     */
    public void BorrowerSearchBook(final String message, final BorrowerSearchBookPage borrowerSearchBookPage){
        collectionReference= db.collection("books");
        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            borrowerSearchBookPage.dataList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId());
                                String status = (String) document.getData().get("status");
                                if(!status.equals("borrowed") && !status.equals("accepted")){
                                    String title = (String) document.getData().get("title");
                                    String author = (String) document.getData().get("author");
                                    String isbn = (String) document.getData().get("isbn");
                                    String description = (String) document.getData().get("description");
                                    String owner = (String) document.getData().get("owner");

                                    if(title.contains(message)) {
                                        borrowerSearchBookPage.dataList.add(new Book(title, isbn, author, status, owner, null, description));
                                    }
                                    else if (author.contains(message)) {
                                        borrowerSearchBookPage.dataList.add(new Book(title, isbn, author, status, owner, null, description));
                                    }
                                    else if(isbn.contains(message)) {
                                        borrowerSearchBookPage.dataList.add(new Book(title, isbn, author, status, owner, null, description));
                                    }else if(description!= null){
                                        if(description.contains(message)) {
                                            borrowerSearchBookPage.dataList.add(new Book(title, isbn, author, status, owner, null, description));
                                        }
                                    }
                                }
                            }
                            borrowerSearchBookPage.bookAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }


    /**
     * This search user by username in database
     * @param message
     * @param borrowerSearchUserPage
     * These are the username you want to search and the page you want to show the data
     */
    public void BorrowerSearchUser(final String message, final BorrowerSearchUserPage borrowerSearchUserPage){
        collectionReference= db.collection("users");
        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            borrowerSearchUserPage.dataList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId());
                                String Username = document.getId();
                                String email = (String) document.getData().get("email");
                                String description =  (String) document.getData().get("description");
                                String pwd =  (String) document.getData().get("pwd");
                                String phoneNumber =  (String) document.getData().get("phoneNumber");
                                String userType = (String) document.getData().get("userType");
                                if(Username.contains(message)){
                                    if(userType.equals("borrower")) {
                                        borrowerSearchUserPage.dataList.add(new Borrower(Username,email, phoneNumber, pwd, description,null));
                                    }else{
                                        borrowerSearchUserPage.dataList.add(new Owner(Username,email, phoneNumber, pwd, description,null));
                                    }
                                }
                            }
                            borrowerSearchUserPage.userAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    /**
     * This add a book document in database
     * @param bitmap
     * @param book
     * @param owner
     * These are the picture, book item and owner information that you want to add into the document
     */
    public void addBook(Bitmap bitmap, final Book book, final Owner owner){
        if (bitmap != null) {
            final StorageReference imageReference = storageReference.child( "images/" + System.currentTimeMillis());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            imageReference.putBytes(byteArray)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    book.setImage(url);
                                    uploadBook(book,owner);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("Upload image", "Error uploading.", e);
                        }
                    });
        }
    }


    /**
     * This change a book document in database
     * @param book
     * @param owner
     * These are book and its owner that the document you want to change have
     */
    private void uploadBook(final Book book, final Owner owner){
        db.collection("books").document(book.getISBN())
                .set(book)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        updateOwnerBookList(owner, book);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Add book", "Error adding book", e);
                    }
                });
    }


    /**
     * This change the bookList filed in a user document in database
     * @param owner
     * @param book
     * These are user and the book item you want to add into booklist
     */
    private void updateOwnerBookList(Owner owner, final Book book){
        db.collection("users").document(owner.getUsername())
                .update("bookList", FieldValue.arrayUnion(book.getISBN()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("updateUserBookList", "Error updating", e);
                    }
                });
    }


    /**
     * This send a bookRequest into database
     * @param newRequest
     * @param borrower
     * These are request infomation and the user who send this request
     */
    public void sendBookRequest(final Request newRequest, Borrower borrower){
        collectionReference = db.collection("requests");
        TAG = "";
        collectionReference
                .document(newRequest.getBook().getISBN())
                .update("requester", FieldValue.arrayUnion(borrower.getUsername()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Data has been added successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        collectionReference.document(newRequest.getBook().getISBN()).set(newRequest);
                        Log.d(TAG, "Data addition failed" + e.toString());
                        db.collection("requests").document(newRequest.getBook().getISBN())
                                .set(newRequest);
                        db.collection("books").document(newRequest.getBook().getISBN())
                                .update(
                                        "status", "requested"
                                );
                    }
                });
    }


    /**
     * This upload a user document in database
     * @param username
     * @param phoneText
     * @param emailText
     * @param descriptionText
     * These are new field values you want your document to have
     */
    public void updateInfo(String username, String phoneText, String emailText, String descriptionText){
        DocumentReference userRef = db.collection("users").document(username);
        userRef.update("phoneNumber", phoneText);
        userRef.update("email", emailText);
        userRef.update("description", descriptionText);
    }


    /**
     * This search for requests in the database which request for a book and show the list
     * @param isbn
     * @param sameBookRequest
     * These are the isbn code the requested book had and the page you want to show the list
     */
    public void getSameBookRequest(String isbn, final SameBookRequestList sameBookRequest){
        DocumentReference docRef = db.collection("requests").document(isbn);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                BookRequest request = documentSnapshot.toObject(BookRequest.class);
                sameBookRequest.dataListClear();
                for(int i = 0; i < request.getRequester().size(); i++){
                    sameBookRequest.dataListAdd(request);
                }
                sameBookRequest.bookAdapterChanged();
            }
        });
    }


    /**
     * This show the updated request list for a book in the database
     * @param isbn
     * @param sameBookRequest
     * These are the isbn code the requested book had and the page you want to show the list
     */
    public  void declinePressed(String isbn, final SameBookRequestList sameBookRequest){
        DocumentReference docRef = db.collection("requests").document(isbn);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                BookRequest request = documentSnapshot.toObject(BookRequest.class);
                sameBookRequest.dataListClear();
                for(int i = 0; i < request.getRequester().size(); i++){
                    sameBookRequest.dataListAdd(request);
                }
                sameBookRequest.bookAdapterChanged();
            }
        });
    }


    /**
     * This check if document with same name exist in database
     * If not, it add a document into user with usertype owner in database
     * @param usernameText
     * @param emailText
     * @param phoneText
     * @param pwdText
     * @param descriptionText
     * @param signUpActivity
     * These are the user information your document will have
     */
    public void ownerSignUp(final String usernameText, final String emailText,
                            final String phoneText, final String pwdText, final String descriptionText, final SignUpActivity signUpActivity){
        final DocumentReference userRef = db.collection("users").document(usernameText);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        Toast toast = Toast.makeText(signUpActivity, "Username already in use!", Toast.LENGTH_SHORT);
                        toast.show();
                    }else{
                        userRef.set(new Owner(usernameText, emailText, phoneText, pwdText, descriptionText, null));
                        Toast toast = Toast.makeText(signUpActivity, "Sign up successful!", Toast.LENGTH_SHORT);
                        toast.show();
                        signUpActivity.finish();
                    }
                }
            }
        });
    }
	
	
    /**
     * This check if document with same name exist in database
     * If not, it add a document into user with usertype borrower in database
     * @param usernameText
     * @param emailText
     * @param phoneText
     * @param pwdText
     * @param descriptionText
     * @param signUpActivity
     * These are the user information your document will have
     */
    public void borrowerSignUp(final String usernameText, final String emailText,
                            final String phoneText, final String pwdText, final String descriptionText, final SignUpActivity signUpActivity){
        final DocumentReference userRef = db.collection("users").document(usernameText);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        Toast toast = Toast.makeText(signUpActivity, "Username already in use!", Toast.LENGTH_SHORT);
                        toast.show();
                    }else{
                        userRef.set(new Borrower(usernameText, emailText, phoneText, pwdText, descriptionText, null));
                        Toast toast = Toast.makeText(signUpActivity, "Sign up successful!", Toast.LENGTH_SHORT);
                        toast.show();
                        signUpActivity.finish();
                    }
                }
            }
        });
    }

    public void OwnerRequestPageRequestSnapShotListener(final OwnerRequestPageActivity ownerRequestPageActivity, final String username){
        final CollectionReference requestRef = db.collection("requests");
        requestRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                db.collection("users").document(username).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot document) {
                        final ArrayList<String> bookisbn = (ArrayList<String>) document.get("bookList");
                        downloadRequests(ownerRequestPageActivity, bookisbn);

                    }
                });
            }
        });

    }

    private void downloadRequests(final OwnerRequestPageActivity ownerRequestPageActivity, final ArrayList<String> bookisbn){
        CollectionReference requestRef = db.collection("requests");
        for (int i = 0; i < bookisbn.size(); i++) {
            final DocumentReference bookRef1 = requestRef.document(bookisbn.get(i));
            bookRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if(document.exists()){
                            BookRequest bookRequest = document.toObject(BookRequest.class);
                            String isbn = bookRequest.getBook().getISBN();
                            if(!ownerRequestPageActivity.getOwnerRequestList().contains(isbn)){
                                ownerRequestPageActivity.ownerAddRequest(isbn);
                                ownerRequestPageActivity.ownerAddRequestArrayList(bookRequest);
                                ownerRequestPageActivity.getPending();
                                ownerRequestPageActivity.getAccepted();
                                ownerRequestPageActivity.getBorrowed();
                            }
                        }
                    }
                }
            });
        }
    }

    public void OwnerHomePageAddBookSnapShotListener(final OwnerHomepage ownerHomepage, final String username){

        final CollectionReference bookRef = db.collection("books");
        bookRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                db.collection("users").document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()){
                            ownerHomepage.setBookArrayList(new ArrayList<Book>());
                            ownerHomepage.ownerSetBookList(new ArrayList<String>());
                            DocumentSnapshot document = task.getResult();
                            final ArrayList<String> bookisbn = (ArrayList<String>) document.get("bookList");
                            downloadBooks(ownerHomepage, bookisbn);

                        }

                    }
                });
            }
        });
    }

    private void downloadBooks(final OwnerHomepage ownerHomepage , final ArrayList<String> bookisbn) {
        CollectionReference bookRef = db.collection("books");
        for (int i = 0; i < bookisbn.size(); i++) {
            DocumentReference bookRef1 = bookRef.document(bookisbn.get(i));
            bookRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Book book = document.toObject(Book.class);
                            if (!ownerHomepage.getOwnerBookList().contains(book.getISBN())){
                                ownerHomepage.ownerAddBook(book.getISBN());
                                ownerHomepage.addBookArrayList(book);
                                ownerHomepage.setBookList();
                                ownerHomepage.dataChanged();
                                ownerHomepage.getAvailable();
                                ownerHomepage.getAccepted();
                                ownerHomepage.getRequested();
                                ownerHomepage.getBorrowed();
                            }

                        }
                    }
                }
            });
        }
    }

    public void OwnerHomePageAddUserSnapShotListener(final OwnerHomepage ownerHomepage, final String username){
        DocumentReference userRef = db.collection("users").document(username);
        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                ownerHomepage.setBookArrayList(new ArrayList<Book>());
                ownerHomepage.ownerSetBookList(new ArrayList<String>());
                ArrayList<String> bookisbn = (ArrayList<String>) value.get("bookList");
                downloadBooks(ownerHomepage, bookisbn);
            }
        });
    }

    public void BorrowerProfileAddUserSnapShotListener(final BorrowerProfileActivity borrowerProfileActivity, final String username){
        DocumentReference userRef = db.collection("users").document(username);
        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                updateBorrowerInfo(value, borrowerProfileActivity);
            }
        });

    }

    private void updateBorrowerInfo(DocumentSnapshot document, final BorrowerProfileActivity borrowerProfileActivity) {
        Borrower borrower = document.toObject(Borrower.class);
        borrowerProfileActivity.setUsername(borrower.getUsername());
        borrowerProfileActivity.setUserType(borrower.getUserType());
        borrowerProfileActivity.setPhone(borrower.getPhoneNumber());
        borrowerProfileActivity.setEmail(borrower.getEmail());
        borrowerProfileActivity.setDescription(borrower.getDescription());
    }

    public void OwnerProfileAddUserSnapShotListener(final OwnerProfileActivity ownerProfileActivity, final String username){
        DocumentReference userRef = db.collection("users").document(username);
        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                updateOwnerInfo(value, ownerProfileActivity);
            }
        });

    }

    private void updateOwnerInfo(DocumentSnapshot document, final OwnerProfileActivity ownerProfileActivity) {
        Borrower borrower = document.toObject(Borrower.class);
        ownerProfileActivity.setUsername(borrower.getUsername());
        ownerProfileActivity.setUserType(borrower.getUserType());
        ownerProfileActivity.setPhone(borrower.getPhoneNumber());
        ownerProfileActivity.setEmail(borrower.getEmail());
        ownerProfileActivity.setDescription(borrower.getDescription());
    }

}
