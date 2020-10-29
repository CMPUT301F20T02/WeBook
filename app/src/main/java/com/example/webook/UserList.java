package com.example.webook;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class UserList extends ArrayAdapter<User> {
    private ArrayList<User> users;
    private Context context;

    public UserList(@NonNull Context context, @NonNull ArrayList<User> users) {
        super(context, 0, users);
    }
}
