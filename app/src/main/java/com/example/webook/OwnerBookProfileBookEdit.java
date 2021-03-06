package com.example.webook;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class OwnerBookProfileBookEdit extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private static final int CAMERA = 2;
    private static final int SCAN_CODE = 3;
    private TextView title;
    private TextView author;
    private TextView isbn;
    private TextView description;
    private ImageView book_icon;
    private Button confirmButton;
    private Uri imageUri;
    private Owner owner;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private Button scanButton;
    private DataBaseManager dataBaseManager;
    private int imageChanged = 0;  // 0 no change, 1 deleted, 2 updated.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_edit_book);
        Intent intent = getIntent();
        final Book oldbook = (Book) intent.getSerializableExtra("selectBook");
        owner = (Owner) intent.getSerializableExtra("user");
        scanButton = findViewById(R.id.scan_button);
        confirmButton = findViewById(R.id.confirm_add_book_button);
        book_icon = findViewById(R.id.book_icon_add_edit);
        title = findViewById(R.id.editTextBookTitle);
        author = findViewById(R.id.editTextBookAuthor);
        isbn = findViewById(R.id.editTextISBN);
        description = findViewById(R.id.editTextDescription);
        title.setText(oldbook.getTitle());
        author.setText(oldbook.getAuthor());
        isbn.setText(oldbook.getISBN());
        description.setText(oldbook.getDescription());

        dataBaseManager = new DataBaseManager();
        if (oldbook.getImage() == null){
            book_icon.setImageResource(R.drawable.book_icon);
        }else{
            Glide.with(this)
                    .load(oldbook.getImage())
                    .into(book_icon);
        }
        book_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePiker(oldbook.getImage());
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseManager.updateBook(isbn.getText().toString(),title.getText().toString(),author.getText().toString(),description.getText().toString(), ((BitmapDrawable) book_icon.getDrawable() ).getBitmap(), imageChanged);
                finish();
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerBookProfileBookEdit.this, CodeScanner.class);
                startActivityForResult(intent, SCAN_CODE);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        });


    }

    /**
     * pick an new image or delete the old image
     * @param oldImage the old image or null if it does not exist
     */

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void imagePiker(final String oldImage) {
        final AlertDialog.Builder camOrGal = new AlertDialog.Builder(OwnerBookProfileBookEdit.this);
        camOrGal.setTitle("Edit book image");
        final CharSequence[] selection = new CharSequence[]{"Camera", "Photo Gallery", "Delete"};


        camOrGal.setItems( selection, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ( selection[which].equals("Camera") ) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                    }
                    else {
                        // permission has been already granted, you can use camera straight away
                    }
                    startActivityForResult(intent, CAMERA);
                    overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
                } else if ( selection[which].equals("Photo Gallery") ) {
                    System.out.println("the old image"+oldImage);
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, PICK_IMAGE);
                    overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
                }else if(selection[which].equals("Delete")){
                    imageChanged = 1;
                    book_icon.setImageDrawable(getResources().getDrawable(R.drawable.book_icon));
                    dataBaseManager.deleteImage(isbn.getText().toString());
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
            book_icon.setImageURI(imageUri);
            imageChanged = 2;
        }

        else if (requestCode == CAMERA && resultCode == RESULT_OK && data != null && data.getExtras() != null) {
            Bundle bundle = data.getExtras();
            Bitmap imageBitmap = (Bitmap) bundle.get("data");
            book_icon.setImageBitmap(imageBitmap);
            imageChanged = 2;
        }else if (requestCode == SCAN_CODE && resultCode == RESULT_OK && data != null && data.getExtras() != null){
            String isbnString = data.getStringExtra("code");
            isbn.setText(isbnString);
        }
    }


}
