package com.example.alexsandr.seesee;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class StartActivity extends AppCompatActivity {

    ImageView imageView;
    Animation startAnimation;
    int color[]={0,0,0};
    int Delay=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/GochiHand-Regular.ttf");
        setContentView(R.layout.start_activity);
        for(int i=0;i<3;i++)
            color[i]= new Random().nextInt(256);
        TextView textView=(TextView)findViewById(R.id.startactivity_textView);
        imageView=(ImageView) findViewById(R.id.startactivity_ImageView);
        textView.setTextColor(Color.rgb(256-color[0],256-color[1],256-color[2]));
        textView.setTypeface(type);
        imageView.setBackgroundColor(Color.rgb(color[0],color[1],color[2]));
        startAnimation=AnimationUtils.loadAnimation(this, R.anim.startactivity_animation);
        imageView.startAnimation(startAnimation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        imageView.setBackgroundColor(Color.rgb(color[0],color[1],color[2]));
        startAnimation=AnimationUtils.loadAnimation(this, R.anim.startactivity_animation);
        imageView.startAnimation(startAnimation);
        ThreadStartMainActive.start();
    }

    public void delay() {
        try {
            TimeUnit.SECONDS.sleep(Delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    Thread ThreadStartMainActive;

    {
        ThreadStartMainActive = new Thread(new Runnable() {
            public void run() {
                delay();
                int startColor[] = {0, 0, 0, 0};
                for (int i = 0; i < 3; i++)
                    startColor[i] = color[i];
                startColor[3] = (int) imageView.getAlpha() * 100;
                startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("StartColor", startColor));
                finish();
            }
        });
    }
}
