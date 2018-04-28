package com.udacity.giannis.popularmovies.popularmovies.Database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.Switch;

import com.udacity.giannis.popularmovies.popularmovies.Model.Movies;

/**
 * Created by giann on 4/26/2018.
 */

public class MoviesProvider extends ContentProvider {

    private MoviesDbHelper moviesDbHelper;
    private Context mContext;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    static final int FAVORITES=100;

    static UriMatcher buildUriMatcher(){
        final UriMatcher matcher= new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MoviesContract.CONTENT_AUTHORITY;

        matcher.addURI(authority,MoviesContract.PATH_FAVORITES,FAVORITES);
        return matcher;
    }
    @Override
    public boolean onCreate() {
        moviesDbHelper = new MoviesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        SQLiteDatabase sqLiteDatabase = moviesDbHelper.getReadableDatabase();
        Cursor mCursor;
        try {
            switch (sUriMatcher.match(uri)){
                case (FAVORITES): {
                    mCursor= sqLiteDatabase.query(
                            MoviesContract.FavoritesEntry.TABLE_NAME,
                            strings,
                            s,
                            strings1,
                            null,
                            null,
                            s1
                            );
                    return mCursor;
                }
                default: throw new UnsupportedOperationException("unknown Uri" + uri);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            moviesDbHelper.close();
        }
      return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase sqLiteDatabase = moviesDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        Uri uriToReturn;

        try {
            switch (match){
                case   FAVORITES:{
                    long id =sqLiteDatabase.insert(MoviesContract.FavoritesEntry.TABLE_NAME,null,contentValues);
                    if (id>0){
                        uriToReturn = MoviesContract.FavoritesEntry.buildUri(id);
                    }else {
                        throw new android.database.SQLException("Error while insering a row in : " +uri);
                    }break;
                }default: throw  new UnsupportedOperationException("unknown uri" + uri);
            }mContext.getContentResolver().notifyChange(uri,null);
            return uriToReturn;
        }catch (Exception e){
            e.printStackTrace();
            sqLiteDatabase.close();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        final int matxh=sUriMatcher.match(uri);
        int moviesDeleted;

        try (SQLiteDatabase sqLiteDatabase = moviesDbHelper.getWritableDatabase()) {
            switch (matxh) {
                case FAVORITES: {
                    moviesDeleted = sqLiteDatabase.delete(MoviesContract.FavoritesEntry.TABLE_NAME, s, strings);
                    break;
                }
                default:
                    throw new UnsupportedOperationException("unknown uri" + uri);
            }
            if (moviesDeleted != 0) {
                mContext.getContentResolver().notifyChange(uri, null);
            }
            return moviesDeleted;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
