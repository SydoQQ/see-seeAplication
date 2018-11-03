package com.example.alexsandr.seesee;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    int heightDisplay=0;
    LinearLayout mLine;
    ImageView mImageView;
    ImageView mainBackGround;
    int startColor[]={0,0,0,0};
    float startAlpha=0;
    int currentLinePosition=0;
    int BackGroundColor[][]=new int[4][3];
    int LineColor[][]=new int[2][3];
    int gradientC[]=new int[2];
    GradientDrawable gradientBackDrawable;
    GenerateNewLinePosition generatorNewLinePosition;
    private int timeKof[]={10,20,30};
    private int indKof=0;
    private float Kof=1;
    private int DisAdd=1000;
    long Point=0;
    TextView pointView;
    boolean running=true;
    int seconds=0;
    /*Default Settings*/
    private int changeStartAlphaTime=100;
    private float stepChangeStartAlpha=(float)0.05;
    private int defaulDelayChangeBackGroundGradientMKS=25000;
    private int defaultDelayChangeLinePosition=10;
    private int defaultDelayChangeLineColorNS=35000000;
    private int numbArryaPos=100;
    private int dis=10;
    private static final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        startColor=intent.getIntArrayExtra("StartColor");
        startAlpha=(float)(startColor[3]/100);
        mLine=(LinearLayout)findViewById(R.id.mainLineLayout);
        mainBackGround=(ImageView)findViewById(R.id.mainBackGround);
        mImageView=(ImageView)findViewById(R.id.mainLine);
        gradientC[0]=Color.rgb(startColor[0],startColor[1],startColor[2]);
        gradientC[1]=Color.rgb(startColor[0],startColor[1],startColor[2]);
        gradientBackDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, gradientC);
        mainBackGround.setBackgroundDrawable(gradientBackDrawable);
        Display display = getWindowManager().getDefaultDisplay();
        heightDisplay = display.getHeight();
        generatorNewLinePosition=new GenerateNewLinePosition(heightDisplay,numbArryaPos,dis,defaultDelayChangeLinePosition);
        currentLinePosition=heightDisplay/2;
        StartAnimationThread.start();
        timerStart();
        checkedTime();
        AddPoint();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast toast = Toast.makeText(getApplicationContext(),
                "Go!", Toast.LENGTH_SHORT);
        toast.show();
        mLine.setY(currentLinePosition);
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

    Thread StartAnimationThread=new Thread(new Runnable() {
        @Override
        public void run() {

            for(int i=0;i<3;i++)
            {
                LineColor[0][i]=startColor[i];
            }
            for(int i=1;i<4;i+=2)
            {
                for(int j=0;j<3;j++)
                {
                    BackGroundColor[i][j]=new Random().nextInt(256);
                }
            }
            for(int i=0;i<4;i+=2)
            {
                for(int j=0;j<3;j++)
                {
                    BackGroundColor[i][j]=startColor[j];
                }
            }
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
            ThreadChangeBackGroundGradient.start();
            ThreadChangeLineColor.start();
            ThreadChangeLinePosition.start();
        }
    });
    Thread ThreadChangeBackGroundGradient=new Thread(new Runnable() {
        @Override
        public void run() {
            int k;
            int IGC;
            while(true) {
                k=0;
                IGC=0;
                for(int i=1;i<4;i+=2)
                {
                    for(int j=0;j<3;j++)
                    {
                        BackGroundColor[i][j]=new Random().nextInt(256);
                    }
                }
                for(int i=0;i<4;i+=2)
                {
                    for(int j=0;j<3;j++)
                    {
                        if(BackGroundColor[i][j]>BackGroundColor[i+1][j])
                            k=-1;
                        else k=1;
                        for(;;)
                        {
                            MICROSLEEP(defaulDelayChangeBackGroundGradientMKS);
                            if(BackGroundColor[i][j]==BackGroundColor[i+1][j])
                                break;
                            BackGroundColor[i][j]+=k;
                            gradientC[IGC]=Color.rgb(BackGroundColor[i][0],BackGroundColor[i][1],BackGroundColor[i][2]);
                            mainBackGround.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (android.os.Build.VERSION.SDK_INT >= 16) {
                                        gradientBackDrawable.mutate();
                                        gradientBackDrawable.setColors(gradientC);
                                    } else {
                                        GradientDrawable newGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, gradientC);
                                        gradientBackDrawable=newGradientDrawable;
                                    }
                                    mainBackGround.setBackgroundDrawable(gradientBackDrawable);
                                }
                            });
                        }
                    }
                    IGC++;
                    k=0;
                }
            }
        }
    });
    Thread ThreadChangeLineColor=new Thread(new Runnable() {
        @Override
        public void run() {
            int k;
            int IGC;
            while(true) {
                k=0;
                for(int j=0;j<3;j++)
                {
                        LineColor[1][j]=new Random().nextInt(256);
                }
                for(int j=0;j<3;j++)
                {
                    if(LineColor[0][j]>LineColor[1][j])
                            k=-1;
                    else k=1;
                    for(;;)
                    {
                        NANOSLEEP(defaultDelayChangeLineColorNS);
                        if(LineColor[0][j]==LineColor[1][j])
                                break;
                        LineColor[0][j]+=k;
                        mImageView.post(new Runnable() {
                            @Override
                            public void run() {
                                int C=Color.rgb(LineColor[0][0],LineColor[0][1],LineColor[0][2]);
                                mImageView.setBackgroundColor(C);
                                }
                            });
                        }
                    }
                    k=0;
                }
            }
    });
    Thread ThreadChangeLinePosition=new Thread(new Runnable() {
        @Override
        public void run() {
            while (true)
            {
                int newLinePosition=0;
                int k=0;
                newLinePosition=generatorNewLinePosition.generate(currentLinePosition);
                defaultDelayChangeLinePosition=generatorNewLinePosition.generateTimeDelay();
                //Log.d(TAG,String.valueOf(newLinePosition)+" "+String.valueOf(defaultDelayChangeLinePosition));
                if(currentLinePosition>newLinePosition)
                    k=-1;
                else
                    k=1;
                for(;;)
                {
                    MILLISLEEP(defaultDelayChangeLinePosition);
                    if(currentLinePosition==newLinePosition)
                        break;
                    currentLinePosition+=k;
                    mLine.post(new Runnable() {
                        @Override
                        public void run() {

                            mLine.setY(currentLinePosition);
                        }
                    });
                }
                k=0;
            }

        }
    });

    protected void checkedKof(){
        switch (indKof){
            case 1: {Kof=(float)1/2; break;}
            case 2: {Kof=(float)1/4; break;}
            case 3: {Kof=-1;break;}
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
        final TextView textView=(TextView)findViewById(R.id.pointView);
        final Handler textViewhandler=new Handler();
        Point=0;
        String str=String.format("%019d", Point);
        textView.setText(String.valueOf(str));
        textViewhandler.post(new Runnable() {
            @Override
            public void run() {
                if(running){
                    long add=new Random().nextInt(DisAdd);
                    add*=Kof;
                    Point+=add;
                    //Log.d(TAG,String.valueOf(Point)+" "+String.valueOf(add));
                    if(Point<0) {
                        Point = 0;
                        running=false;
                    }
                    else if(Point<Long.MIN_VALUE+(DisAdd*2)){
                        Point = Long.MAX_VALUE;
                        running=false;
                    }
                    String str=String.format("%019d", Point);
                    textView.setText(str);
                }
                textViewhandler.postDelayed(this,1000);
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
