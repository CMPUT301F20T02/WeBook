package com.example.webook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
/**
 * This is a class that keeps track of a list of BookRequests objects
 */
public class RequestList extends ArrayAdapter<BookRequest> {
    private ArrayList<BookRequest> BookRequests;
    private Context context;
    private ArrayList<String> borrower;
    private int mode;

    public RequestList(Context context, ArrayList<BookRequest> BookRequests, ArrayList<String> borrower, int mode) {
        super(context, 0, BookRequests);
        this.BookRequests = BookRequests;
        this.context = context;
        this.borrower = borrower;
        this.mode = mode;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;


        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.request_list_content, parent, false);
        }

        BookRequest BookRequest = BookRequests.get(position);

        ImageView img= (ImageView) view.findViewById(R.id.request_book_icon);
        img.setImageResource(R.drawable.book_icon);

        TextView title_text = view.findViewById(R.id.request_book_title);
        title_text.setText(BookRequest.getBook().getTitle());
        TextView borrow_text = view.findViewById(R.id.request_requesterORrequestee);
        String temp;
        if (mode == 0) {
            temp = "Requested by " + BookRequest.getRequester().get(position);
        }else if (mode == 1) {
            temp = "Requested by " + borrower.get(position);
        }else{
            temp = "";
        }
        borrow_text.setText(temp);

        return view;
    }
}