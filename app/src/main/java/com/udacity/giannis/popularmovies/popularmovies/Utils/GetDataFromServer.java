package com.udacity.giannis.popularmovies.popularmovies.Utils;

import android.content.Context;
import android.os.AsyncTask;

import com.udacity.giannis.popularmovies.popularmovies.onEventListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by giann on 4/18/2018.
 */

public class GetDataFromServer extends AsyncTask<String, Void, String> {

    private onEventListener<String> mCallback;
    private Context mContext;
    private Exception exception;

    public GetDataFromServer( Context mContext,onEventListener mCallback) {
        this.mCallback = mCallback;
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        String apiLink = strings[0];
        try {
            URL url = new URL(apiLink);
            String httpDataResponce = NetworkUtils.getDataFromServer(url);

            return httpDataResponce;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
       if (mCallback!=null){
           mCallback.onSuccess(s);
       }else{
           mCallback.onFailure(exception);
       }
    }
}
