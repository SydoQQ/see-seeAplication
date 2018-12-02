package com.example.alexsandr.seesee;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.security.auth.Destroyable;

public class MainActivity extends AppCompatActivity {

    ImageView mainBackGround;
    TextView textView;
    private int startColor[]={0,0,0,0};
    private float startAlpha=0;

    private GradientDrawable gradientBackDrawable;
    private long Point=0;
    private TextView pointView;
    private boolean running=true;

    /*Default Settings*/
    private int changeStartAlphaTime=100;
    private float stepChangeStartAlpha=(float)0.05;
    private static final String TAG = "myLogs";
    TextView ExitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        //setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        startColor=intent.getIntArrayExtra("StartColor");
        startAlpha=(float)(startColor[3]/100);
        //backGroundGratientChange();
        //PointColorChange();
        //timerStart();
        //checkedTime();
        //AddPoint();
        ExitButton=(TextView)findViewById(R.id.exitbutton);
        ExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private int colorGradient[][]=new int[2][3];
    private int NN=0;
    private int MM=0;
    private int newValueColor=0;
    private int AddColor=0;
    private int KK=0;
    private int gradientC[]=new int[2];
    protected void backGroundGratientChange()
    {
        final Handler backGroundChangeHandler=new Handler();
        mainBackGround=(ImageView)findViewById(R.id.mainBackGround);
        mainBackGround.setBackgroundColor(Color.rgb(startColor[0],startColor[1],startColor[2]));
        for(int i=0;i<2;i++)
            for(int j=0;j<3;j++)
                colorGradient[i][j]=startColor[j];
        gradientC[0]=Color.rgb(colorGradient[0][0],colorGradient[0][1],colorGradient[0][2]);
        gradientC[1]=Color.rgb(colorGradient[1][0],colorGradient[1][1],colorGradient[1][2]);
        gradientBackDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, gradientC);
        mainBackGround.setBackgroundDrawable(gradientBackDrawable);
        newValueColor=new Random().nextInt(256);
        if(newValueColor>colorGradient[NN][MM])
            KK=1;
        else
            KK=-1;
        for(;;)
        {
            MILLISLEEP(changeStartAlphaTime);
            mainBackGround.post(new Runnable() {
                @Override
                public void run() {
                    mainBackGround.setAlpha(startAlpha);
                }
            });
            if(startAlpha==1.0)
                break;
            startAlpha+=stepChangeStartAlpha;
        }
        backGroundChangeHandler.post(new Runnable() {
            @Override
            public void run() {
                if (running) {
                    if (AddColor == newValueColor) {
                        MM++;
                        if (MM > 2) {
                            MM = 0;
                            NN++;
                            if (NN > 1)
                                NN = 0;
                        }
                        newValueColor = new Random().nextInt(256);
                        if (newValueColor > colorGradient[NN][MM])
                            KK = 1;
                        else
                            KK = -1;
                        AddColor = 0;
                    }
                    AddColor++;
                    colorGradient[NN][MM] += KK;
                    if (colorGradient[NN][MM] == 255) {
                        AddColor = newValueColor;
                    }
                    gradientC[NN] = Color.rgb(colorGradient[NN][0], colorGradient[NN][1], colorGradient[NN][2]);
                    gradientBackDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, gradientC);
                    mainBackGround.setBackgroundDrawable(gradientBackDrawable);
                }
                backGroundChangeHandler.postDelayed(this, TimeKofBackGround);
                }
        });
    }

    private int PointColor[]=new int[3];
    private int II=0;
    private int AddPointColor=0;
    private int newPointColor=0;
    private int KC=0;
    private void PointColorChange (){
        final Handler pointColorChange=new Handler();
        for(int i=0;i<3;i++)
            PointColor[i]=0;
        newPointColor=new Random().nextInt(256);
        KC=1;
        pointColorChange.post(new Runnable() {
            @Override
            public void run() {
                if(running){
                    if(AddPointColor==newPointColor)
                    {
                        II++;
                        if(II>2)
                            II=0;
                        newPointColor=new Random().nextInt(256);
                        AddPointColor=0;
                        if(newPointColor>PointColor[II])
                            KC=1;
                        else KC=-1;
                    }
                    AddPointColor++;
                    PointColor[II]+=KC;
                    if(PointColor[II]==255)
                        AddPointColor=newPointColor;
                    textView.setTextColor(Color.rgb(PointColor[0],PointColor[1],PointColor[2]));
                }
                pointColorChange.postDelayed(this,TimeKofBackGround);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast toast = Toast.makeText(getApplicationContext(),
                "Go!", Toast.LENGTH_SHORT);
        toast.show();
        running=true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast toast = Toast.makeText(getApplicationContext(),
                "Good Buy!", Toast.LENGTH_SHORT);
        toast.show();
        running=false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast toast = Toast.makeText(getApplicationContext(),
                "Go!", Toast.LENGTH_SHORT);
        toast.show();
        running=false;
    }

    private void MICROSLEEP(int delay) {
        try {
            TimeUnit.MICROSECONDS.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void MILLISLEEP(int delay) {
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void NANOSLEEP(int delay) {
        try {
            TimeUnit.NANOSECONDS.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int timeKof[]={10,20,30};
    private int indKof=0;
    private float Kof=1;
    private int DisAdd=1000;
    private long newValuePoint;
    private long Add;
    private int TimeKofBackGround=1;
    private int seconds=0;
    private int TimeKof;
    protected void checkedKof(){
        switch (indKof){
            case 1: {Kof=1; TimeKof=1;TimeKofBackGround=25; break;}
            case 2: {Kof=1; TimeKof=10;TimeKofBackGround=100; break;}
            case 3: {Kof=-1; TimeKof=10;TimeKofBackGround=1000; break;}
        }
    }

    protected void checkedTime(){
        final Handler handlerCheckedTime=new Handler();
        handlerCheckedTime.post(new Runnable() {
            @Override
            public void run() {
                if(indKof>2)
                    indKof=2;
                if(seconds>timeKof[indKof]) {
                    indKof++;
                    checkedKof();
                }
                handlerCheckedTime.postDelayed(this,1000);
            }
        });
    }

    protected void timerStart(){
        final Handler handlerTimerView=new Handler();
        handlerTimerView.post(new Runnable() {
            @Override
            public void run() {
                if(running){
                    seconds++;
                }
                handlerTimerView.postDelayed(this,1000);
            }
        });
    }

    protected void AddPoint(){
        textView=(TextView)findViewById(R.id.pointView);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/12046.ttf");
        textView.setTypeface(type);
        final Handler textViewhandler=new Handler();
        Point=0;
        Add=0;
        String str=String.format("%019d", Point);
        textView.setText(String.valueOf(str));
        textViewhandler.post(new Runnable() {
            @Override
            public void run() {
                if(running){
                    if(newValuePoint==Add)
                    {
                        newValuePoint=new Random().nextInt(DisAdd);
                        Add=0;
                    }
                    Point+=Kof;
                    //Log.d(TAG,String.valueOf(Point)+" "+String.valueOf(add));
                    if(Point<0) {
                        Point = 0;
                        running=false;
                    }
                    else if(Point<Long.MIN_VALUE+(DisAdd*2)){
                        Point = Long.MAX_VALUE;
                        running=false;
                    }
                    Add++;
                    String str=String.format("%019d", Point);
                    textView.setText(str);
                }
                textViewhandler.postDelayed(this,TimeKof);
            }
        });
    }
    public boolean onTouchEvent(MotionEvent e) {
        seconds=0;
        indKof--;
        running=true;
        if(indKof<0) {
            indKof = 0;
            Kof=1;
        }
        checkedKof();
        return true;
    }
}
