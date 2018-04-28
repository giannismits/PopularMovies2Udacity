package com.udacity.giannis.popularmovies.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.giannis.popularmovies.popularmovies.Model.Movies;
import com.udacity.giannis.popularmovies.popularmovies.MoviesDetailActivity;
import com.udacity.giannis.popularmovies.popularmovies.R;

import java.util.List;

/**
 * Created by giann on 3/5/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesHolderView> {
    protected final String BASE_POSTER_URL ="http://image.tmdb.org/t/p/w342/";
    private final Context context;
    private final List<Movies> movieList;

    public MoviesAdapter(@NonNull Context context, List<Movies> objects) {
        this.movieList=objects;
        this.context=context;

    }

    public class MoviesHolderView extends RecyclerView.ViewHolder{
      private final  ImageView imageView;
        public MoviesHolderView(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (position!=RecyclerView.NO_POSITION){
                        Movies movies = movieList.get(position);

                        Intent intent = new Intent(context,MoviesDetailActivity.class);
                       intent.putExtra("MOVIES",movies);

                        if (intent.resolveActivity(context.getPackageManager()) != null) {
                            context.startActivity(intent);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MoviesAdapter.MoviesHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_layout,parent,false);
        return new MoviesHolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MoviesHolderView holder, int position) {
    String picture = BASE_POSTER_URL+movieList.get(position).getImage();
        Picasso.with(context).load(picture).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (movieList==null){
            return 0;
        }else{
            return movieList.size();
        }
    }


}
