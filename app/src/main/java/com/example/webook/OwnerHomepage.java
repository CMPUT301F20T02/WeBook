package com.example.webook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class OwnerHomepage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_owner_homepage);
        /*
=======
        setContentView(R.layout.activity_owner_homepage);
>>>>>>> cd752cbf30340312dd0f8f2debf588b89120c9b2
        Drawable image = getResources().getDrawable(R.drawable.book_icon);
        final Owner owner = new Owner("asd", "asadsda", "asdsad", "qwewqe");
        owner.addBook("TITLE","ISBN","ASD",image,"sdassd", "available");
        owner.addBook("TITLE","ISBN","ASD",image,"sdassd", "available");
        owner.addBook("TITLE","ISBN","ASD",image,"sdassd", "available");
        owner.addBook("TITLE","ISBN","ASD",image,"sdassd", "borrowed");
        owner.addBook("TITLE","ISBN","ASD",image,"sdassd", "borrowed");
        owner.addBook("TITLE","ISBN","ASD",image,"sdassd", "borrowed");
        owner.addBook("TITLE","ISBN","ASD",image,"sdassd", "requested");
        owner.addBook("TITLE","ISBN","ASD",image,"sdassd", "accepted");
        owner.addBook("TITLE","ISBN","ASD",image,"sdassd", "accepted");
<<<<<<< HEAD

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imagesRef = storageReference.child("images/" + "book_icon");

        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.book_icon)).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();



        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(OwnerHomepage.this, "Incorrect credentials!", duration);
                toast.show();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(OwnerHomepage.this, "correct credentials!", duration);
                toast.show();
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });

*/

















        TextView me = findViewById(R.id.owner_me_tab);

        Intent intent = getIntent();
        final Owner owner = (Owner) intent.getSerializableExtra("user");
        final ListView bookListView = findViewById(R.id.owner_book_list);
        final BookList bookList = new BookList(this, owner.getBookList());

        bookListView.setAdapter(bookList);
        TextView all = findViewById(R.id.owner_all);
        TextView available = findViewById(R.id.owner_available);
        TextView requested = findViewById(R.id.owner_requested);
        TextView accepted = findViewById(R.id.owner_accepted);
        TextView borrowed = findViewById(R.id.owner_borrowed);

        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomepage.this, OwnerProfileActivity.class);
                intent.putExtra("user", owner);
                startActivity(intent);
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookListView.setAdapter(bookList);
            }
        });

        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookList bookList1 = new BookList(OwnerHomepage.this, owner.getAvailable());
                bookListView.setAdapter(bookList1);
            }
        });

        requested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookList bookList2 = new BookList(OwnerHomepage.this, owner.getRequested());
                bookListView.setAdapter(bookList2);
            }
        });

        accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookList bookList3 = new BookList(OwnerHomepage.this, owner.getAccepted());
                bookListView.setAdapter(bookList3);
            }
        });

        borrowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookList bookList4 = new BookList(OwnerHomepage.this, owner.getBorrowed());
                bookListView.setAdapter(bookList4);
            }
        });



    }

}