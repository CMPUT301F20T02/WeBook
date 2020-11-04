package com.example.webook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class OwnerProfileActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView username;
    private TextView userType;
    private TextView phone;
    private TextView email;
    private ImageView user_pic;
    private Button addButton;
    private Button editButton;
    private TextView description;
    private Owner owner;
    private DocumentReference userRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);

        username = findViewById(R.id.owner_username);
        userType = findViewById(R.id.owner_user_type);
        phone = findViewById(R.id.owner_phone);
        email = findViewById(R.id.owner_email);
        user_pic = findViewById(R.id.owner_image);
        addButton = findViewById(R.id.addBookButton);
        editButton = findViewById(R.id.owner_editProfile);
        description = findViewById(R.id.owner_description);


        Intent intent = getIntent();
        owner = (Owner) intent.getSerializableExtra("user");
        userRef = db.collection("users").document(owner.getUsername());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerProfileActivity.this, AddBookActivity.class);
                intent.putExtra("user", owner);
                startActivity(intent);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerProfileActivity.this, EditUserProfileActivity.class);
                intent.putExtra("user", owner);
                startActivity(intent);
            }
        });


        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference user_picRef = storageReference.child("images/empty_user_icon.png");

        //String a = user_picRef.getDownloadUrl().toString();
        //description.setText(a);

        Drawable image = getResources().getDrawable(R.drawable.empty_user_icon);
        Bitmap image1 = BitmapFactory.decodeResource(getResources(), R.drawable.empty_user_icon);

        //user_pic.setImageBitmap(image1);


        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                updateUserInfo(value);
            }
        });


        Glide.with(OwnerProfileActivity.this)
                .load(user_picRef)
                .into(user_pic);
    }

    private void updateUserInfo(DocumentSnapshot document) {
        owner = document.toObject(Owner.class);
        username.setText(owner.getUsername());
        userType.setText(owner.getUserType());
        phone.setText(owner.getPhoneNumber());
        email.setText(owner.getEmail());
        description.setText(owner.getDescription());
    }
}