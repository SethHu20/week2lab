package com.fit2099.week7lab;

import static com.fit2099.week7lab.MainActivity.MOVIE_LIST_KEY;
import static com.fit2099.week7lab.MainActivity.MOVIE_PREF;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AllMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_movies);
        SharedPreferences sp = getSharedPreferences(MOVIE_PREF, MODE_PRIVATE);
        String movieData = sp.getString(MOVIE_LIST_KEY, "");
        Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
        ArrayList<Movie> movieList = (ArrayList<Movie>) new Gson().fromJson(movieData, type);

        RecyclerView recyclerView = findViewById(R.id.all_movie_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MovieCardAdapter movieCardAdapter = new MovieCardAdapter(movieList);
        recyclerView.setAdapter(movieCardAdapter);
        movieCardAdapter.notifyDataSetChanged();
    }
}