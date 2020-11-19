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

    public RequestList(Context context, ArrayList<BookRequest> BookRequests) {
        super(context, 0, BookRequests);
        this.BookRequests = BookRequests;
        this.context = context;
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
        borrow_text.setText("Requested by " + BookRequest.getRequester().get(position));



        return view;
    }
}