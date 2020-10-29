package com.example.webook;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Borrower extends User {

    public Borrower(String username, String email, String phoneNumber, String pwd, Bitmap image) {
        super(username, email, phoneNumber, pwd, image);
        this.setUserType("borrower");
    }
}
