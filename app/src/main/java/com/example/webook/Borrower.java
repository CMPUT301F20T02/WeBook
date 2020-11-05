package com.example.webook;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Borrower extends User {

    public Borrower(){}

    public Borrower(String username, String email, String phoneNumber, String pwd, String description, String image) {
        super(username, email, phoneNumber, pwd, description, image);
        this.setUserType("borrower");
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> origin/txia
