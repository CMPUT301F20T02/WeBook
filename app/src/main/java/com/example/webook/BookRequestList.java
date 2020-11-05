package com.example.webook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BookRequestList extends ArrayAdapter<BookRequest> {
    private ArrayList<BookRequest> BookRequests;
    private Context context;

    public BookRequestList(Context context, ArrayList<BookRequest> BookRequests) {
        super(context, 0, BookRequests);
        this.BookRequests = BookRequests;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.request_list_content, parent, false);
        }

        BookRequest BookRequest = BookRequests.get(position);

        TextView title_text = view.findViewById(R.id.book_title);
        title_text.setText(BookRequest.getBook().getTitle());
        TextView borrow_text = view.findViewById(R.id.book_author);
        borrow_text.setText(BookRequest.getRequester().get(position));


        return view;
    }
}