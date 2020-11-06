package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SameBookRequestList extends AppCompatActivity implements OwnerAcceptDeclineFragment.OnFragmentInteractionListener{
    private ListView sameBookRequestList;
    private RequestList bookAdapter;
    private static ArrayList<BookRequest> dataList;
    private static final String TAG = "Sample";
    private Book selectBook;
    private DataBaseManager dataBaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_same_book_list);
        sameBookRequestList = findViewById(R.id.same_book_request_list);
        dataList = new ArrayList<BookRequest>();
        bookAdapter = new RequestList(this,dataList);
        sameBookRequestList.setAdapter(bookAdapter);
        final Intent intent = getIntent();
        selectBook = (Book) intent.getSerializableExtra("selectBook");
        dataBaseManager = new DataBaseManager();
//        final BookRequest newRequest = new BookRequest(selectBook, selectBook.getOwner(), "requester1", null, null);
        dataBaseManager.getSameBookRequest(selectBook.getISBN(),this);


        sameBookRequestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Object selectRequest = sameBookRequestList.getItemAtPosition(position);
                OwnerAcceptDeclineFragment ownerAcceptDeclineFragment = OwnerAcceptDeclineFragment.newInstance((BookRequest) selectRequest, position);
                ownerAcceptDeclineFragment.show(getSupportFragmentManager(), "Accept or Decline");
            }
        });
    }

    public void dataListClear(){
        dataList.clear();
    }
    public void dataListAdd(BookRequest request){
        dataList.add(request);
    }
    public void bookAdapterChanged(){
        bookAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAcceptPressed(){
        finish();
    }


    @Override
    public void onDeclinePressed(){
        dataBaseManager.declinePressed(selectBook.getISBN(),this);
    }
}