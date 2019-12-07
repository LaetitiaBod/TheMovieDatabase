package com.example.filmdatabase.view;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filmdatabase.R;
import com.example.filmdatabase.model.Movie;
import com.squareup.picasso.Picasso;
import java.text.*;


public class FilmDatabaseAdapter extends RecyclerView.Adapter<FilmDatabaseAdapter.ViewHolder> {

    public static final String CLE_DONNEES_ID_FILM = "idFilm";
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w342";

    private final List<Movie> tosee;
    private List<Movie> favoris;
    private List<Movie> values;
    private Context context;
    private final OnFavoriteClickListener listener;
    private final OnToSeeClickListener listenerTS;

    public interface OnToSeeClickListener {
        void onToSeeAdded(Movie item);
        void onToSeeRemove(Movie item);
    }
    public interface OnFavoriteClickListener {
        void onFavoriteAdded(Movie item);
        void onFavoriteRemove(Movie item);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public TextView txtLower;
        public ImageView imgView;
        public View layout;
        public ImageView imgFavoriteView;
        public ImageView imgToSeeView;
        public int cptT = 0;
        public ViewHolder(final View v) {
            super(v);
            layout = v;
            txtHeader = v.findViewById(R.id.firstLine);
            txtFooter = v.findViewById(R.id.secondLine);
            txtLower = v.findViewById(R.id.thirdLine);
            imgView = v.findViewById(R.id.icon);
            imgFavoriteView = v.findViewById(R.id.addFavorite);
            imgToSeeView = v.findViewById(R.id.addTosee);

        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public FilmDatabaseAdapter(List<Movie> myDataset, List<Movie> fl, List<Movie> tsl, Context context, OnFavoriteClickListener listener, OnToSeeClickListener listenerTS) {

        this.context = context;
        values = myDataset;
        favoris = fl;
        tosee = tsl;
        this.listener = listener;
        this.listenerTS = listenerTS;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FilmDatabaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Movie currentFilm = values.get(position);

        holder.txtHeader.setText(currentFilm.getTitle());
        holder.txtHeader.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent randomIntent = new Intent(context, DetailActivity.class);
            randomIntent.putExtra(CLE_DONNEES_ID_FILM, currentFilm.getId());
            context.startActivity(randomIntent);
        });

        DecimalFormat df = new DecimalFormat("#.##");
        if(currentFilm.getVote_average() <= 0) {
            holder.txtFooter.setText("☆☆☆☆☆   "+df.format(currentFilm.getVote_average()));
        } else if(currentFilm.getVote_average() < 2) {
            holder.txtFooter.setText("★☆☆☆☆   "+df.format(currentFilm.getVote_average()));
        } else if(currentFilm.getVote_average() < 4) {
            holder.txtFooter.setText("★★☆☆☆   "+df.format(currentFilm.getVote_average()));
        } else if(currentFilm.getVote_average() < 6) {
            holder.txtFooter.setText("★★★☆☆   "+df.format(currentFilm.getVote_average()));
        } else if(currentFilm.getVote_average() < 8) {
            holder.txtFooter.setText("★★★★☆   "+df.format(currentFilm.getVote_average()));
        } else if(currentFilm.getVote_average() <= 10) {
            holder.txtFooter.setText("★★★★★   " + df.format(currentFilm.getVote_average()));
        } else {
            holder.txtFooter.setText("☆☆☆☆☆   "+df.format(currentFilm.getVote_average()));
        }

        holder.txtLower.setText(currentFilm.getRelease_date());
        if(currentFilm.getPoster_path() != null) {
            Picasso.with(context).load(BASE_URL_IMG+currentFilm.getPoster_path()).into(holder.imgView);
        }

        if(favoris != null) {
            for (int i = 0; i < favoris.size(); i++) {
                if (favoris.get(i).getId() == currentFilm.getId()) {
                    currentFilm.setIs_favorite(true);
                    holder.imgFavoriteView.setImageResource(R.drawable.favorite_validate);
                }
            }
        }

        holder.imgFavoriteView.setOnClickListener(v -> {
            if(currentFilm.getIs_favorite()) {
                holder.imgFavoriteView.setImageResource(R.drawable.favorites);
                Toast.makeText(v.getContext(), currentFilm.getTitle()+" deleted from favorites", Toast.LENGTH_SHORT).show();
                listener.onFavoriteRemove(currentFilm);
            } else {
                holder.imgFavoriteView.setImageResource(R.drawable.favorite_validate);
                Toast.makeText(v.getContext(), currentFilm.getTitle()+" added to favorites", Toast.LENGTH_SHORT).show();
                listener.onFavoriteAdded(currentFilm);
            }
        });

        if(tosee != null) {
            for (int i = 0; i < tosee.size(); i++) {
                if (tosee.get(i).getId() == currentFilm.getId()) {
                    currentFilm.setIs_to_see(true);
                    holder.imgToSeeView.setImageResource(R.drawable.checked);
                }
            }
        }
        holder.imgToSeeView.setOnClickListener(v -> {
            if(currentFilm.getIs_to_see()) {
                holder.imgToSeeView.setImageResource(R.drawable.eye);
                Toast.makeText(v.getContext(), currentFilm.getTitle()+" deleted from movies to see", Toast.LENGTH_SHORT).show();
                listenerTS.onToSeeRemove(currentFilm);
            } else {
                holder.imgToSeeView.setImageResource(R.drawable.checked);
                Toast.makeText(v.getContext(), currentFilm.getTitle()+" added to movies to see", Toast.LENGTH_SHORT).show();
                listenerTS.onToSeeAdded(currentFilm);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(!values.isEmpty()) {
            return values.size();
        } else {
            return 0;
        }
    }

}
