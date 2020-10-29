
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
        TextView email = view.findViewById(R.id.Email);
        TextView userName = view.findViewById(R.id.User_name);
        TextView phoneNumber = view.findViewById(R.id.PhoneNumber);
        email.setText(user.getEmail());
        phoneNumber.setText(user.getPhoneNumber());
        userName.setText(user.getUsername());
        return view;

    }

}