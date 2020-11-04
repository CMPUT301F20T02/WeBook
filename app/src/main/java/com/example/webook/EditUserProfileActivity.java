package com.example.webook;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class EditUserProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int CAMERA = 2;
    private String url;
    private Uri imageUri;
    private TextView username;
    private TextView userType;
    private EditText phoneNumber;
    private EditText email;
    private EditText description;
    private ImageView userImage;
    private Button confirm;
    private Button cancel;
    private User user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_user_profile);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        username = findViewById(R.id.editPage_username);
        userType = findViewById(R.id.editPage_user_type);
        phoneNumber = findViewById(R.id.editUserPhone);
        email = findViewById(R.id.editUserEmail);
        description = findViewById(R.id.editUserDescription);
        userImage = findViewById(R.id.editUserImage);
        confirm = findViewById(R.id.editUserConfirm);
        cancel = findViewById(R.id.editUserCancel);

        username.setText(user.getUsername());
        userType.setText(user.getUserType());
        phoneNumber.setText(user.getPhoneNumber());
        email.setText(user.getEmail());
        description.setText(user.getDescription());

        if (user.getUser_image() == null){
            Drawable defaultImage = getResources().getDrawable(R.drawable.empty_user_icon);
            userImage.setImageDrawable(defaultImage);
        }else{
            Glide.with(EditUserProfileActivity.this)
                    .load(user.getUser_image())
                    .into(userImage);
        }


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneText = phoneNumber.getText().toString();
                String emailText = email.getText().toString();
                String descriptionText = description.getText().toString();
                //uploadImage();


                //user.editInformation(emailText, phoneText, descriptionText);
            }
        });


    }

/*
    private void imagePiker() {
        AlertDialog.Builder camOrGal = new AlertDialog.Builder(EditUserProfileActivity.this);
        camOrGal.setTitle("Choose your source");
        final CharSequence[] selection = {"Camera", "Photo Gallery"};
        camOrGal.setItems( selection, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ( selection[which].equals("Camera") ) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA);
                } else if ( selection[which].equals("Photo Gallery") ) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, PICK_IMAGE);
                }
            }
        } );
        camOrGal.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            userImage.setImageURI(imageUri);
        }

        else if (requestCode == CAMERA && resultCode == RESULT_OK && data != null && data.getExtras() != null) {
            Bundle bundle = data.getExtras();
            Bitmap imageBitmap = (Bitmap) bundle.get("data");
            userImage.setImageBitmap(imageBitmap);
        }
    }


    private void uploadImage(){
        Bitmap bitmap = ( (BitmapDrawable) userImage.getDrawable() ).getBitmap();
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
                                    url = uri.toString();
                                    updateUser();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("Upload image", "Error uploading.", e);
                            Toast toast = Toast.makeText(AddBookActivity.this,"Failed to add book.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
        }
    }


    public void updateUser(){
        final User user =
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
                        Toast toast = Toast.makeText(AddBookActivity.this,"Failed to add book.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
    }

*/

}
