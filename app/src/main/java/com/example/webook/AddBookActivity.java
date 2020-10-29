package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AddBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        Button confirmButton = findViewById(R.id.confirm_add_book_button);
        ImageView book_icon = findViewById(R.id.book_icon_add_book);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView title = findViewById(R.id.editTextBookTitle);
                TextView author = findViewById(R.id.editTextBookAuthor);
                TextView isbn = findViewById(R.id.editTextISBN);
                TextView description = findViewById(R.id.editTextDescription);

                Book book = new Book(title.getText().toString(), isbn.getText().toString(), author.getText().toString(),
                        "available", "test_user", null, description.getText().toString());

            }
        });


    }
}