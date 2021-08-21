package com.example.simonsays;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreBoardActivity extends AppCompatActivity {

    private TextView txt;
    private Context context;
    ListView myListView;
    ArrayList<HighscoreObject> highScoreObjectArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        highScoreObjectArrayList = new ArrayList<>();


        highScoreObjectArrayList = ConfigSharedPreferences.readListFromPref(this); // Get highscore data from shared preferences
        if ( highScoreObjectArrayList ==null)
            highScoreObjectArrayList = new ArrayList<>();

        myListView=findViewById(R.id.listViewhighScores);

        Collections.sort(highScoreObjectArrayList); // Sort the list in descending order so the highest score will be first

        ListviewAdapter adapter = new ListviewAdapter(ScoreBoardActivity.this, R.layout.listviewline_design, highScoreObjectArrayList);
        myListView.setAdapter(adapter);
    }
}