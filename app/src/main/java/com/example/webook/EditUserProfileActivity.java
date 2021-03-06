package com.example.webook;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
/**
 * This is an activity shows edit interface for user's profile
 * User can cancel or comfrim changes
 */
public class EditUserProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int CAMERA = 2;
    private TextView username;
    private TextView userType;
    private EditText phoneNumber;
    private EditText email;
    private EditText description;
    private ImageView userImage;
    private Button confirm;
    private Button cancel;
    private User user;
    private DataBaseManager dataBaseManager;
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
        dataBaseManager = new DataBaseManager();
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
                dataBaseManager.updateInfo(user.getUsername(), phoneText, emailText, descriptionText);
                finish();
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });


    }
}
