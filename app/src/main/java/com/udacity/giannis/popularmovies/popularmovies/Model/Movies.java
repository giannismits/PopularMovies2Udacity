package com.udacity.giannis.popularmovies.popularmovies.Model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by giann on 3/4/2018.
 */

public class Movies implements Parcelable{

private String movie_id;
private String title;
private String image;
private String users_rating;
private  String plot;
private String release;
private  String thumbnail;

    public Movies(String title,String image, String users_rating,String plot, String release,String thumbnail,String movie_id) {
    this.title=title;
    this.image=image;
    this.users_rating=users_rating;
    this.plot=plot;
    this.release=release;
    this.thumbnail=thumbnail;
    this.movie_id=movie_id;
    }

    private Movies(Parcel in){
        title = in.readString();
        image = in.readString();
        users_rating = in.readString();
        plot = in.readString();
        release = in.readString();
        thumbnail = in.readString();
        movie_id = in.readString();
    }




    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsers_rating() {
        return users_rating;
    }

    public void setUsers_rating(String users_rating) {
        this.users_rating = users_rating;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(image);
        parcel.writeString(users_rating);
        parcel.writeString(plot);
        parcel.writeString(release);
        parcel.writeString(thumbnail);
        parcel.writeString(movie_id);

    }
    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>(){

        @Override
        public Movies createFromParcel(Parcel parcel) {
            return new Movies(parcel);
        }

        @Override
        public Movies[] newArray(int i) {
            return new Movies[i];
        }
    };
}
