package com.example.week2lab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText titleInput;
    private EditText yearInput;
    private EditText actorsInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleInput = findViewById(R.id.titleInput);
        yearInput = findViewById(R.id.yearInput);
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
}