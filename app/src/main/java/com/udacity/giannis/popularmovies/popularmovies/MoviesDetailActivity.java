package com.udacity.giannis.popularmovies.popularmovies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.giannis.popularmovies.popularmovies.Adapters.MoviesAdapter;
import com.udacity.giannis.popularmovies.popularmovies.Adapters.ReviewsAdapter;
import com.udacity.giannis.popularmovies.popularmovies.Adapters.TrailersAdapter;
import com.udacity.giannis.popularmovies.popularmovies.Database.MoviesContract;
import com.udacity.giannis.popularmovies.popularmovies.Database.MoviesDbHelper;
import com.udacity.giannis.popularmovies.popularmovies.Model.Movies;
import com.udacity.giannis.popularmovies.popularmovies.Model.Reviews;
import com.udacity.giannis.popularmovies.popularmovies.Model.Trailers;
import com.udacity.giannis.popularmovies.popularmovies.Utils.GetDataFromServer;
import com.udacity.giannis.popularmovies.popularmovies.Utils.MoviesJsonUtils;
import com.udacity.giannis.popularmovies.popularmovies.Utils.NetworkUtils;
import com.udacity.giannis.popularmovies.popularmovies.Utils.ReviewsJsonUtils;
import com.udacity.giannis.popularmovies.popularmovies.Utils.TrailersJsonUtils;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by giann on 3/26/2018.
 */

public class MoviesDetailActivity extends AppCompatActivity {

    protected final String BASE_THUMBNAIL_URL ="http://image.tmdb.org/t/p/w342/";
    private Movies movies;
    private List<Trailers> trailersDataFromJson = new ArrayList<>();
    private List<Reviews> reviewssDataFromJson = new ArrayList<>();
    private RecyclerView rv,lv;
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;
    private ImageButton mButton;
    private Cursor cursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_details);

        ImageView mImageView = (ImageView) findViewById(R.id.image_detail);
        TextView mOriginalTitle= (TextView) findViewById(R.id.original);
        TextView mUser= (TextView) findViewById(R.id.users_rating);
        TextView mRelease= (TextView) findViewById(R.id.release_date);
        TextView mPlot= (TextView) findViewById(R.id.plot_synopsis);
        final ImageButton mButton = (ImageButton) findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite()){
                    deleteFromFavorites();
                }
                else{
                    addToFavorates();
                }
            if (cursor!=null){
                    cursor.close();
            }
            }
        });

      movies = getIntent().getParcelableExtra("MOVIES");
        if (movies==null){
            return;
    }
    //setting the data to the UI.
            mUser.setText(movies.getUsers_rating());
            mOriginalTitle.setText(movies.getTitle());
            mRelease.setText(movies.getRelease());
            mPlot.setText(movies.getPlot());
            Picasso.with(this).load(BASE_THUMBNAIL_URL+movies.getThumbnail()).into(mImageView);

            //youtube Videos.

        String youtubeInfo = movies.getMovie_id()+"/videos";
        URL correctUrl = NetworkUtils.buildUrl(youtubeInfo.toString());
        GetDataFromServer getDataFromServer = new GetDataFromServer(getApplicationContext(), new onEventListener<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    trailersDataFromJson = TrailersJsonUtils.getStringInfoFromJson(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (trailersDataFromJson != null) {
                    rv =  findViewById(R.id.recycle_trailers);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rv.setLayoutManager(linearLayoutManager);
                    rv.setHasFixedSize(true);
                    //set the adapter
                    trailersAdapter = new TrailersAdapter(getApplicationContext(), trailersDataFromJson );
                    rv.setAdapter(trailersAdapter);
                }else {
                    setContentView(R.layout.fragmant_layout);
                }
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getApplicationContext(), "Error while transfering data", Toast.LENGTH_SHORT).show();
            }
        });
        getDataFromServer.execute(correctUrl.toString());

        //Reviews
        String reviewInfo = movies.getMovie_id() +"/reviews";
        URL reviewsUrl = NetworkUtils.buildUrl(reviewInfo.toString());
        GetDataFromServer getDataFromServer1 = new GetDataFromServer(getApplicationContext(), new onEventListener<String>() {

            @Override
            public void onSuccess(String result) {
                try {
                    reviewssDataFromJson = ReviewsJsonUtils.getStringInfoFromJson(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (reviewssDataFromJson != null) {
                    lv =  findViewById(R.id.recycle_reviews);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    lv.setLayoutManager(linearLayoutManager);
                    lv.setHasFixedSize(true);
                    //set the adapter
                    reviewsAdapter = new ReviewsAdapter(getApplicationContext(), reviewssDataFromJson);
                    lv.setAdapter(reviewsAdapter);
                }
            }
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getApplicationContext(), "Error while transfering data", Toast.LENGTH_SHORT).show();
            }
        });
        getDataFromServer1.execute(reviewsUrl.toString());
    }
    private boolean isFavorite(){
    MoviesDbHelper moviesDbHelper = new MoviesDbHelper(this);
        int movid= Integer.parseInt( movies.getMovie_id());
      cursor=  moviesDbHelper.getMatchData(movid);
     return  (cursor!=null)&&(cursor.getCount()>0);

    }
    private void addToFavorates(){
        Uri uri = MoviesContract.FavoritesEntry.CONTENT_URI;
        ContentResolver cv = this.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.clear();

        contentValues.put(MoviesContract.FavoritesEntry.COLUMN_ID,movies.getMovie_id());
        contentValues.put(MoviesContract.FavoritesEntry.COLUMN_Rating,movies.getUsers_rating());
        contentValues.put(MoviesContract.FavoritesEntry.COLUMN_Title,movies.getTitle());
        contentValues.put(MoviesContract.FavoritesEntry.COLUMN_Release,movies.getRelease());
        contentValues.put(MoviesContract.FavoritesEntry.COLUMN_Overview,movies.getPlot());
        contentValues.put(MoviesContract.FavoritesEntry.COLUMN_Thumbnail,movies.getThumbnail());

        cv.insert(uri,contentValues);

        Toast.makeText(this,"added to favorites",Toast.LENGTH_SHORT).show();
    }

    private void deleteFromFavorites(){
        Uri uri = MoviesContract.FavoritesEntry.CONTENT_URI;
        ContentResolver cv = this.getContentResolver();
        String select = MoviesContract.FavoritesEntry.COLUMN_ID + "=?";

        String[] selectionArg = {movies.getMovie_id()};

        cv.delete(uri,select,selectionArg);

        Toast.makeText(this,"removed from favorites.",Toast.LENGTH_SHORT).show();
    }
}
