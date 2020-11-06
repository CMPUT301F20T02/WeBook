package com.example.webook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
/**
 * This is an activity shows interface for borrower's profile with an edit button
 * User press edit to jump to EditUserProfileActivity
 */
public class BorrowerProfileActivity extends AppCompatActivity {
    private TextView username;
    private TextView userType;
    private TextView phone;
    private TextView email;
    public ImageView user_pic;
    private Button editButton;
    private TextView description;
    private Borrower borrower;
    private DataBaseManager dataBaseManager = new DataBaseManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_profile);

        username = findViewById(R.id.borrower_username);
        userType = findViewById(R.id.borrower_userType);
        phone = findViewById(R.id.borrower_phone);
        email = findViewById(R.id.borrower_email);
        user_pic = findViewById(R.id.borrower_image);
        editButton = findViewById(R.id.borrower_editProfile);
        description = findViewById(R.id.borrower_description);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BorrowerProfileActivity.this, EditUserProfileActivity.class);
                intent.putExtra("user", borrower);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        borrower = (Borrower) intent.getSerializableExtra("user");
        Drawable empty_user = getResources().getDrawable(R.drawable.empty_user_icon);
        user_pic.setImageDrawable(empty_user);

        dataBaseManager.BorrowerProfileAddUserSnapShotListener(this, borrower.getUsername());
    }


    /**
     * This sets the username in database manager
     * @param usernameText
     * this os the username to be set
     */
    public void setUsername(String usernameText){
        this.username.setText(usernameText);
    }


    /**
     * this sets the usertype in the database manager
     * @param userTypeText
     * this is the user type to be set
     */
    public void setUserType(String userTypeText) {
        this.userType.setText(userTypeText);
    }


    /**
     * This set text on the profile page
     * @param document
     * This is a document contains info of your profile
     */
    private void updateUserInfo(DocumentSnapshot document) {
        borrower = document.toObject(Borrower.class);
        username.setText(borrower.getUsername());
        userType.setText(borrower.getUserType());
        phone.setText(borrower.getPhoneNumber());
        email.setText(borrower.getEmail());
        description.setText(borrower.getDescription());
    }


    /**
     * This sets the phone number on the profile page
     * @param phoneText
     * this is the phone to be set
     */
    public void setPhone(String phoneText){
        this.phone.setText(phoneText);
    }


    /**
     * This sets the email on the profile page
     * @param emailText
     * this is the email to be set
     */
    public void setEmail(String emailText){
        this.email.setText(emailText);
    }


    /**
     * This sets the description on the profile page
     * @param descriptionText
     * this is the description to be set
     */
    public void setDescription(String descriptionText){
        this.description.setText(descriptionText);
    }
}