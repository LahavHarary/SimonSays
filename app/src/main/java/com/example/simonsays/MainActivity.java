package com.example.simonsays;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private EditText enterUserName;
    Intent intent;
    ImageView simon,says;
    TextView helloWhatsYourName;
    Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simon = findViewById(R.id.simon);
        says = findViewById(R.id.says);
        sendBtn = findViewById(R.id.enter_name_btn);
        helloWhatsYourName = findViewById(R.id.whats_your_name);
        enterUserName = findViewById(R.id.enter_name_edit_text);
        simon.animate().translationX(-1500);
        says.animate().translationX(1500).withEndAction(new Runnable() {
            @Override
            public void run() {
                simon.animate().translationX(0).setDuration(1750).start();
                says.animate().translationX(0).setDuration(1750).start();
                simon.animate().alpha(1).setDuration(3500).start();
                says.animate().alpha(1).setDuration(3500).start();
            }
        });
        helloWhatsYourName.animate().alpha(1f).setStartDelay(2000).setDuration(2000);
        enterUserName.animate().alpha(1f).setStartDelay(3000).setDuration(2000);
        sendBtn.animate().alpha(1f).setStartDelay(4000).setDuration(1000);
        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.scale_up_button);
        sendBtn.startAnimation(animation);
    }

    public void onClickOnSendName(View view) {

        String name = enterUserName.getText().toString();
        if(name.trim().equals(""))
        {
            name = "Simon";
        }
        intent = new Intent(this, MenuActivity.class);
        intent.putExtra("hello_name_txt",name);
        startActivity(intent);
    }
}