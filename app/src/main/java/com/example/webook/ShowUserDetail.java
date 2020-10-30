package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowUserDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_detail);
        Intent intent = getIntent();
        final User user = (User)intent.getSerializableExtra(BorrowerSearchUserPage.EXTRA_MESSAGE);
        final TextView username = findViewById(R.id.detail_UserName);
        final TextView userType = findViewById(R.id.detail_UserType);
        final TextView  phone = findViewById(R.id.detail_Phone);
        final TextView  email = findViewById(R.id.detailed_UserEmail);
        final TextView description = findViewById(R.id.detail_UserDescription);
        username.setText(user.getUsername());
        userType.setText(user.getUserType());
        phone.setText(user.getPhoneNumber());
        email.setText(user.getEmail());
        description.setText(user.getDescription());
    }
}