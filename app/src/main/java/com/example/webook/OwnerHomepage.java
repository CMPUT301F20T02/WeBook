package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OwnerHomepage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner__homepage);

        Owner owner = new Owner("test", "test@test.com", "110", "123");
        owner.addBook("AA", "1234-5678", "Jia", null, "DES");
        ListView bookListView = findViewById(R.id.book_list);
        bookListView.setAdapter(owner.getBookList());

        TextView me = findViewById(R.id.me);

        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomepage.this, OwnerProfileActivity.class);

                startActivity(intent);
            }
        });




    }

}