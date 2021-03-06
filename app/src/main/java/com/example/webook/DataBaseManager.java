package com.example.webook;

import android.app.Activity;
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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
                        String textToShow = "Login success, welcome " + username;
                        Toast toast = Toast.makeText(mainActivity, textToShow, Toast.LENGTH_LONG);
                        toast.show();
                        if (document.getString("userType").equals("owner")){
                            intent = new Intent(mainActivity, OwnerHomepage.class);
                            Owner owner = new Owner(username, document.getString("email"),
                                    document.getString("phoneNumber"), document.getString("pwd"), document.getString("description"), document.getString("user_image"));
                            owner.setBookList((ArrayList<String>) document.get("bookList"));
                            intent.putExtra("user", owner);
                            mainActivity.startActivity(intent);
                            ((Activity)mainActivity).overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
                        }else if (document.getString("userType").equals("borrower")) {
                            intent = new Intent(mainActivity, BorrowerHomepage.class);
                            Borrower borrower = new Borrower(username, document.getString("email"),
                                    document.getString("phoneNumber"), document.getString("pwd"), document.getString("description"), document.getString("user_image"));
                            intent.putExtra("user", borrower);
                            mainActivity.startActivity(intent);
                            ((Activity)mainActivity).overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
                        }
                    } else {
                        String textToShow = "Wrong username or password, please try again.";
                        Toast toast = Toast.makeText(mainActivity, textToShow, Toast.LENGTH_LONG);
                        toast.show();
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
                                    Book book = document.toObject(Book.class);
                                    String title = book.getTitle();
                                    String author = book.getAuthor();
                                    String isbn = book.getISBN();
                                    String description = book.getDescription();

                                    if(title.contains(message)) {
                                        borrowerSearchBookPage.dataList.add(book);
                                    }
                                    else if (author.contains(message)) {
                                        borrowerSearchBookPage.dataList.add(book);
                                    }
                                    else if(isbn.contains(message)) {
                                        borrowerSearchBookPage.dataList.add(book);
                                    }else if(description!= null){
                                        if(description.contains(message)) {
                                            borrowerSearchBookPage.dataList.add(book);
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
     * Search for users in the database
     * @param message the keyword
     * @param ownerSearchUserPage the activity reference
     */

    public void OwnerSearchUser(final String message, final OwnerSearchUserPage ownerSearchUserPage){
        collectionReference= db.collection("users");
        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ownerSearchUserPage.dataList.clear();
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
                                        ownerSearchUserPage.dataList.add(new Borrower(Username,email, phoneNumber, pwd, description,null));
                                    }else{
                                        ownerSearchUserPage.dataList.add(new Owner(Username,email, phoneNumber, pwd, description,null));
                                    }
                                }
                            }
                            ownerSearchUserPage.userAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    /**
     * This add a book document in database
     * @param bitmap the book image
     * @param book the book
     * @param owner the owner of the book
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
     * @param book the book to be changed
     * @param owner the owner of the book
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
    public void sendBookRequest(final Request newRequest, final Borrower borrower){
        collectionReference = db.collection("requests");
        TAG = "";
        collectionReference
                .document(newRequest.getBook().getISBN())
                .update(
                        "requester", FieldValue.arrayUnion(borrower.getUsername()),
                        "book.status", "requested"
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Data has been added successfully!");
                        db.collection("users").document(newRequest.getBook().getOwner()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                System.out.println("in onComplete");
                                if (task.isSuccessful()) {
                                    System.out.println("in first if");
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    if (documentSnapshot.exists()){
                                        System.out.println("in second if");
                                        ArrayList<String> requestList = (ArrayList<String>) documentSnapshot.get("requestList");
                                        requestList.add(newRequest.getBook().getISBN());
                                        db.collection("users").document(newRequest.getBook().getOwner())
                                                .update(
                                                        "requestList", requestList
                                                )
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        System.out.println("haha");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        System.out.println("fuck");
                                                    }
                                                });
                                    }
                                }
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        collectionReference.document(newRequest.getBook().getISBN()).set(newRequest);

                        db.collection("requests").document(newRequest.getBook().getISBN()).update(
                                "book.status", "requested"
                        );
                        Log.d(TAG, "Data addition failed" + e.toString());

                        db.collection("books").document(newRequest.getBook().getISBN())
                                .update(
                                        "status", "requested"
                                );
                        db.collection("users").document(newRequest.getBook().getOwner()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                System.out.println("in onComplete");
                                if (task.isSuccessful()) {
                                    System.out.println("in first if");
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    if (documentSnapshot.exists()){
                                        System.out.println("in second if");
                                        ArrayList<String> requestList = (ArrayList<String>) documentSnapshot.get("requestList");
                                        requestList.add(newRequest.getBook().getISBN());
                                        db.collection("users").document(newRequest.getBook().getOwner())
                                                .update(
                                                        "requestList", requestList
                                                )
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                    }
                                                });
                                    }
                                }
                            }
                        });
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
                if(documentSnapshot.exists()) {
                    BookRequest request = documentSnapshot.toObject(BookRequest.class);
                    sameBookRequest.dataListClear();
                    for (int i = 0; i < request.getRequester().size(); i++) {
                        sameBookRequest.dataListAdd(request);
                    }
                    sameBookRequest.bookAdapterChanged();
                }
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
        final DocumentReference docRef = db.collection("requests").document(isbn);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    BookRequest request = documentSnapshot.toObject(BookRequest.class);
                    sameBookRequest.dataListClear();
                    for (int i = 0; i < request.getRequester().size(); i++) {
                        sameBookRequest.dataListAdd(request);
                    }
                    sameBookRequest.bookAdapterChanged();
                }
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
                        ((Activity)signUpActivity).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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
                        ((Activity)signUpActivity).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                    }
                }
            }
        });
    }

    /**
     * This method set a SnapShotListener on, if there is any change
     * of the owner's requests in the database, the array list will refresh
     * @param ownerRequestPageActivity
     * @param username
     * These are related activity and username
     */
    public void OwnerRequestPageRequestSnapShotListener(final OwnerRequestPageActivity ownerRequestPageActivity, final String username){
        CollectionReference requestRef = db.collection("requests");
        requestRef
                .whereEqualTo("requestee", username)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        assert value != null;
                        List<DocumentSnapshot> documents = value.getDocuments();
                        ArrayList<BookRequest> bookRequests = new ArrayList<>();
                        for (int i = 0; i < documents.size(); i++){
                            BookRequest temp = documents.get(i).toObject(BookRequest.class);
                            bookRequests.add(temp);
                        }
                        ownerRequestPageActivity.setArrayList(bookRequests);
                    }
                });
    }

    /**
     *This method set a SnapShotListener, if there is any change
     * of the borrower's requests in the database, the array list will refresh
     * @param borrowerRequestPageActivity
     * @param username
     * These are related activity and username
     */
    public void BorrowerRequestPageRequestSnapShotListener(final BorrowerRequestPageActivity borrowerRequestPageActivity, final String username){
        CollectionReference requestRef = db.collection("requests");
        requestRef
                .whereArrayContains("requester", username)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        assert value != null;
                        List<DocumentSnapshot> documents = value.getDocuments();
                        ArrayList<BookRequest> bookRequests = new ArrayList<>();
                        for (int i = 0; i < documents.size(); i++){
                            BookRequest temp = documents.get(i).toObject(BookRequest.class);
                            bookRequests.add(temp);
                        }
                        borrowerRequestPageActivity.setArrayList(bookRequests);
                    }
                });
    }

    /**
     * This method set a SnapShotListener, when owner add new books, the
     * book list of owner homepage will refresh
     * @param ownerHomepage
     * @param username
     * These are related activity and username
     */
    public void OwnerHomePageAddBookSnapShotListener(final OwnerHomepage ownerHomepage, final String username){
        final CollectionReference bookRef = db.collection("books");
        bookRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                db.collection("users").document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()) {
                                ownerHomepage.setBookArrayList(new ArrayList<Book>());
                                ownerHomepage.ownerSetBookList(new ArrayList<String>());
                                final ArrayList<String> bookisbn = (ArrayList<String>) document.get("bookList");
                                downloadBooks(ownerHomepage, bookisbn);
                            }
                        }

                    }
                });
            }
        });
    }

    /**
     * This method is used to download books from database
     * @param ownerHomepage
     * @param bookisbn
     * These are related activity and an array list of ISBN
     */
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

    /**
     * This method set a SnapShotListener on an owner, if there is any change,
     * the owner home page will refresh
     * @param ownerHomepage
     * @param username
     * These are related activity and username
     */
    public void OwnerHomePageAddUserSnapShotListener(final OwnerHomepage ownerHomepage, final String username){
        DocumentReference userRef = db.collection("users").document(username);
        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()) {
                    ownerHomepage.setBookArrayList(new ArrayList<Book>());
                    ownerHomepage.ownerSetBookList(new ArrayList<String>());
                    ArrayList<String> bookisbn = (ArrayList<String>) value.get("bookList");
                    downloadBooks(ownerHomepage, bookisbn);
                }
            }
        });
    }

    /**
     * This method sets a SnapShotListener, when the info of borrower changes,
     * it will call updateBorrowerInfo method
     * @param borrowerProfileActivity
     * @param username
     * These are related activity and username
     */
    public void BorrowerProfileAddUserSnapShotListener(final BorrowerProfileActivity borrowerProfileActivity, final String username){
        DocumentReference userRef = db.collection("users").document(username);
        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()) {
                    updateBorrowerInfo(value, borrowerProfileActivity);
                }
            }
        });

    }

    /**
     * This method can reset borrower's profile page when the
     * info of the borrower changes
     * @param document
     * @param borrowerProfileActivity
     * These are related activity and DocumentSnapshot
     */
    private void updateBorrowerInfo(DocumentSnapshot document, final BorrowerProfileActivity borrowerProfileActivity) {
        Borrower borrower = document.toObject(Borrower.class);
        if(document.exists()) {
            borrowerProfileActivity.setUsername(borrower.getUsername());
            borrowerProfileActivity.setUserType(borrower.getUserType());
            borrowerProfileActivity.setPhone(borrower.getPhoneNumber());
            borrowerProfileActivity.setEmail(borrower.getEmail());
            borrowerProfileActivity.setDescription(borrower.getDescription());
        }
    }

    /**
     * This method sets a SnapshotListener, when the info of owner changes,
     * it will call updateOwnerInfo method
     * @param ownerProfileActivity
     * @param username
     * These are related activity and username
     */
    public void OwnerProfileAddUserSnapShotListener(final OwnerProfileActivity ownerProfileActivity, final String username){
        DocumentReference userRef = db.collection("users").document(username);
        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()) {
                    updateOwnerInfo(value, ownerProfileActivity);
                }
            }
        });

    }

    /**
     * This method can reset owner's profile page when the
     * info of the owner changes
     * @param document
     * @param ownerProfileActivity
     * These are related activity and DocumentSnapshot
     */
    private void updateOwnerInfo(DocumentSnapshot document, final OwnerProfileActivity ownerProfileActivity) {
        Borrower borrower = document.toObject(Borrower.class);
        ownerProfileActivity.setUsername(borrower.getUsername());
        ownerProfileActivity.setUserType(borrower.getUserType());
        ownerProfileActivity.setPhone(borrower.getPhoneNumber());
        ownerProfileActivity.setEmail(borrower.getEmail());
        ownerProfileActivity.setDescription(borrower.getDescription());
    }

    /**
     * This method can remove a book from database
     * @param book
     * This is the book that need to be deleted
     */
    public void removeBook(Book book){
        DocumentReference docRef = db.collection("books").document(book.getISBN());
        docRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    /**
     * This method sets a SnapshotListener, when user update the info of
     * a book, it will call updateBookInfo method
     * @param ownerBookProfile
     * @param isbn
     * These are related activity and the ISBN of the book
     */
    public void BookProfileAddUserSnapShotListener(final OwnerBookProfile ownerBookProfile, final String isbn){
        DocumentReference bookRef = db.collection("books").document(isbn);
        bookRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()){
                    updateBookInfo(value, ownerBookProfile);
                }
            }
        });
    }

    /**
     * This method can reset the info of a book in OwnerBookProfile
     * @param documentSnapshot
     * @param ownerBookProfile
     * These are related activity and DocumentSnapshot
     */
    private void updateBookInfo (DocumentSnapshot documentSnapshot, final OwnerBookProfile ownerBookProfile) {
        Book book = documentSnapshot.toObject(Book.class);
        ownerBookProfile.setTitle_text(book.getTitle());
        ownerBookProfile.setIsbn_text(book.getISBN());
        ownerBookProfile.setDescription_text(book.getDescription());
        ownerBookProfile.setAuthor_text(book.getAuthor());
        System.out.println("image is: "+ book.getImage());
        ownerBookProfile.setImage(book.getImage());
        ownerBookProfile.setSelectBook(book);
    }

    /**
     * This method can update the information of a book in the database
     * @param isbn
     * @param title
     * @param author
     * @param des
     * @param bitmap
     * @param imageChanged
     * These are the new information of the book
     */
    public void updateBook (final String isbn, String title, String author, String des, Bitmap bitmap, int imageChanged) {
        DocumentReference bookRef = db.collection("books").document(isbn);
        bookRef.update("description",des);
        bookRef.update("title",title);
        bookRef.update("author", author);
        if (imageChanged == 2) {
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
                                    db.collection("books").document(isbn).update("image", url);
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
        }else if (imageChanged == 1){
            db.collection("books").document(isbn).update("image", null);
        }
    }

    /**
     * This method sets a SnapshotListener on the BorrowerHomepage
     * @param borrowerHomepage
     * @param username
     * These are related activity and username
     */
    public void BorrowerHomepageBookAddSnapShotListener(final BorrowerHomepage borrowerHomepage, final String username){


        db.collection("requests")
                .whereArrayContains("requester", username)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if( value != null) {
                            List<DocumentSnapshot> documents = value.getDocuments();
                            ArrayList<Book> pending = new ArrayList<>();
                            ArrayList<Book> accepted = new ArrayList<>();
                            ArrayList<Book> borrowed = new ArrayList<>();
                            for (int i = 0; i < documents.size(); i++) {
                                BookRequest temp = documents.get(i).toObject(BookRequest.class);
                                assert temp != null;
                                Book book = temp.getBook();
                                switch (book.getStatus()) {
                                    case "requested":
                                        pending.add((book));
                                        break;
                                    case "accepted":
                                        accepted.add(book);
                                        break;
                                    case "borrowed":
                                        borrowed.add(book);
                                        break;
                                }
                            }
                            borrowerHomepage.setBorrowedBooks(borrowed);
                            borrowerHomepage.setRequestedBooks(pending);
                            borrowerHomepage.setAcceptedBooks(accepted);
                            borrowerHomepage.updateAllBooks();
                        }
                    }
                });
    }

    /**
     * This method is used to delete a book, after the action
     * it will show a toast to inform owner the deletion is successful
     * or failed
     * @param ownerBookProfile
     * @param isbn
     * These are related activity and the isbn of the book
     */
    public void deleteBook(final OwnerBookProfile ownerBookProfile, String isbn){
        db.collection("books").document(isbn)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast toast = Toast.makeText(ownerBookProfile, "Deleted successfully", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast toast = Toast.makeText(ownerBookProfile, "Delete failed", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
    }

    /**
     * This method sets a SnapshotListener on BorrowerHomepage. When there is
     * any change relate to the borrower, it will refresh the list and
     * also show a toast to inform the borrower what had been changed
     * @param borrowerHomepage
     * @param username
     * These are related activity and the username
     */
    public void BorrowerHomepageRequestListener(final BorrowerHomepage borrowerHomepage, final String username){

        db.collection("requests")
                .whereArrayContains("requester", username)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        assert value != null;
                        for (final DocumentChange dc : value.getDocumentChanges()) {
                            switch (dc.getType()){
                                case ADDED:

                                    ListenerRegistration listenerRegistration = dc.getDocument().getReference()
                                            .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                                    if(value.get("time") != null){
                                                        BookRequest requestHere = dc.getDocument().toObject(BookRequest.class);
                                                        System.out.println("1");
                                                        if(requestHere.getBook().getStatus().equals("accepted") && requestHere.getNotify() == 1) {
                                                            System.out.println("added" + dc.getDocument().get("book.title"));
                                                            dc.getDocument().getReference().update("notify", 2);
                                                            Book bookHere = requestHere.getBook();
                                                            String sentence = "Delivery time and location set for \n Book: " + bookHere.getTitle();
                                                            Toast toast = Toast.makeText(borrowerHomepage,
                                                                    sentence, Toast.LENGTH_LONG);
                                                            toast.show();
                                                        }
                                                    }

                                                    BookRequest requestHere = dc.getDocument().toObject(BookRequest.class);
                                                    if(requestHere.getBook().getStatus().equals("accepted") && requestHere.getNotify() == 0){
                                                        System.out.println("2");
                                                        if(value.get("time") == null){
                                                            dc.getDocument().getReference().update("notify", 1);
                                                            Book bookHere = requestHere.getBook();
                                                            String sentence = "Owner have accepted you request on \n" + bookHere.getTitle();
                                                            Toast toast = Toast.makeText(borrowerHomepage, sentence , Toast.LENGTH_LONG);
                                                            toast.show();
                                                        }
                                                    }
                                                }
                                            });
                                    borrowerHomepage.addListenerRegistration(listenerRegistration);
                                    borrowerHomepage.addIsbn(dc.getDocument().getId());
                                    break;
                                case MODIFIED:

                                    String isbn = dc.getDocument().getId();
                                    if(borrowerHomepage.getIsbns().contains(isbn)){
                                        int index = borrowerHomepage.getIsbns().indexOf(isbn);
                                        borrowerHomepage.removeListenerRegistration(index);
                                    }else{
                                        ListenerRegistration listenerRegistration1 = dc.getDocument().getReference()
                                                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                                        if(value.get("time") != null){
                                                            BookRequest requestHere = dc.getDocument().toObject(BookRequest.class);
                                                            System.out.println("3");
                                                            if(requestHere.getBook().getStatus().equals("accepted") && requestHere.getNotify() == 1) {
                                                                dc.getDocument().getReference().update("notify", 2);
                                                                Book bookHere = requestHere.getBook();
                                                                String sentence = "Delivery time and location set for \n Book: " + bookHere.getTitle();
                                                                Toast toast = Toast.makeText(borrowerHomepage,
                                                                        sentence, Toast.LENGTH_LONG);
                                                                toast.show();
                                                            }
                                                        }

                                                        BookRequest requestHere = dc.getDocument().toObject(BookRequest.class);
                                                        if(requestHere.getBook().getStatus().equals("accepted") && requestHere.getNotify() == 0){
                                                            System.out.println("4");
                                                            if(value.get("time") == null){
                                                                dc.getDocument().getReference().update("notify", 1);
                                                                System.out.println("modified" + dc.getDocument().get("book.title"));
                                                                Book bookHere = requestHere.getBook();
                                                                String sentence = "Owner have accepted you request on \n" + bookHere.getTitle();
                                                                Toast toast = Toast.makeText(borrowerHomepage, sentence , Toast.LENGTH_LONG);
                                                                toast.show();
                                                            }
                                                        }
                                                    }
                                                });
                                        borrowerHomepage.addListenerRegistration(listenerRegistration1);
                                        borrowerHomepage.addIsbn(dc.getDocument().getId());
                                    }
                                    break;

                                case REMOVED:
                                    String isbn1 = dc.getDocument().getId();
                                    if(borrowerHomepage.getIsbns().contains(isbn1)) {
                                        int index = borrowerHomepage.getIsbns().indexOf(isbn1);
                                        borrowerHomepage.removeListenerRegistration(index);
                                    }
                                default:
                                    break;

                            }
                        }
                    }
                });


    }

    /**
     * This method allows users to tap the name of owner or borrower
     * in BorrowerBookProfile to see their profile
     * @param borrowerBookProfile
     * @param username
     * These are related activity and the username
     */
    public void getUserBorrowBookProfile(final BorrowerBookProfile borrowerBookProfile, String username){
        DocumentReference docRef = db.collection("users").document(username);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    Owner owner = documentSnapshot.toObject(Owner.class);
                    Intent intent = new Intent(borrowerBookProfile, ShowUserDetail.class);
                    intent.putExtra("user", owner);
                    borrowerBookProfile.startActivity(intent);
                    borrowerBookProfile.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            }
        });
    }

    /**
     * This method allows users to tap the name of owner or borrower
     * in ShowBookDetail to see their profile
     * @param showBookDetail
     * @param username
     * These are related activity and the username
     */
    public void getUserShowBookDetail(final ShowBookDetail showBookDetail, String username){
        DocumentReference docRef = db.collection("users").document(username);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    Owner owner = documentSnapshot.toObject(Owner.class);
                    Intent intent = new Intent(showBookDetail, ShowUserDetail.class);
                    intent.putExtra("user", owner);
                    showBookDetail.startActivity(intent);
                    showBookDetail.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            }
        });
    }

    /**
     * This method allows users to tap the name of owner or borrower
     * in BorrowerRequestDelivery to see their profile
     * @param borrowerRequestDelivery
     * @param username
     * These are related activity and the username
     */
    public void getUserBorrowerRequestDelivery(final BorrowerRequestDelivery borrowerRequestDelivery, String username){
        DocumentReference docRef = db.collection("users").document(username);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    Owner owner = documentSnapshot.toObject(Owner.class);
                    Intent intent = new Intent(borrowerRequestDelivery, ShowUserDetail.class);
                    intent.putExtra("user", owner);
                    borrowerRequestDelivery.startActivity(intent);
                    borrowerRequestDelivery.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            }
        });
    }

    /**
     * This method allows users to tap the name of owner or borrower
     * in OnwerBookProfile to see their profile
     * @param ownerBookProfile
     * @param username
     * These are related activity and the username
     */
    public void getUserOwnerBookProfile(final OwnerBookProfile ownerBookProfile, String username){
        DocumentReference docRef = db.collection("users").document(username);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Owner owner = documentSnapshot.toObject(Owner.class);
                    Intent intent = new Intent(ownerBookProfile, ShowUserDetail.class);
                    intent.putExtra("user", owner);
                    ownerBookProfile.startActivity(intent);
                    ownerBookProfile.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            }
        });
    }

    /**
     * This method allows users to tap the name of owner or borrower
     * in Request Profile to see their profile
     * @param requestProfile
     * @param username
     * These are related activity and the username
     */
    public void getUserRequestProfile(final RequestProfile requestProfile, String username){
        DocumentReference docRef = db.collection("users").document(username);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    Owner owner = documentSnapshot.toObject(Owner.class);
                    Intent intent = new Intent(requestProfile, ShowUserDetail.class);
                    intent.putExtra("user", owner);
                    requestProfile.startActivity(intent);
                    requestProfile.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            }
        });
    }

    /**
     * This method allows users to tap the name of owner or borrower
     * in BorrowerReturn to see their profile
     * @param borrowerReturn
     * @param username
     * These are related activity and the username
     */
    public void getUserBorrowerReturn(final BorrowerReturn borrowerReturn, String username){
        DocumentReference docRef = db.collection("users").document(username);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    Owner owner = documentSnapshot.toObject(Owner.class);
                    Intent intent = new Intent(borrowerReturn, ShowUserDetail.class);
                    intent.putExtra("user", owner);
                    borrowerReturn.startActivity(intent);
                    borrowerReturn.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            }
        });
    }

    /**
     * This method allows users to tap the name of owner or borrower
     * in OwnerReturn to see their profile
     * @param ownerReturn
     * @param username
     * These are related activity and the username
     */
    public void getUserOwnerReturn(final OwnerReturn ownerReturn, String username){
        DocumentReference docRef = db.collection("users").document(username);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    Owner owner = documentSnapshot.toObject(Owner.class);
                    Intent intent = new Intent(ownerReturn, ShowUserDetail.class);
                    intent.putExtra("user", owner);
                    ownerReturn.startActivity(intent);
                    ownerReturn.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            }
        });
    }

    /**
     * This method allows users to tap the name of owner or borrower
     * in OwnerAcceptDeclineFragment to see their profile
     * @param ownerAcceptDeclineFragment
     * @param username
     * These are related activity and the username
     */
    public void getUserOwnerAcceptedDecline(final OwnerAcceptDeclineFragment ownerAcceptDeclineFragment, String username){
        DocumentReference docRef = db.collection("users").document(username);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    Owner owner = documentSnapshot.toObject(Owner.class);
                    Intent intent = new Intent(ownerAcceptDeclineFragment.getActivity(), ShowUserDetail.class);
                    intent.putExtra("user", owner);
                    ownerAcceptDeclineFragment.startActivity(intent);
                    ownerAcceptDeclineFragment.getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            }
        });
    }

    /**
     * This method is used to delete the image of a book
     * @param isbn
     * This is the isbn of the book
     */
    public void deleteImage(String isbn){
        db.collection("books").document(isbn).update("image", null);
    }

    public void OwnerHomePageNotify(final OwnerHomepage ownerHomepage, final String username){
        db.collection("users").document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()){
                        ownerHomepage.setBefore(((ArrayList<String>)documentSnapshot.get("requestList")).size());
                        ListenerRegistration l = db.collection("users").document(username).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                if(value.exists()) {
                                    Integer now = ((ArrayList<String>) value.get("requestList")).size();
                                    if (ownerHomepage.getBefore() < now) {
                                        String sentence = "You have received a new request";
                                        Toast toast = Toast.makeText(ownerHomepage, sentence, Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                    ownerHomepage.setBefore(now);
                                }
                            }
                        });
                        ownerHomepage.setListenerRegistration(l);
                    }
                }
            }
        });
    }

}
