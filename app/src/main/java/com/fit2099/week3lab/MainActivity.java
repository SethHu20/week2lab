package com.fit2099.week3lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fit2099.week3lab.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText titleInput, yearInput, countryInput, genreInput, costInput, keywordsInput, actorsInput;
    MyReceiver SMSReceiver = null;

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

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        SMSReceiver = new MyReceiver();
        registerReceiver(SMSReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));

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
            // TODO: This method is called when the BroadcastReceiver is receiving
            SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
            // Toast.makeText(context, Integer.toString(messages.length), Toast.LENGTH_SHORT).show();
            onReceiveSMS(messages[0]);
        }
    }
}