package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * This is activity shows a user's profile
 * @pram EXTRA_MESSAGE User item to show in profile, clicked in list by user in formal actvity
 */
public class ShowUserDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Intent intent = getIntent();
        final User user = (User)intent.getSerializableExtra(BorrowerSearchUserPage.EXTRA_MESSAGE);
        final TextView username = findViewById(R.id.user_username);
        final TextView userType = findViewById(R.id.user_userType);
        final TextView  phone = findViewById(R.id.user_phone);
        final TextView  email = findViewById(R.id.user_email);
        final TextView description = findViewById(R.id.user_description);
        final ImageView image = findViewById(R.id.user_icon);
        image.setImageDrawable(getResources().getDrawable(R.drawable.empty_user_icon));
        username.setText(user.getUsername());
        userType.setText(user.getUserType());
        phone.setText(user.getPhoneNumber());
        email.setText(user.getEmail());
        description.setText(user.getDescription());
    }
}