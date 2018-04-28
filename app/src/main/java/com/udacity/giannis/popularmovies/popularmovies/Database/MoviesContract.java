package com.udacity.giannis.popularmovies.popularmovies.Database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

/**
 * Created by giann on 4/23/2018.
 */

public final class MoviesContract {

    public static final String CONTENT_AUTHORITY="com.udacity.giannis.popularmovies.popularmovies";
    public static final Uri BASE_CONTENT_URI= Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_FAVORITES = "Favoriters";

    public MoviesContract() {
    }

    public static final class FavoritesEntry implements BaseColumns{
        public static final Uri CONTENT_URI=
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();


        public static final String TABLE_NAME="FAVORITES";

        public static final String COLUMN_ID = "Id";
        public static final String COLUMN_Title = "Title";
        public static final String COLUMN_Rating = "Rating";
        public static final String COLUMN_Release = "Release";
        public static final String COLUMN_Overview = "Overview";
        public static final String COLUMN_Thumbnail = "Thumbnail";

        public static Uri buildUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }
}
