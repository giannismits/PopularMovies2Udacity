package com.udacity.giannis.popularmovies.popularmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by giann on 4/22/2018.
 */

public class Reviews implements Parcelable {

    private String author;
    private String author_review;

    public Reviews(String author, String author_review) {
        this.author = author;
        this.author_review = author_review;
    }

    public Reviews(Parcel parcel){
        author = parcel.readString();
        author_review = parcel.readString();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor_review() {
        return author_review;
    }

    public void setAuthor_review(String author_review) {
        this.author_review = author_review;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static Creator<Reviews> CREATOR= new Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel parcel) {
            return new Reviews(parcel);
        }

        @Override
        public Reviews[] newArray(int i) {
            return new Reviews[i];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(author_review);
    }
}
