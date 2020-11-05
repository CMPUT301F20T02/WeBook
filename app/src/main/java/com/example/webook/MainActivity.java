package com.example.webook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Sample";
    private TextView username;
    private TextView pwd;
    private Button login;
    private Button signUp;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username_input);
        pwd = findViewById(R.id.pwd_input);
        signUp = findViewById(R.id.signup_button);
        login = findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_text = username.getText().toString();
                String pwd_text = pwd.getText().toString();
                if (username_text.length() != 0 & pwd_text.length() != 0){
                    authenticate(username_text, pwd_text);
                }

            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        username.setText("");
        pwd.setText("");
    }

    public void authenticate(final String username, final String pwd){
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
                            intent = new Intent(MainActivity.this, OwnerHomepage.class);
                            Owner owner = new Owner(username, document.getString("email"),
                                    document.getString("phoneNumber"), document.getString("pwd"), document.getString("description"), document.getString("user_image"));
                            owner.setBookList((ArrayList<String>) document.get("bookList"));
                            intent.putExtra("user", owner);
                            startActivity(intent);
                        }else if (document.getString("userType").equals("borrower")) {
                            intent = new Intent(MainActivity.this, BorrowerHomepage.class);
                            Borrower borrower = new Borrower(username, document.getString("email"),
                                    document.getString("phoneNumber"), document.getString("pwd"), document.getString("description"), document.getString("user_image"));
                            intent.putExtra("user", borrower);
                            startActivity(intent);
                        }
                    } else {
                        Toast toast = Toast.makeText(MainActivity.this, "Incorrect credentials!", duration);
                        toast.show();
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        } );
    }
}