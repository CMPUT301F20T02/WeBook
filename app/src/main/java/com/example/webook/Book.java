package com.example.webook;

import android.graphics.drawable.Drawable;
import android.media.Image;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String ISBN;
    private String author;
    private String status;
    private String owner;
    private String image;
    private String description;
    private String borrower;
    public Book(){}

    public Book(String title, String ISBN, String author, String status, String owner, String image, String description, String borrower) {
        this.title = title;
        this.ISBN = ISBN;
        this.author = author;
        this.status = status;
        this.owner = owner;
        this.image = image;
        this.description = description;
        this.borrower = borrower;
    }
    public Book(String title, String ISBN, String author, String status, String owner, String image, String description) {
        this.title = title;
        this.ISBN = ISBN;
        this.author = author;
        this.status = status;
        this.owner = owner;
        this.image = image;
        this.description = description;
        this.borrower = "No borrower recently";
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getTitle() {
        return title;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public String getStatus() {
        return status;
    }

    public String getOwner() {
        return owner;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getBorrower() {
        return borrower;
    }
}
