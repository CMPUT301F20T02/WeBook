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

/*
        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                updateUserInfo(value);
            }
        });

 */



    }

    public void setUsername(String usernameText){
        this.username.setText(usernameText);
    }

    public void setUserType(String userTypeText){
        this.userType.setText(userTypeText);
    }

    public void setPhone(String phoneText){
        this.phone.setText(phoneText);
    }

    public void setEmail(String emailText){
        this.email.setText(emailText);
    }

    public void setDescription(String descriptionText){
        this.description.setText(descriptionText);
    }
}