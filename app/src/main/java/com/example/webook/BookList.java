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

public class BookList extends ArrayAdapter<Book> {
    private ArrayList<Book> books;
    private Context context;

    public BookList(Context context, ArrayList<Book> books){
        super(context,0, books);
        this.books = books;
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.book_list_content, parent,false);
        }

        Book book = books.get(position);
        TextView name = view.findViewById(R.id.book_name);
        TextView author = view.findViewById(R.id.book_author);
        TextView status = view.findViewById(R.id.book_status);
        name.setText(book.getTitle());
        author.setText(book.getAuthor());
        status.setText(book.getStatus());
        return view;

    }
}
