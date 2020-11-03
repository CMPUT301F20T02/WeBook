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

public class BorrowerSearchUserPage extends AppCompatActivity {
    ListView userList;
    EditText input;
    ArrayList<User> dataList;
    ArrayAdapter<User> userAdapter;
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
        input = findViewById(R.id.search_book_user_result);
        input.setHint("Search for users");
        dataList = new ArrayList<>();
        userAdapter = new UserList(this, dataList);
        userList.setAdapter(userAdapter);
        final ArrayList<String> userNameList = new ArrayList<String>();
        final FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("users");

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(BorrowerSearchUserPage.this,ShowUserDetail.class);
                User user = dataList.get(i);
                intent.putExtra(EXTRA_MESSAGE, user);
                startActivity(intent);
            }
        });

        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            dataList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId());
                                String Username = document.getId();
                                String email = (String) document.getData().get("email");
                                String userType =  (String) document.getData().get("userType");
                                String pwd =  (String) document.getData().get("pwd");
                                String phoneNumber =  (String) document.getData().get("phoneNumber");
                                if(Username.contains(message)){
                                    if(userType.equals("borrower")) {
                                        dataList.add(new Borrower(Username,email, phoneNumber, pwd, userType));
                                    }else{
                                        dataList.add(new Owner(Username,email, phoneNumber, pwd, userType));
                                    }
                                }
                            }
                            userAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        collectionReference.get();
    }
}
