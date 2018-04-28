package com.udacity.giannis.popularmovies.popularmovies.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.giannis.popularmovies.popularmovies.Model.Reviews;
import com.udacity.giannis.popularmovies.popularmovies.R;

import java.util.List;

/**
 * Created by giann on 4/22/2018.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsHolderView> {

private final Context context;
private final List<Reviews> reviewsList;

public ReviewsAdapter(@NonNull Context context, List<Reviews> objects) {
        this.reviewsList = objects;
        this.context = context;

        }

@Override
public void onBindViewHolder(@NonNull ReviewsAdapter.ReviewsHolderView holder, int position) {
        String authorn = reviewsList.get(position).getAuthor();
        String reviewa = reviewsList.get(position).getAuthor_review();
        holder.author.setText(authorn);
        holder.author_review.setText(reviewa);
        }

public class ReviewsHolderView extends RecyclerView.ViewHolder {
    private final TextView author,author_review;

    public ReviewsHolderView(View itemView) {
        super(itemView);
        author = (TextView) itemView.findViewById(R.id.author_name);
        author_review = (TextView) itemView.findViewById(R.id.review_author);


    }
}

    @NonNull
    @Override
    public ReviewsAdapter.ReviewsHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_list_view, parent, false);
        return new ReviewsAdapter.ReviewsHolderView(view);
    }
    @Override
    public int getItemCount() {
        if (reviewsList == null) {
            return 0;
        } else {
            return reviewsList.size();
        }
    }
}
