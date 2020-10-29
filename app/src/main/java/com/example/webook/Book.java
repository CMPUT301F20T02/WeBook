package com.example.webook;

import android.graphics.drawable.Drawable;
import android.media.Image;

public class Book {
    private String title;
    private String ISBN;
    private String author;
    private String status;
    private String owner;
    private Drawable image;
    private String description;

    public Book(){}

    public Book(String title, String ISBN, String author, String status, String owner, Drawable image, String description) {
        this.title = title;
        this.ISBN = ISBN;
        this.author = author;
        this.status = status;
        this.owner = owner;
        this.image = image;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
