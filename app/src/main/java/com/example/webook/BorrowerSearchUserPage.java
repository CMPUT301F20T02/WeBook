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
 * This is activity shows search results as a list of user
 * User click on item in list to jump to profile activity
 * @pram EXTRA_MESSAGE User item to show in profile
 */
public class BorrowerSearchUserPage extends AppCompatActivity {
    ListView userList;
    EditText input;
    ArrayList<User> dataList;
    ArrayAdapter<User> userAdapter;
    private DataBaseManager dataBaseManager;
    private SuperSwipeRefreshLayout swipeRefreshLayout;
    public static final String EXTRA_MESSAGE = "com.example.BorrowerSearchUserPage.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        final String TAG = "User";
        // User's key for search
        Intent intent = getIntent();
        final String message = intent.getStringExtra(BorrowerSearch.EXTRA_MESSAGE);
        userList = findViewById(R.id.search_result_list);
//        input = findViewById(R.id.search_book_user_result);
        dataList = new ArrayList<>();
        userAdapter = new UserList(this, dataList);
        userList.setAdapter(userAdapter);
        final ArrayList<String> userNameList = new ArrayList<String>();
        dataBaseManager = new DataBaseManager();

        dataBaseManager.BorrowerSearchUser(message,this);
        findViewById(R.id.loadingPanelMid).setVisibility(View.GONE);

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(BorrowerSearchUserPage.this,ShowUserDetail.class);
                User user = dataList.get(i);
                intent.putExtra(EXTRA_MESSAGE, user);
                startActivity(intent);
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
                                final String TAG = "User";
                                // User's key for search
                                Intent intent = getIntent();
                                final String message = intent.getStringExtra(BorrowerSearch.EXTRA_MESSAGE);
                                userList = findViewById(R.id.search_result_list);
//                                input = findViewById(R.id.search_book_user_result);
                                dataList = new ArrayList<>();
                                userAdapter = new UserList(BorrowerSearchUserPage.this, dataList);
                                userList.setAdapter(userAdapter);
                                final ArrayList<String> userNameList = new ArrayList<String>();
                                dataBaseManager = new DataBaseManager();
                                
                                dataBaseManager.BorrowerSearchUser(message,BorrowerSearchUserPage.this);

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
}
