package com.udacity.giannis.popularmovies.popularmovies;

/**
 * Created by giann on 4/20/2018.
 */

public interface onEventListener<T> {
    public void onSuccess(T object);
    public void onFailure(Exception e);
}
