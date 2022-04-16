package com.fit2099.week7lab;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String MOVIE_LIST_KEY = "MOVIE_LIST_DB";
    public static final String MOVIE_PREF = "moviePref";
    private EditText titleInput, yearInput, countryInput, genreInput, costInput, keywordsInput, actorsInput;
    MyReceiver SMSReceiver = null;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ListView movieListView;
    ArrayAdapter movieListViewAdapter;
    public ArrayList<Movie> movieList;
    ArrayList<Movie> movieList2;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);

        titleInput = findViewById(R.id.titleInput);
        yearInput = findViewById(R.id.yearInput);
        countryInput = findViewById(R.id.countryInput);
        genreInput = findViewById(R.id.genreInput);
        costInput = findViewById(R.id.costInput);
        keywordsInput = findViewById(R.id.keywordsInput);
        actorsInput = findViewById(R.id.actorsInput);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        SMSReceiver = new MyReceiver();
        registerReceiver(SMSReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));

        movieList = new ArrayList<>();

        // link toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add options
        // actually that's a method

        // set navigation drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // listen navigation drawer selections
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationDrawerListener());

        // set list view and array, and adapter
        movieListView = findViewById(R.id.movie_list);
        movieListViewAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, movieList);
        movieListView.setAdapter(movieListViewAdapter);
        movieListViewAdapter.notifyDataSetChanged();

        // set FAB functionality
        floatingActionButton = findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMovieIntoList();
            }
        });
    }

    public Movie getMovieFromInput() {
        Movie movie = new Movie();
        movie.setTitle(titleInput.getText().toString());
        movie.setYear(Util.parseInt(yearInput.getText().toString()));
        movie.setCountry(countryInput.getText().toString());
        movie.setGenre(genreInput.getText().toString());
        movie.setCost(Util.parseFloat(costInput.getText().toString()));
        movie.setKeywords(keywordsInput.getText().toString());
        movie.setActors(Util.parseInt(actorsInput.getText().toString()));
        return movie;
    }

    public void addMovieIntoList() {
        Movie movie = getMovieFromInput();
        this.movieList.add(movie);
        this.movieListViewAdapter.notifyDataSetChanged();
    }

    public void removeLastMovieInList() {
        if (!movieList.isEmpty()) movieList.remove(movieList.size()-1);
        movieListViewAdapter.notifyDataSetChanged();
    }

    public void removeAllMoviesInList() {
        movieList.clear();
        movieListViewAdapter.notifyDataSetChanged();
    }

    public void listAllMoviesInList() {
        Intent intent = new Intent(this, AllMoviesActivity.class);
        startActivity(intent);
    }

    public void addMovie(View view) {
        String title = titleInput.getText().toString();
        Toast.makeText(
                getApplicationContext(),
                "Movie - " + title + " - has been added",
                Toast.LENGTH_SHORT
        ).show();
    }

    public void movieInfo(View view) {
        String title = titleInput.getText().toString();
        String year = yearInput.getText().toString();
        String actors = actorsInput.getText().toString();
        Toast.makeText(
                getApplicationContext(),
                "Movie: " + title
                        + "\nYear: " + year
                        + "  Actors: " + actors,
                Toast.LENGTH_LONG
        ).show();
    }

    public void clearInfo(View view) {
        Log.d("infoDebug", "clearInfo: active");
        titleInput.setText("");
        yearInput.setText("");
        countryInput.setText("");
        genreInput.setText("");
        costInput.setText("");
        keywordsInput.setText("");
        actorsInput.setText("");
    }

    public void doubleCost(View view) {
        saveSharedPreferences(view);
        SharedPreferences sp = getSharedPreferences("moviePref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        int cost;
        try {
            cost = Integer.parseInt(sp.getString("costInput", "0"));
        }
        catch (Exception e) {
            cost = 0;
        }
        editor.putString("costInput", Integer.toString(cost * 2));
        editor.apply();
        costInput.setText(Integer.toString(cost * 2));
        Toast.makeText(
                getApplicationContext(),
                "Costs are doubled",
                Toast.LENGTH_SHORT
        ).show();
    }

    public void loadSharedPreferences(View view) {
        SharedPreferences sp = getSharedPreferences("moviePref", MODE_PRIVATE);
        titleInput.   setText(sp.getString("titleInput", ""));
        yearInput.    setText(sp.getString("yearInput", ""));
        countryInput. setText(sp.getString("countryInput", ""));
        genreInput.   setText(sp.getString("genreInput", ""));
        costInput.    setText(sp.getString("costInput", ""));
        keywordsInput.setText(sp.getString("keywordsInput", ""));
        actorsInput.  setText(sp.getString("actorsInput", ""));
        Toast.makeText(
                getApplicationContext(),
                "Preferences are Loaded",
                Toast.LENGTH_SHORT
        ).show();
    }

    public void clearSharedPreferences(View view) {
        SharedPreferences sp = getSharedPreferences("moviePref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("titleInput", "");
        editor.putString("yearInput", "");
        editor.putString("countryInput", "");
        editor.putString("genreInput", "");
        editor.putString("costInput", "");
        editor.putString("keywordsInput", "");
        editor.putString("actorsInput", "");
        editor.apply();
        Toast.makeText(
                getApplicationContext(),
                "Preferences are Cleared",
                Toast.LENGTH_SHORT
        ).show();
    }

    public void saveSharedPreferences(View view) {
        SharedPreferences sp = getSharedPreferences("moviePref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("titleInput", titleInput.getText().toString());
        editor.putString("yearInput", yearInput.getText().toString());
        editor.putString("countryInput", countryInput.getText().toString());
        editor.putString("genreInput", genreInput.getText().toString());
        editor.putString("costInput", costInput.getText().toString());
        editor.putString("keywordsInput", keywordsInput.getText().toString());
        editor.putString("actorsInput", actorsInput.getText().toString());
        editor.apply();
        Toast.makeText(
                getApplicationContext(),
                "Preferences are Loaded",
                Toast.LENGTH_SHORT
        ).show();
    }

    class NavigationDrawerListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.drawerAddMovie:
                    addMovieIntoList();
                    break;

                case R.id.drawerRemoveLastMovie:
                    removeLastMovieInList();
                    break;

                case R.id.drawerRemoveAllMovie:
                    removeAllMoviesInList();
                    break;

                case R.id.drawerListAllMovies:
                    listAllMoviesInList();
                    break;

                default:
                    break;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_clear_field:
                clearInfo(null);
                break;

            case R.id.option_lower_case:
                titleInput.setText(titleInput.getText().toString().toLowerCase());
                break;

            case R.id.option_update_adapter:
                movieListViewAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = getSharedPreferences(MOVIE_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(MOVIE_LIST_KEY, new Gson().toJson(movieList));
        editor.apply();
    }

    @Override
    protected void onResume() {
        SharedPreferences sp = getSharedPreferences(MOVIE_PREF, MODE_PRIVATE);
        String movieData = sp.getString(MOVIE_LIST_KEY, "");
        if (movieData.equals("null")) {
            return;
        }
        Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
        movieList = (ArrayList<Movie>) new Gson().fromJson(movieData, type);
        movieListViewAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, movieList);
        movieListView.setAdapter(movieListViewAdapter);
        movieListViewAdapter.notifyDataSetChanged();
        super.onResume();
    }

    // for SMS receiver
    public void onReceiveSMS(SmsMessage msg) {
        String[] info = msg.getDisplayMessageBody().split(";");
        if (info.length < 7) {
            return;
        }
        int newYear = 0;
        try {
            newYear = Integer.parseInt(info[1]) + Integer.parseInt(info[6]);
        } catch (Exception e){
            return;
        }
        titleInput.   setText(info[0]);  // Title upper case on load
        yearInput.    setText(Integer.toString(newYear));
        countryInput. setText(info[2]);
        genreInput.   setText(info[3]);
        costInput.    setText(info[4]);
        keywordsInput.setText(info[5]);
        // actorsInput.  setText();
    }


    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
            // Toast.makeText(context, Integer.toString(messages.length), Toast.LENGTH_SHORT).show();
            onReceiveSMS(messages[0]);
        }
    }
}