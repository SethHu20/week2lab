package com.fit2099.week7lab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieCardAdapter extends RecyclerView.Adapter<MovieCardAdapter.ViewHolder> {
    ArrayList<Movie> movieArrayList = new ArrayList();

    public MovieCardAdapter(ArrayList<Movie> movieArrayList) {
        this.movieArrayList = movieArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.movie_card, parent, false
        );
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movieArrayList.get(position);
        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getYear() + "");
        holder.country.setText(movie.getCountry());
        holder.genre.setText(movie.getGenre());
        holder.cost.setText(movie.getCost() * 1.1 + "");
        holder.keywords.setText(movie.getKeywords());
        holder.actors.setText(movie.getActors() + "");
        holder.tax.setText(movie.getCost() * 0.1 + "");
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView year;
        public TextView country;
        public TextView genre;
        public TextView cost;
        public TextView keywords;
        public TextView actors;
        public TextView tax;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_title);
            year = itemView.findViewById(R.id.card_year);
            country = itemView.findViewById(R.id.card_country);
            genre = itemView.findViewById(R.id.card_genre);
            cost = itemView.findViewById(R.id.card_cost);
            keywords = itemView.findViewById(R.id.card_keywords);
            actors = itemView.findViewById(R.id.card_actors);
            tax = itemView.findViewById(R.id.card_tax);
        }
    }
}