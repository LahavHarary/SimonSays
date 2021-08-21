package com.example.simonsays;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class GameActivity extends AppCompatActivity {


    private TextView numberOfLevel;
    private String[] buttonColorsArray = {"red_btn", "blue_btn", "green_btn", "yellow_btn"};
    private Vector<String> gamePattern = new Vector<>();//the colors the game chose
    private Vector<String> userClickedPattern = new Vector<>();//the colors the player inserted
    private ArrayList<Integer> messagesImages;
    private int level = 0, i;
    private ImageView red, blue, green, yellow;
    private Button startGame;
    private Context context;
    private Integer score;
    private TextView showMyScore;
    private String username;
    private pl.droidsonroids.gif.GifImageView message;
    private Handler handler, handler2;
    private Runnable runnable, runnable2;
    private LinearLayout gameLayout;
    private ArrayList<HighscoreObject> highScoreList;
    private MediaPlayer Sound_blue, Sound_green, Sound_red, Sound_wrong, Sound_yellow;
    private boolean soundIsOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        username = getIntent().getStringExtra("hello_name_txt");
        score = 0;
        context = getApplicationContext();
        numberOfLevel = findViewById(R.id.level_txt);
        red = findViewById(R.id.red_btn);
        blue = findViewById(R.id.blue_btn);
        green = findViewById(R.id.green_btn);
        yellow = findViewById(R.id.yellow_btn);
        gameLayout = findViewById(R.id.game_layout);
        setButtonClickable(false);
        startGame = findViewById(R.id.begin_btn);
        Sound_blue = MediaPlayer.create(this, R.raw.blue);
        Sound_green = MediaPlayer.create(this, R.raw.green);
        Sound_red = MediaPlayer.create(this, R.raw.red);
        Sound_yellow = MediaPlayer.create(this, R.raw.yellow);
        Sound_wrong = MediaPlayer.create(this, R.raw.wrong);
        showMyScore = findViewById(R.id.points_txt);
        messagesImages = new ArrayList<>();
        message = findViewById(R.id.well_done_gif_img);
        messagesImages.add(R.drawable.well_done_gif_1);
        messagesImages.add(R.drawable.well_done_gif_2);
        messagesImages.add(R.drawable.well_done_gif_3);
        messagesImages.add(R.drawable.well_done_gif_4);
        messagesImages.add(R.drawable.well_done_gif_5);
        highScoreList = ConfigSharedPreferences.readListFromPref(this); // fetch high score data
        if (highScoreList == null || highScoreList.isEmpty()) {
            highScoreList = new ArrayList<>();
        }

        soundIsOn = getIntent().getExtras().getBoolean("boolSoundIsOn");
    }

    //onClick
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    public void beginGame(View view) {
        Toast.makeText(GameActivity.this, getResources().getString(R.string.start_of_game), Toast.LENGTH_SHORT).show();
        gameLayout.setBackground(getDrawable(R.drawable.game_activity_background));
        startGame.setText(getString(R.string.new_game_btn));
        startGame.setClickable(false);
        nextSequence();//start fresh new game
    }

    //clear user's selection and pc's selection and start from the first level
    private void startOver() {

        setButtonClickable(false);
        userClickedPattern.removeAllElements();
        gamePattern.removeAllElements();
        score = 0;
        showMyScore.setText(getString(R.string.default_score));
        level = 0;
    }

    public void playGif(){
        message.setAlpha(1f);
        message.animate().alpha(0f).setStartDelay(1000).setDuration(2000);

    }
    //clear player's selection, choose next color and add it to the gamepattern array, increase the level and display it
    public void nextSequence() {

        userClickedPattern.removeAllElements();
        Random randomColor = new Random();
        Random randomGifs = new Random();
        int randomColorChooser = randomColor.nextInt(4);
        int randomGifChooser = randomGifs.nextInt(5);
        if(level >= 1) {

            message.setImageResource(messagesImages.get(randomGifChooser));
            playGif();
        }
        String randomChosenColor = buttonColorsArray[randomColorChooser];
        gamePattern.add(randomChosenColor);
        int size = gamePattern.size();
        i = 0;

        handler = new Handler();
        runnable = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                playPattern(gamePattern.elementAt(i));
                i++;
                if (i < size)
                    handler.postDelayed(runnable, 900);
                else {
                    setButtonClickable(true);

                }
            }

        };
        handler.postDelayed(runnable, 900);
        //playPattern();
        level++;
        numberOfLevel.setText(getString(R.string.level) +" " + level);//this will change the text to the current level number
        setButtonClickable(false);
    }

    //here we generate the new pattern for the new level
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void playPattern(String color) {
        playSound(color);
        buttonAnimation(color);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void restoreButtonColorAndShape(ImageView view, int curColor) {

        handler2 = new Handler();
        runnable2 = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {

                view.setColorFilter(ContextCompat.getColor(view.getContext(), curColor),
                        android.graphics.PorterDuff.Mode.SRC_IN);

            }
        };
        handler2.postDelayed(runnable2, 300);


        view.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void buttonAnimation(String name) {
        switch (name) {
            case "red_btn":
                restoreButtonColorAndShape(red, R.color.red);
                break;
            case "blue_btn":
                restoreButtonColorAndShape(blue, R.color.blue);
                break;
            case "green_btn":
                restoreButtonColorAndShape(green, R.color.green);
                break;
            case "yellow_btn":
                restoreButtonColorAndShape(yellow, R.color.yellow);
                break;
            default:
                gameLayout.setBackgroundColor(Color.DKGRAY);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void checkAnswer(String color, int currentLevel) {

        userClickedPattern.add(color);
        if (gamePattern.elementAt(currentLevel - 1).equals(userClickedPattern.elementAt(currentLevel - 1))) {
            playPattern(color);

            if (currentLevel == level) {
                score += 10;
                showMyScore.setText(getString(R.string.current_score) +": " +score.toString());
                nextSequence();
            }
        } else {
            playPattern("wrong");
            gameOver();
        }
    }

    private void gameOver() {
        numberOfLevel.setText(getString(R.string.game_over));
        message.setImageResource(R.drawable.game_over_gif);
        playGif();
        startGame.setText(getString(R.string.bad_luck_try_again));
        startGame.setClickable(true);
        updateHighScores();
        startOver();
    }

    //here we check which sound to play according to which color was chosen
    private void playSound(String name) {

        if (soundIsOn == false) {
            return;
        }
        MediaPlayer mediaPlayer;
        switch (name) {
            case "red_btn":
                Sound_red.start();
                break;
            case "blue_btn":
                Sound_blue.start();
                break;
            case "green_btn":
                Sound_green.start();
                break;
            case "yellow_btn":
                Sound_yellow.start();
                break;
            default:
                Sound_wrong.start();
                break;
        }
    }

    public void setButtonClickable(boolean status) {

        red.setClickable(status);
        blue.setClickable(status);
        green.setClickable(status);
        yellow.setClickable(status);

    }

    /*these buttons refer to the options*/

    //user chose red
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void redWasChosen(View view) {
        checkAnswer("red_btn", userClickedPattern.size() + 1);
    }

    //user selected yellow
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void yellowWasChosen(View view) {
        checkAnswer("yellow_btn", userClickedPattern.size() + 1);
    }

    //user selected green
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void greenWasChosen(View view) {
        checkAnswer("green_btn", userClickedPattern.size() + 1);
    }

    //user selected blue
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void blueWasChosen(View view) {
        checkAnswer("blue_btn", userClickedPattern.size() + 1);
    }

    private void updateHighScores() {
        HighscoreObject newHighScore = new HighscoreObject(username, score.toString()); // Create a new highscore with username and current points
        highScoreList.add(newHighScore); // Add it to the list
        Collections.sort(highScoreList); // Sort the list in descending order so the highest score will be first
        // (this only works because we implemented Comparable in HighScoreObject.java class and override compareTo function

        // Shared Preferences
        ConfigSharedPreferences.writeListInPref(getApplicationContext(), highScoreList); // Write list to shared preferences so it would be saved if we re-open the application
    }
}