package com.example.webook;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

/**
 * This activity shows book adding interface
 * Allows users to input book info, picture or sacnning the book's code
 */
public class AddBookActivity extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_edit_book);
        Intent intent = getIntent();
        owner = (Owner) intent.getSerializableExtra("user");
        scanButton = findViewById(R.id.scan_button);
        confirmButton = findViewById(R.id.confirm_add_book_button);
        book_icon = findViewById(R.id.book_icon_add_edit);
        title = findViewById(R.id.editTextBookTitle);
        author = findViewById(R.id.editTextBookAuthor);
        isbn = findViewById(R.id.editTextISBN);
        description = findViewById(R.id.editTextDescription);
        book_icon = findViewById(R.id.book_icon_add_edit);
        dataBaseManager = new DataBaseManager();
        book_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePiker();

            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) book_icon.getDrawable() ).getBitmap();
                Book book = new Book(title.getText().toString(), isbn.getText().toString(), author.getText().toString(),
                        "available", owner.getUsername(), null, description.getText().toString());
                dataBaseManager.addBook(bitmap,book,owner);
                finish();
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddBookActivity.this, CodeScanner.class);
                startActivityForResult(intent, SCAN_CODE);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        });


    }

    /**
     * This function picks the book image from camera or gallery
     */

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void imagePiker() {
        AlertDialog.Builder camOrGal = new AlertDialog.Builder(AddBookActivity.this);
        camOrGal.setTitle("Choose your source");
        final CharSequence[] selection = {"Camera", "Photo Gallery"};
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
            book_icon.setImageURI(imageUri);
        }

        else if (requestCode == CAMERA && resultCode == RESULT_OK && data != null && data.getExtras() != null) {
            Bundle bundle = data.getExtras();
            Bitmap imageBitmap = (Bitmap) bundle.get("data");
            book_icon.setImageBitmap(imageBitmap);
        }else if (requestCode == SCAN_CODE && resultCode == RESULT_OK && data != null && data.getExtras() != null){
            String isbnString = data.getStringExtra("code");
            isbn.setText(isbnString);
        }
    }
}