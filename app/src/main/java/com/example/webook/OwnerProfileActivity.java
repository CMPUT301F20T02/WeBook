package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class OwnerProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);
        TextView username = findViewById(R.id.username);
        TextView userType = findViewById(R.id.user_type);
        TextView phone = findViewById(R.id.phone);
        TextView email = findViewById(R.id.email);
        ImageView user_pic = findViewById(R.id.user_image);
        Button addButton = findViewById(R.id.addButton);
        Button editButton = findViewById(R.id.editButton);
        TextView description = findViewById(R.id.owner_description);


        Intent intent = getIntent();
        final Owner owner = (Owner) intent.getSerializableExtra("user");


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerProfileActivity.this, AddBookActivity.class);

                startActivity(intent);
            }
        });

    }


}