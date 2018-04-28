package com.udacity.giannis.popularmovies.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import com.udacity.giannis.popularmovies.popularmovies.Model.Trailers;
import com.udacity.giannis.popularmovies.popularmovies.R;

import java.util.List;

/**
 * Created by giann on 4/22/2018.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerHolderView> {

    private final Context context;
    private final List<Trailers> trailerList;

    public TrailersAdapter(@NonNull Context context, List<Trailers> objects) {
        this.trailerList = objects;
        this.context = context;

    }

    @Override
    public void onBindViewHolder(@NonNull TrailersAdapter.TrailerHolderView holder, int position) {
        String tr = trailerList.get(position).getVideo_key();
        holder.textView.setText("Trailer  " + position);
    }

    public class TrailerHolderView extends RecyclerView.ViewHolder {
        private final TextView textView;

        public TrailerHolderView(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.trailers_ex);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    Trailers trailers=trailerList.get(position);

                    Uri uri = Uri.parse("https://www.youtube.com/watch?v="+trailers.getVideo_key());
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public TrailersAdapter.TrailerHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_view, parent, false);
        return new TrailersAdapter.TrailerHolderView(view);
    }
    @Override
    public int getItemCount() {
        if (trailerList == null) {
            return 0;
        } else {
            return trailerList.size();
        }
    }

}
