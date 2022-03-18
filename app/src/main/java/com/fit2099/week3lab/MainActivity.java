package com.fit2099.week3lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fit2099.week3lab.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText titleInput, yearInput, countryInput, genreInput, costInput, keywordsInput, actorsInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleInput = findViewById(R.id.titleInput);
        yearInput = findViewById(R.id.yearInput);
        countryInput = findViewById(R.id.countryInput);
        genreInput = findViewById(R.id.genreInput);
        costInput = findViewById(R.id.costInput);
        keywordsInput = findViewById(R.id.keywordsInput);
        actorsInput = findViewById(R.id.actorsInput);

    }

    public void addMovie(View view) {
        String title = titleInput.getText().toString();
        Toast.makeText(
                getApplicationContext(),
                "Movie - " + title + " - has been added",
                Toast.LENGTH_LONG
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

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        genreInput.setText(genreInput.getText().toString().toLowerCase());
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        titleInput.setText(titleInput.getText().toString().toUpperCase());
//    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = getSharedPreferences("moviePref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("titleInput", titleInput.getText().toString());
        editor.putString("yearInput", yearInput.getText().toString());
        editor.putString("countryInput", countryInput.getText().toString());
        editor.putString("genreInput", genreInput.getText().toString().toLowerCase());  // genre lower case on save
        editor.putString("costInput", costInput.getText().toString());
        editor.putString("keywordsInput", keywordsInput.getText().toString());
        editor.putString("actorsInput", actorsInput.getText().toString());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("moviePref", MODE_PRIVATE);
        titleInput.   setText(sp.getString("titleInput", "").toUpperCase());  // Title upper case on load
        yearInput.    setText(sp.getString("yearInput", ""));
        countryInput. setText(sp.getString("countryInput", ""));
        genreInput.   setText(sp.getString("genreInput", ""));
        costInput.    setText(sp.getString("costInput", ""));
        keywordsInput.setText(sp.getString("keywordsInput", ""));
        actorsInput.  setText(sp.getString("actorsInput", ""));
    }
}