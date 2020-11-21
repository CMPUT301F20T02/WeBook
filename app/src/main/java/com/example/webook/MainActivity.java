package com.example.webook;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
This is the mainactivity shows users' login interface
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Sample";
    private TextView username;
    private TextView pwd;
    private Button login;
    private Button signUp;
    private  DataBaseManager dataBaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username_input);
        pwd = findViewById(R.id.pwd_input);
        signUp = findViewById(R.id.signup_button);
        login = findViewById(R.id.login_button);
        dataBaseManager = new DataBaseManager();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_text = username.getText().toString();
                String pwd_text = pwd.getText().toString();
                if (username_text.length() != 0 & pwd_text.length() != 0){
                    dataBaseManager.authenticate(username_text, pwd_text,MainActivity.this);
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }



    @Override
    protected void onStop() {
        super.onStop();
        username.setText("");
        pwd.setText("");
    }


}