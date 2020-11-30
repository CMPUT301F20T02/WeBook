package com.example.webook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class BorrowerRequestPageActivity extends AppCompatActivity {

    private Borrower borrower;
    private RequestList requestListPending;
    private RequestList requestListAccepted;
    private RequestList requestListBorrowed;
    private ArrayList<BookRequest> requestArrayList;
    private ArrayList<BookRequest> pendingRequests;
    private ArrayList<BookRequest> acceptedRequests;
    private ArrayList<BookRequest> borrowedRequests;
    private DataBaseManager dataBaseManager;
    private ArrayList<String> borrowedList;
    private ArrayList<String> acceptedList;
    private String currentView = "pending";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_request_page);

        requestArrayList = new ArrayList<>();
        pendingRequests = new ArrayList<>();
        acceptedRequests = new ArrayList<>();
        borrowedRequests = new ArrayList<>();

        dataBaseManager = new DataBaseManager();

        Intent intent = getIntent();
        borrower = (Borrower) intent.getSerializableExtra("user");

        final ListView requestListView = findViewById(R.id.borrower_request_list);
        TextView me = findViewById(R.id.borrower_me_tab);
        TextView books = findViewById(R.id.borrower_books_tab);
        TextView pending = findViewById(R.id.borrower_request_requested);
        TextView accepted = findViewById(R.id.borrower_request_accepted);
        TextView borrowed = findViewById(R.id.borrower_request_borrowed);
        borrowedList = new ArrayList<>();
        acceptedList = new ArrayList<>();

        requestListPending = new RequestList(this, pendingRequests, null, 2);
        requestListAccepted = new RequestList(this, acceptedRequests, acceptedList, 2);
        requestListBorrowed = new RequestList(this, borrowedRequests, borrowedList, 2);
        dataBaseManager.BorrowerRequestPageRequestSnapShotListener(this, borrower.getUsername());

        requestListView.setAdapter(requestListPending);
        requestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookRequest selectRequest = null;
                if (currentView.equals("accepted")){
                    selectRequest = acceptedRequests.get(position);
                    Intent intent = new Intent(BorrowerRequestPageActivity.this, BorrowerRequestDelivery.class);
                    intent.putExtra("request", selectRequest);
                    startActivity(intent);

                }else if(currentView.equals("borrowed")){
                    selectRequest = borrowedRequests.get(position);
                    Intent intent = new Intent(BorrowerRequestPageActivity.this, BorrowerReturn.class);
                    intent.putExtra("request", selectRequest);
                    startActivity(intent);
                }

            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestListView.setAdapter(requestListPending);
                currentView = "pending";
                System.out.println("pending 2 size"+pendingRequests.size());
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

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });

        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BorrowerRequestPageActivity.this, BorrowerProfileActivity.class);
                intent.putExtra("user", borrower);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        });

    }

    public ArrayList<String> getOwnerRequestList(){
        return borrower.getRequestList();
    }

    public void ownerAddRequest(String isbn){
        borrower.addRequest(isbn);
    }



    public void getPending(){
        this.pendingRequests.clear();
        for (int i = 0; i < this.requestArrayList.size(); i++){
            if (requestArrayList.get(i).getBook().getStatus().equals("requested")){
                pendingRequests.add(requestArrayList.get(i));
            }
        }
        requestListPending.notifyDataSetChanged();

    }

    public void getAccepted(){
        this.acceptedRequests.clear();
        this.acceptedList.clear();
        for (int i = 0; i < this.requestArrayList.size(); i++){
            if (requestArrayList.get(i).getBook().getStatus().equals("accepted")){
                acceptedRequests.add(requestArrayList.get(i));
                acceptedList.add(requestArrayList.get(i).getRequester().get(0));
            }
        }
        requestListAccepted.notifyDataSetChanged();

    }

    public void getBorrowed(){
        this.borrowedRequests.clear();
        this.borrowedList.clear();
        for (int i = 0; i < this.requestArrayList.size(); i++){
            if (requestArrayList.get(i).getBook().getStatus().equals("borrowed")){
                borrowedRequests.add(requestArrayList.get(i));
                borrowedList.add(requestArrayList.get(i).getRequester().get(0));
            }
        }
        requestListBorrowed.notifyDataSetChanged();

    }


    public void setArrayList(ArrayList<BookRequest> bookRequests){
        this.requestArrayList = bookRequests;
        System.out.println("whole size" + requestArrayList.size());
        getPending();
        getBorrowed();
        getAccepted();
    }
}