package com.example.webook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
 * This is activity shows search results as a list of user
 * User click on item in list to jump to profile activity
 * @pram EXTRA_MESSAGE User item to show in profile
 */
public class OwnerSearchUserPage extends AppCompatActivity {
    ListView userList;
    EditText input;
    ArrayList<User> dataList;
    ArrayAdapter<User> userAdapter;
    private DataBaseManager dataBaseManager;
    public static final String EXTRA_MESSAGE = "com.example.BorrowerSearchUserPage.MESSAGE";
    private SuperSwipeRefreshLayout swipeRefreshLayout;
    
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
//        input.setHint("Searching users");
        dataList = new ArrayList<>();
        userAdapter = new UserList(this, dataList);
        userList.setAdapter(userAdapter);
        final ArrayList<String> userNameList = new ArrayList<String>();
        dataBaseManager = new DataBaseManager();

        dataBaseManager.OwnerSearchUser(message,this);
        ProgressBar loading = findViewById(R.id.loadingPanelMid);
        loading.clearAnimation();
        loading.setVisibility(View.GONE);

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(OwnerSearchUserPage.this,ShowUserDetail.class);
                User user = dataList.get(i);
                intent.putExtra("user", user);
                startActivity(intent);
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
//                              input = findViewById(R.id.search_book_user_result);
//                              input.setHint("Searching users");
                                dataList = new ArrayList<>();
                                userAdapter = new UserList(this, dataList);
                                userList.setAdapter(userAdapter);
                                final ArrayList<String> userNameList = new ArrayList<String>();
                                dataBaseManager = new DataBaseManager();
                                dataBaseManager.OwnerSearchUser(message,this);

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
