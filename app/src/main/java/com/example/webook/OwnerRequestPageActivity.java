package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.internal.$Gson$Preconditions;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;

public class OwnerRequestPageActivity extends AppCompatActivity implements OwnerAcceptDeclineFragment.OnFragmentInteractionListener {
    private Owner owner;
    private RequestList requestListPending;
    private RequestList requestListAccepted;
    private RequestList requestListBorrowed;
    private ArrayList<BookRequest> requestArrayList;
    private ArrayList<BookRequest> pendingRequests;
    private ArrayList<BookRequest> acceptedRequests;
    private ArrayList<BookRequest> borrowedRequests;
    private DataBaseManager dataBaseManager;
    private String currentView = "pending";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_request_page);

        requestArrayList = new ArrayList<>();
        pendingRequests = new ArrayList<>();
        acceptedRequests = new ArrayList<>();
        borrowedRequests = new ArrayList<>();

        dataBaseManager = new DataBaseManager();

        Intent intent = getIntent();
        owner = (Owner) intent.getSerializableExtra("user");

        dataBaseManager.OwnerRequestPageRequestSnapShotListener(this, owner.getUsername());

        final ListView requestListView = findViewById(R.id.owner_request_list);
        TextView me = findViewById(R.id.owner_request_me_tab);
        TextView books = findViewById(R.id.owner_request_books_tab);
        TextView pending = findViewById(R.id.owner_request_pending);
        TextView accepted = findViewById(R.id.owner_request_accepted);
        TextView borrowed = findViewById(R.id.owner_request_borrowed);

        requestListPending = new RequestList(this, pendingRequests, 0);
        requestListAccepted = new RequestList(this, acceptedRequests, 0);
        requestListBorrowed = new RequestList(this, borrowedRequests, 0);

        requestListView.setAdapter(requestListPending);
        requestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookRequest selectRequest = null;
                if (currentView.equals("pending")) {
                    selectRequest = pendingRequests.get(position);
                }else if (currentView.equals("accepted")){
                    selectRequest = acceptedRequests.get(position);
                }else if(currentView.equals("borrowed")){
                    selectRequest = borrowedRequests.get(position);
                }
                assert selectRequest != null;
                Book selectBook = selectRequest.getBook();
                Intent intent = new Intent(OwnerRequestPageActivity.this, SameBookRequestList.class);
                intent.putExtra("selectBook", selectBook);
                startActivity(intent);
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestListView.setAdapter(requestListPending);
                currentView = "pending";
            }
        });

        accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestListView.setAdapter(requestListAccepted);
                currentView = "accepted";
            }
        });

        borrowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestListView.setAdapter(requestListBorrowed);
                currentView = "borrowed";
            }
        });
    }

    public ArrayList<String> getOwnerRequestList(){
        return owner.getRequestList();
    }

    public void ownerAddRequest(String isbn){
        owner.addRequest(isbn);
    }

    public void ownerAddRequestArrayList(BookRequest bookRequest){
        this.requestArrayList.add(bookRequest);
    }


    public void getPending(){
        this.pendingRequests.clear();
        for (int i = 0; i < this.requestArrayList.size(); i++){
            if (requestArrayList.get(i).getStatus().equals("pending")){
                pendingRequests.add(requestArrayList.get(i));
            }
        }
        requestListPending.notifyDataSetChanged();
    }

    public void getAccepted(){
        this.acceptedRequests.clear();
        for (int i = 0; i < this.requestArrayList.size(); i++){
            if (requestArrayList.get(i).getStatus().equals("accepted")){
                acceptedRequests.add(requestArrayList.get(i));
            }
        }
        requestListAccepted.notifyDataSetChanged();
    }

    public void getBorrowed(){
        this.borrowedRequests.clear();
        for (int i = 0; i < this.requestArrayList.size(); i++){
            if (requestArrayList.get(i).getStatus().equals("borrowed")){
                borrowedRequests.add(requestArrayList.get(i));
            }
        }
        requestListBorrowed.notifyDataSetChanged();
    }


    public void clearList(){
        this.requestArrayList.clear();
        this.pendingRequests.clear();
        this.acceptedRequests.clear();
        this.borrowedRequests.clear();
    }

    @Override
    public void onAcceptPressed() {

    }

    @Override
    public void onDeclinePressed() {

    }
}