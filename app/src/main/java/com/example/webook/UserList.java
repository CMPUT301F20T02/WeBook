
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
 * This is a class that keeps track of a list of user objects
 */
public class UserList extends ArrayAdapter<User> {
    private ArrayList<User> users;
    private Context context;

    public UserList(Context context, ArrayList<User> users){
        super(context,0, users);
        this.users = users;
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.user_list_content, parent,false);
        }

        User user = users.get(position);
        TextView email = view.findViewById(R.id.list_user_email);
        TextView userName = view.findViewById(R.id.list_user_username);
        TextView phoneNumber = view.findViewById(R.id.list_user_phone);
        ImageView image = view.findViewById(R.id.list_user_icon);
        email.setText(user.getEmail());
        phoneNumber.setText(user.getPhoneNumber());
        userName.setText(user.getUsername());
        image.setImageDrawable(context.getResources().getDrawable(R.drawable.empty_user_icon));
        return view;

    }

}