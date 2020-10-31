package com.example.webook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;

public class OwnerProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);

        TextView username = findViewById(R.id.owner_username);
        TextView userType = findViewById(R.id.owner_user_type);
        TextView phone = findViewById(R.id.owner_phone);
        TextView email = findViewById(R.id.owner_email);
        ImageView user_pic = findViewById(R.id.owner_user_image);
        Button addButton = findViewById(R.id.addBookButton);
        Button editButton = findViewById(R.id.owner_editProfile);
        TextView description = findViewById(R.id.owner_user_description);


        Intent intent = getIntent();
        final Owner owner = (Owner) intent.getSerializableExtra("user");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerProfileActivity.this, AddBookActivity.class);
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
        username.setText(owner.getUsername());
        userType.setText(owner.getUserType());
        phone.setText(owner.getPhoneNumber());
        email.setText(owner.getEmail());
        Glide.with(OwnerProfileActivity.this)
                .load(user_picRef)
                .into(user_pic);
    }
}