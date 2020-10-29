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

public class BookList extends ArrayAdapter<Book> {
    private ArrayList<Book> books;
    private Context context;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.book_content, parent, false);
        }

        Book book = books.get(position);

        ImageView icon = view.findViewById(R.id.book_icon);
        TextView title = view.findViewById(R.id.book_title);
        TextView author = view.findViewById(R.id.book_author);
        TextView status = view.findViewById(R.id.book_status);

        icon.setImageDrawable(book.getImage());
        title.setText(book.getTitle());
        author.setText((book.getAuthor()));
        status.setText(book.getStatus());

        return view;

    }


    public BookList(@NonNull Context context, ArrayList<Book> books){
        super(context, 0, books);
        this.books = books;
        this.context = context;
    }
}
