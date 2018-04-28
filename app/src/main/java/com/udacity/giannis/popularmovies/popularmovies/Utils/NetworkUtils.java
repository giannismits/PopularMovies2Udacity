package com.udacity.giannis.popularmovies.popularmovies.Utils;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by giann on 2/25/2018.
 */

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    //this method builds and returns the url.
    public static URL buildUrl(String cat)  {
        Uri uri= Uri.parse(MoviesConstParams.BASE_MOVIES_URL).buildUpon().appendEncodedPath(cat).appendQueryParameter(MoviesConstParams.API_KEY,MoviesConstParams.API_KEY_NUMBER).build();

        URL buildNewUrl=null;
        try {
            buildNewUrl= new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG,"Build Url: "+ buildNewUrl);
        return buildNewUrl;
    }
//this method return the JSON from the server.
    public static String getDataFromServer (URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String dataToSend = new String();
        try {
            connection.connect();
            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                dataToSend+= scanner.next();
            } else {
                return null;
            }
        }finally {
            connection.disconnect();
        }
        return dataToSend;
    }
}
