package com.example.webook;
/**
 * This activity is created by OwnerBookProfile when the owner clicks to view all the requests on a particular book
 * It will create a  OwnerAcceptDeclineFragment instance to handel the user request
 * Current issue: When book has no requests, the program will crash
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;

import java.util.ArrayList;

public class SameBookRequestList extends AppCompatActivity implements OwnerAcceptDeclineFragment.OnFragmentInteractionListener{
    private ListView sameBookRequestList;
    private RequestList bookAdapter;
    private static ArrayList<BookRequest> dataList;
    private static final String TAG = "Sample";
    private Book selectBook;
    private DataBaseManager dataBaseManager;
    private SuperSwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_same_book_list);
        sameBookRequestList = findViewById(R.id.same_book_request_list);
        dataList = new ArrayList<BookRequest>();
        bookAdapter = new RequestList(this, dataList, null, 0);
        sameBookRequestList.setAdapter(bookAdapter);
        final Intent intent = getIntent();
        selectBook = (Book) intent.getSerializableExtra("selectBook");
        dataBaseManager = new DataBaseManager();
//        final BookRequest newRequest = new BookRequest(selectBook, selectBook.getOwner(), "requester1", null, null);

        dataBaseManager.getSameBookRequest(selectBook.getISBN(), this);
        findViewById(R.id.loadingPanelRequest).setVisibility(View.GONE);


        sameBookRequestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Object selectRequest = sameBookRequestList.getItemAtPosition(position);
                OwnerAcceptDeclineFragment ownerAcceptDeclineFragment = OwnerAcceptDeclineFragment.newInstance((BookRequest) selectRequest, position);
                ownerAcceptDeclineFragment.show(getSupportFragmentManager(), "Accept or Decline");
            }
        });

        swipeRefreshLayout = (SuperSwipeRefreshLayout) findViewById(R.id.swipe_refresh_request);
        swipeRefreshLayout
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                sameBookRequestList = findViewById(R.id.same_book_request_list);
                                dataList = new ArrayList<BookRequest>();
                                bookAdapter = new RequestList(SameBookRequestList.this, dataList, null, 0);
                                sameBookRequestList.setAdapter(bookAdapter);
                                final Intent intent = getIntent();
                                selectBook = (Book) intent.getSerializableExtra("selectBook");
                                dataBaseManager = new DataBaseManager();
                                // final BookRequest newRequest = new BookRequest(selectBook, selectBook.getOwner(), "requester1", null, null);
                                dataBaseManager.getSameBookRequest(selectBook.getISBN(), SameBookRequestList.this);

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

    /**
     * Reset dataList
     */
    public void dataListClear(){
        dataList.clear();
    }

    /**
     * add requests to dataList
     * @param request
     */
    public void dataListAdd(BookRequest request){
        dataList.add(request);
    }

    /**
     * Update listView
     */
    public void bookAdapterChanged(){
        bookAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAcceptPressed(){
        finish();
    }

    /**
     * decline a request
     */
    @Override
    public void onDeclinePressed(){
        dataBaseManager.declinePressed(selectBook.getISBN(),this);

    }
}
