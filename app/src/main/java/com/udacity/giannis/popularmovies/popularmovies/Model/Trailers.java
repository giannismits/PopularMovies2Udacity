package com.udacity.giannis.popularmovies.popularmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by giann on 4/18/2018.
 */

public class Trailers implements Parcelable {

    private String video_key;

    public Trailers(String video_key) {
        this.video_key = video_key;
    }

    public String getVideo_key() {
        return video_key;
    }

    public void setVideo_key(String video_key) {
        this.video_key = video_key;
    }

    protected Trailers(Parcel in) {
        video_key=in.readString();
    }

    public static final Creator<Trailers> CREATOR = new Creator<Trailers>() {
        @Override
        public Trailers createFromParcel(Parcel in) {
            return new Trailers(in);
        }

        @Override
        public Trailers[] newArray(int size) {
            return new Trailers[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(video_key);
    }
}
