package com.udacity.giannis.popularmovies.popularmovies.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by giann on 4/23/2018.
 */

public class MoviesDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    static final String DATABASE_NAME="movies.db";

    private static MoviesDbHelper mInstance=null;
    public static MoviesDbHelper getInstance(Context context){
        if (mInstance==null){
            mInstance=new MoviesDbHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE =

                "CREATE TABLE " + MoviesContract.FavoritesEntry.TABLE_NAME + " (" +
                        MoviesContract.FavoritesEntry.COLUMN_ID + " INTEGER NOT NULL, " +
                        MoviesContract.FavoritesEntry.COLUMN_Title + " TEXT NOT NULL, " +
                        MoviesContract.FavoritesEntry.COLUMN_Overview + " TEXT NOT NULL, " +
                        MoviesContract.FavoritesEntry.COLUMN_Release + " TEXT NOT NULL, " +
                        MoviesContract.FavoritesEntry.COLUMN_Thumbnail + " TEXT NOT NULL, " +
                        MoviesContract.FavoritesEntry.COLUMN_Rating + " REAL NOT NULL " +
                        "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.FavoritesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public Cursor getMatchData(int id){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from favorites where Id="+id+"",null);
        return res;
    }
}
