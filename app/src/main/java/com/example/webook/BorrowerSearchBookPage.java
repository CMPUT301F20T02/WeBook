package com.example.webook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
/**
 * This is activity shows search results as a list of book
 * User click on item in list to jump to profile activity
 * @pram EXTRA_MESSAGE Book item to show in profile
 * @pram borrower Borrower item keeps user information
 */
public class BorrowerSearchBookPage extends AppCompatActivity {
    ListView bookList;
    ArrayList<Book> dataList;
    ArrayAdapter<Book> bookAdapter;
    public static final String EXTRA_MESSAGE = "selectBook";
    private Borrower borrower;
    private  DataBaseManager dataBaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        final String TAG = "Book";
        // User's key for search
        Intent intent = getIntent();
        final String message = intent.getStringExtra(BorrowerSearch.EXTRA_MESSAGE);
        bookList = findViewById(R.id.search_result_list);
        dataList = new ArrayList<Book>();
        bookAdapter = new BookList(this, dataList);
        bookList.setAdapter(bookAdapter);
        dataBaseManager = new DataBaseManager();

        borrower = (Borrower)intent.getSerializableExtra("borrower");
        dataBaseManager.BorrowerSearchBook(message,this);
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(BorrowerSearchBookPage.this,BorrowerBookProfile.class);
                Book book = dataList.get(i);
                intent.putExtra(EXTRA_MESSAGE, book);
                intent.putExtra("borrower", borrower);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
            }
        });
    }
}
