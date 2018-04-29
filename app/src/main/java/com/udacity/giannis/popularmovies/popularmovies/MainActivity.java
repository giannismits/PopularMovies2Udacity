package com.udacity.giannis.popularmovies.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.udacity.giannis.popularmovies.popularmovies.Adapters.FavoritesAdapter;
import com.udacity.giannis.popularmovies.popularmovies.Adapters.MoviesAdapter;
import com.udacity.giannis.popularmovies.popularmovies.Database.MoviesContract;
import com.udacity.giannis.popularmovies.popularmovies.Database.MoviesDbHelper;
import com.udacity.giannis.popularmovies.popularmovies.Model.Movies;
import com.udacity.giannis.popularmovies.popularmovies.Utils.GetDataFromServer;
import com.udacity.giannis.popularmovies.popularmovies.Utils.MoviesJsonUtils;
import com.udacity.giannis.popularmovies.popularmovies.Utils.NetworkUtils;

import org.json.JSONException;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private RecyclerView gv,fv;
    private MoviesAdapter mMoviesAdapter;
    private FavoritesAdapter mFavoritesAdapter;
    private Cursor cursor;
   private List<Movies> moviesDataFromJson = new ArrayList<>();
   private List<Movies> favoritesDAta = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //call popular movies for main page.
        URL correctUrl = NetworkUtils.buildUrl("popular");
        //check if there is an internet connection
        if (checkConnectivity()){
            GetDataFromServer getDataFromServer = new GetDataFromServer(getApplicationContext(),new  onEventListener<String>(){
                @Override
                public void onSuccess(String result) {
                    try {
                        moviesDataFromJson = MoviesJsonUtils.getStringInfoFromJson(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (moviesDataFromJson != null) {
                        //call the method populateUi to set the data to moviesAdapter class.
                        populateUi(moviesDataFromJson);
                    }
                }
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getApplicationContext(), "Error while transfering data", Toast.LENGTH_SHORT).show();
                }
            });
            getDataFromServer.execute(correctUrl.toString());
        }else{
            setContentView(R.layout.fragmant_layout);
        }
    }

    //check connectivity method
    public  boolean checkConnectivity(){
        ConnectivityManager checkConnectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = checkConnectivity.getActiveNetworkInfo();
        return info !=null && info.isConnectedOrConnecting();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        GetDataFromServer getDataFromServer = new GetDataFromServer(getApplicationContext(),new  onEventListener<String>(){
            @Override
            public void onSuccess(String result) {
                try {
                    moviesDataFromJson = MoviesJsonUtils.getStringInfoFromJson(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (moviesDataFromJson != null) {
                    //call the method populateUi to set the data to moviesAdapter class.
                    populateUi(moviesDataFromJson);
                }else {
                    setContentView(R.layout.fragmant_layout);
                }
            }
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getApplicationContext(), "Error while transfering data", Toast.LENGTH_SHORT).show();
            }
        });
        if (id == (R.id.popular_id)) {
            URL correctUrl = NetworkUtils.buildUrl("popular");
            if (checkConnectivity()){
                setContentView(R.layout.activity_main);
                setTitle("Popular Movies");
                getDataFromServer.execute(correctUrl.toString());
            }else{
                setContentView(R.layout.fragmant_layout);
            }
            return true;
        } else if (id == (R.id.top_rated_id)) {
            URL correctUrl = NetworkUtils.buildUrl("top_rated");
            if (checkConnectivity()){
                setContentView(R.layout.activity_main);
                setTitle("Top Rated Movies");
                getDataFromServer.execute(correctUrl.toString());
            }else{
                setContentView(R.layout.fragmant_layout);
            }
            return true;
        }else if (id== R.id.favorites_id){
            setContentView(R.layout.activity_main);
            setTitle("Favorite Movies");
            String[] show = {
                    MoviesContract.FavoritesEntry.COLUMN_ID,
                    MoviesContract.FavoritesEntry.COLUMN_Rating,
                    MoviesContract.FavoritesEntry.COLUMN_Release,
                    MoviesContract.FavoritesEntry.COLUMN_Title,
                    MoviesContract.FavoritesEntry.COLUMN_Overview,
                    MoviesContract.FavoritesEntry.COLUMN_Thumbnail
            };
            MoviesDbHelper moviesDbHelper = new MoviesDbHelper(this);
             cursor =moviesDbHelper.getReadableDatabase().query(MoviesContract.FavoritesEntry.TABLE_NAME,show,null,null,null,null,null);
            if (cursor!=null && cursor.moveToFirst()){
                while (cursor.moveToNext()){
                    Movies movies = new Movies(
                            cursor.getString(cursor.getColumnIndex(MoviesContract.FavoritesEntry.COLUMN_Title)),
                            cursor.getString(cursor.getColumnIndex(MoviesContract.FavoritesEntry.COLUMN_Thumbnail)),
                            cursor.getString(cursor.getColumnIndex(MoviesContract.FavoritesEntry.COLUMN_Rating)),
                            cursor.getString(cursor.getColumnIndex(MoviesContract.FavoritesEntry.COLUMN_Overview)),
                            cursor.getString(cursor.getColumnIndex(MoviesContract.FavoritesEntry.COLUMN_Release)),
                            null,
                            cursor.getString(cursor.getColumnIndex(MoviesContract.FavoritesEntry.COLUMN_ID))
                    );
                    favoritesDAta.add(movies);
                }
                populateFavoritesUi(favoritesDAta);
            }if (cursor!=null){
                cursor.close();
            }
            moviesDbHelper.close();
        }
    return super.onOptionsItemSelected(item);
    }
    public void populateUi (final List list){
      //call the adapter class and pass context and also the List of movies objects
      gv =  findViewById(R.id.grid_view);
      GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
      gv.setLayoutManager(gridLayoutManager);
      gv.setHasFixedSize(true);
      //set the adapter
      mMoviesAdapter = new MoviesAdapter(this, list );
      gv.setAdapter(mMoviesAdapter);
  }
  public void populateFavoritesUi(List list){
      //call the adapter class and pass context and also the List of favorites movies objects
        fv = findViewById(R.id.grid_view);
      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
      fv.setLayoutManager(linearLayoutManager);
      fv.setHasFixedSize(true);
      //set the adapter
      mFavoritesAdapter = new FavoritesAdapter(this,list);
      fv.setAdapter(mFavoritesAdapter);
  }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}



