package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BorrowerHomepage extends AppCompatActivity {
    private Borrower borrower;
    private TextView me;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_homepage);

        Intent intent = getIntent();
        borrower = (Borrower) intent.getSerializableExtra("user");

        me = findViewById(R.id.borrower_me_tab);


        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BorrowerHomepage.this, BorrowerProfileActivity.class);
                intent.putExtra("user", borrower);
                startActivity(intent);

            }
        });

    }
}