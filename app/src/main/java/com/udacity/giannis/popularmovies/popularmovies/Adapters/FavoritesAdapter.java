package com.udacity.giannis.popularmovies.popularmovies.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.udacity.giannis.popularmovies.popularmovies.Model.Movies;
import com.udacity.giannis.popularmovies.popularmovies.R;

import java.util.List;

/**
 * Created by giann on 4/27/2018.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesHolderView> {

    private final Context context;
    private final List<Movies> favoritesList;

    public FavoritesAdapter(@NonNull Context context, List<Movies> objects) {
        this.favoritesList = objects;
        this.context = context;
    }
    public class FavoritesHolderView extends RecyclerView.ViewHolder {
        TextView title,rel,rating;
        public FavoritesHolderView(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.movie_title);
            rel = (TextView) itemView.findViewById(R.id.release_d);
            rating = (TextView) itemView.findViewById(R.id.users_r);
        }
    }
    @NonNull
    @Override
    public FavoritesHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_list, parent, false);
        return new FavoritesAdapter.FavoritesHolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesHolderView holder, int position) {
        String titl = favoritesList.get(position).getTitle();
        String re = favoritesList.get(position).getRelease();
        String rat = favoritesList.get(position).getUsers_rating();
        holder.title.setText(titl);
        holder.rel.setText(re);
        holder.rating.setText(rat);
    }

    @Override
    public int getItemCount() {
        if (favoritesList == null) {
            return 0;
        } else {
            return favoritesList.size();
        }
    }

}