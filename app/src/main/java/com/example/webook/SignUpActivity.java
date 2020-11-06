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

/**
 * This is an activity shows sign up interface for user
 * User can click on buttons to choose to sign up as an owner or a borrower
 */
public class SignUpActivity extends AppCompatActivity {
    private EditText username;
    private EditText pwd;
    private EditText phone;
    private EditText email;
    private EditText description;
    private Button cancel;
    private Button signUpOwner;
    private Button SignUpBorrower;
    private DataBaseManager dataBaseManager;

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
        dataBaseManager = new DataBaseManager();
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
                    dataBaseManager.ownerSignUp(usernameText,emailText,phoneText,pwdText,descriptionText,SignUpActivity.this);
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
                    dataBaseManager.borrowerSignUp(usernameText,emailText,phoneText,pwdText,descriptionText,SignUpActivity.this);
                }
            }
        });
    }
}