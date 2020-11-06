package com.example.webook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    private EditText username;
    private EditText pwd;
    private EditText phone;
    private EditText email;
    private EditText description;
    private Button cancel;
    private Button signUpOwner;
    private Button SignUpBorrower;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.username_signup);
        pwd = findViewById(R.id.pwd_signup);
        phone = findViewById(R.id.phone_number_signup);
        email = findViewById(R.id.email_signup);
        description = findViewById(R.id.description_signup);
        cancel = findViewById(R.id.signup_cancel_button);
        signUpOwner = findViewById(R.id.signup_owner_button);
        SignUpBorrower = findViewById(R.id.signup_borrower_button);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signUpOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usernameText = username.getText().toString();
                final String pwdText = pwd.getText().toString();
                final String phoneText = phone.getText().toString();
                final String emailText = email.getText().toString();
                final String descriptionText = description.getText().toString();
                if (usernameText.length() != 0 && pwdText.length() != 0 && phoneText.length() != 0 && emailText.length() != 0){
                    final DocumentReference userRef = db.collection("users").document(usernameText);
                    userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()){
                                    Toast toast = Toast.makeText(SignUpActivity.this, "Username already in use!", Toast.LENGTH_SHORT);
                                    toast.show();
                                }else{
                                    userRef.set(new Owner(usernameText, emailText, phoneText, pwdText, descriptionText, null));
                                    Toast toast = Toast.makeText(SignUpActivity.this, "Sign up successful!", Toast.LENGTH_SHORT);
                                    toast.show();
                                    finish();
                                }
                            }
                        }
                    });
                }
            }
        });

        SignUpBorrower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usernameText = username.getText().toString();
                final String pwdText = pwd.getText().toString();
                final String phoneText = phone.getText().toString();
                final String emailText = email.getText().toString();
                final String descriptionText = description.getText().toString();
                if (usernameText.length() != 0 && pwdText.length() != 0 && phoneText.length() != 0 && emailText.length() != 0){
                    final DocumentReference userRef = db.collection("users").document(usernameText);
                    userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()){
                                    Toast toast = Toast.makeText(SignUpActivity.this, "Username already in use!", Toast.LENGTH_SHORT);
                                    toast.show();
                                }else{
                                    userRef.set(new Borrower(usernameText, emailText, phoneText, pwdText, descriptionText, null));
                                    Toast toast = Toast.makeText(SignUpActivity.this, "Sign up successful!", Toast.LENGTH_SHORT);
                                    toast.show();
                                    finish();
                                }
                            }
                        }
                    });
                }
            }
        });
    }
}