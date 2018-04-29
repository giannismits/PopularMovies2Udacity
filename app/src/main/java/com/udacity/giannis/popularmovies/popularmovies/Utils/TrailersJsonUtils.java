package com.udacity.giannis.popularmovies.popularmovies.Utils;

import com.udacity.giannis.popularmovies.popularmovies.Model.Movies;
import com.udacity.giannis.popularmovies.popularmovies.Model.Trailers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giann on 4/22/2018.
 */

public class TrailersJsonUtils {

    public static List<Trailers> getStringInfoFromJson(String trailerJsonString) throws JSONException {
        List<Trailers> trailersArrayList = new ArrayList<>();
        if (trailerJsonString != null) {
        JSONObject trailersData = new JSONObject(trailerJsonString);
        JSONArray results = trailersData.getJSONArray("results");


            final int re = results.length();

            for (int i = 0; i < re; i++) {
                JSONObject idObj = results.getJSONObject(i);
                String videoKey = idObj.getString("key");

                if (videoKey != null) {
                    Trailers trailers = new Trailers(videoKey);
                    trailersArrayList.add(trailers);
                }
            }

        }
        return trailersArrayList;
    }
}
