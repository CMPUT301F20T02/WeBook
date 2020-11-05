package com.example.webook;
<<<<<<< HEAD

import android.content.Context;
=======
import android.content.Context;
import android.graphics.drawable.Drawable;
>>>>>>> origin/txia
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

<<<<<<< HEAD
=======
import com.bumptech.glide.Glide;

>>>>>>> origin/txia
import java.util.ArrayList;

public class BookList extends ArrayAdapter<Book> {
    private ArrayList<Book> books;
    private Context context;

<<<<<<< HEAD
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
        TextView title = view.findViewById(R.id.book_title);
        TextView author = view.findViewById(R.id.book_author);
        TextView status = view.findViewById(R.id.book_status);
        ImageView icon = view.findViewById(R.id.book_icon);
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
=======
    public BookList(@NonNull Context context, ArrayList<Book> books){
        super(context, 0, books);
        this.books = books;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.book_list_content, parent, false);
        }

        Book book = books.get(position);

        ImageView icon = view.findViewById(R.id.book_icon);
        TextView title = view.findViewById(R.id.book_title);
        TextView author = view.findViewById(R.id.book_author);
        TextView status = view.findViewById(R.id.book_status);


        title.setText(book.getTitle());
        author.setText((book.getAuthor()));
>>>>>>> origin/txia
        status.setText(book.getStatus());

        if (book.getImage() == null){
            icon.setImageResource(R.drawable.book_icon);
        }else{
            Glide.with(this.context)
                    .load(book.getImage())
                    .into(icon);
        }

<<<<<<< HEAD
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        status.setText(book.getStatus());
        return view;

=======
        return view;
>>>>>>> origin/txia
    }

}
