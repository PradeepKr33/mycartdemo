package com.example.mycart.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class BookItem implements Parcelable {

    private String bookName;
    private int bookImage;
    private double bookPrice;
    private String bookDetails;

    public String getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(String bookDetails) {
        this.bookDetails = bookDetails;
    }

    public BookItem(String bookName, int bookImage, double bookPrice, String bookDetails) {
        this.bookName = bookName;
        this.bookImage = bookImage;
        this.bookPrice = bookPrice;
        this.bookDetails = bookDetails;
    }

    protected BookItem(Parcel in) {
        bookName = in.readString();
        bookDetails = in.readString();
        bookImage = in.readInt();
        bookPrice = in.readDouble();
    }

    public static final Creator<BookItem> CREATOR = new Creator<BookItem>() {
        @Override
        public BookItem createFromParcel(Parcel in) {
            return new BookItem(in);
        }

        @Override
        public BookItem[] newArray(int size) {
            return new BookItem[size];
        }
    };

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookImage() {
        return bookImage;
    }

    public void setBookImage(int bookImage) {
        this.bookImage = bookImage;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(bookName);
        dest.writeString(bookDetails);
        dest.writeInt(bookImage);
        dest.writeDouble(bookPrice);
    }
}
