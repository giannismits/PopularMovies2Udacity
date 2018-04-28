package com.udacity.giannis.popularmovies.popularmovies.Utils;

import com.udacity.giannis.popularmovies.popularmovies.Model.Reviews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giann on 4/22/2018.
 */

public class ReviewsJsonUtils {

    public static List<Reviews> getStringInfoFromJson(String reviewsJsonString) throws JSONException {
        List<Reviews> reviewsArrayList = new ArrayList<>();
        JSONObject reviewsData = new JSONObject(reviewsJsonString);
        JSONArray results = reviewsData.getJSONArray("results");

        final int re = results.length();

        for (int i = 0; i < re; i++) {
            JSONObject idObj = results.getJSONObject(i);
            String author_name = idObj.getString("author");
            String author_review = idObj.getString("content");

            if (author_name != null) {
               Reviews reviews = new Reviews(author_name,author_review);
                reviewsArrayList.add(reviews);
            }
        }
        return reviewsArrayList;
    }
}
