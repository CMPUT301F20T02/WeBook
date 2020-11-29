package com.example.webook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
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
    EditText input;
    ArrayList<Book> dataList;
    ArrayAdapter<Book> bookAdapter;
    public static final String EXTRA_MESSAGE = "selectBook";
    private Borrower borrower;
    private  DataBaseManager dataBaseManager;
    private Intent intent;
    private String message;
    private SuperSwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        final String TAG = "Book";
        // User's key for search
        intent = getIntent();
        message = intent.getStringExtra(BorrowerSearch.EXTRA_MESSAGE);
        bookList = findViewById(R.id.search_result_list);
//        input = findViewById(R.id.search_book_user_result);
        dataList = new ArrayList<Book>();
        bookAdapter = new BookList(this, dataList);
        bookList.setAdapter(bookAdapter);
        dataBaseManager = new DataBaseManager();

        borrower = (Borrower)intent.getSerializableExtra("borrower");
        
        dataBaseManager.BorrowerSearchBook(message,this);
        RelativeLayout loading = findViewById(R.id.loadingPanelMid);
        loading.clearAnimation();
        loading.setVisibility(View.GONE);
        
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(BorrowerSearchBookPage.this,BorrowerBookProfile.class);
                Book book = dataList.get(i);
                intent.putExtra(EXTRA_MESSAGE, book);
                intent.putExtra("borrower", borrower);
                startActivityForResult(intent,1);
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
            }
        });



        swipeRefreshLayout = (SuperSwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                Intent intent = getIntent();
                                final String message = intent.getStringExtra(BorrowerSearch.EXTRA_MESSAGE);
                                bookList = findViewById(R.id.search_result_list);
                                dataList = new ArrayList<Book>();
                                bookAdapter = new BookList(BorrowerSearchBookPage.this, dataList);
                                bookList.setAdapter(bookAdapter);
                                dataBaseManager = new DataBaseManager();

                                borrower = (Borrower)intent.getSerializableExtra("borrower");
                                dataBaseManager.BorrowerSearchBook(message,BorrowerSearchBookPage.this);

                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }, 1000);
                    }

                    @Override
                    public void onPullDistance(int distance) {
                        System.out.println("debug:distance = " + distance);
                        // myAdapter.updateHeaderHeight(distance);
                    }

                    @Override
                    public void onPullEnable(boolean enable) {
                    }
                });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("key");
                if (strEditText.equals("request")) {
                    dataBaseManager.BorrowerSearchBook(message, this);
                }
            }
        }
    }
}
