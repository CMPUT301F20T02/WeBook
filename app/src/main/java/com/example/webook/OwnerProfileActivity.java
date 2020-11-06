package com.example.webook;

/**
 * This activity is created by OwnerHomePage when a owner click on me to view his profile
 */

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class OwnerProfileActivity extends AppCompatActivity {
    private TextView username;
    private TextView userType;
    private TextView phone;
    private TextView email;
    private ImageView user_pic;
    private Button addButton;
    private Button editButton;
    private TextView description;
    private Owner owner;
    private DataBaseManager dataBaseManager = new DataBaseManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);

        username = findViewById(R.id.owner_username);
        userType = findViewById(R.id.owner_userType);
        phone = findViewById(R.id.owner_phone);
        email = findViewById(R.id.owner_email);
        user_pic = findViewById(R.id.owner_image);
        addButton = findViewById(R.id.addBookButton);
        editButton = findViewById(R.id.owner_editProfile);
        description = findViewById(R.id.owner_description);
        Intent intent = getIntent();
        owner = (Owner) intent.getSerializableExtra("user");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerProfileActivity.this, AddBookActivity.class);
                intent.putExtra("user", owner);
                startActivity(intent);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerProfileActivity.this, EditUserProfileActivity.class);
                intent.putExtra("user", owner);
                startActivity(intent);
            }
        });

        Drawable empty_user = getResources().getDrawable(R.drawable.empty_user_icon);
        user_pic.setImageDrawable(empty_user);
        dataBaseManager.OwnerProfileAddUserSnapShotListener(this, owner.getUsername());
    }


    /**
     * This sets the username in database manager
     * @param usernameText
     * this os the username to be set
     */
    public void setUsername(String usernameText){
        this.username.setText(usernameText);
    }


    /**
     * this sets the usertype in the database manager
     * @param userTypeText
     * this is the user type to be set
     */
    public void setUserType(String userTypeText){
        this.userType.setText(userTypeText);
    }


    /**
     * This set the phone number on the profile page
     * @param phoneText
     * This is the phone number to be set
     */
    public void setPhone(String phoneText){
        this.phone.setText(phoneText);
    }


    /**
     * This sets the email on the profile page
     * @param emailText
     * this is the email to be set
     */
    public void setEmail(String emailText){
        this.email.setText(emailText);
    }


    /**
     * This sets the description on the profile page
     * @param descriptionText
     * this is the description to be set
     */
    public void setDescription(String descriptionText){
        this.description.setText(descriptionText);
    }
}