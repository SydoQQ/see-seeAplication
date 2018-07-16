package com.example.alexsandr.seesee;

import java.util.Random;

import static java.lang.Math.abs;

public class GenerateNewLinePosition {
      private int maxValue=0;
      private int newPosition=0;
      private int currentPosition=0;
      private int numbArryaPos=0;
      private int numbUpdate=0;
      private int numbCall=0;
      private int Dis=0;
      private int Delay=0;
      private RC4 rc4;
      public GenerateNewLinePosition(int mV,int numbarrayPosition,int dis,int delayChange)
      {
            maxValue=mV;
            Dis=dis;
            numbArryaPos=numbarrayPosition;
            numbUpdate=new Random().nextInt(numbArryaPos);
            Delay=delayChange;
            byte b[]=new byte[256];
            for(int i=0;i<256;i++)
                  b[i]=(byte)(new Random().nextInt(256));
            rc4=new RC4(b);
      }
      public int generate(int current)
      {
            /*numbCall++;
            currentPosition=current;
            int dis=currentPosition/Dis;
            if(numbCall==numbUpdate){
                  newPosition=new Random().nextInt(maxValue);
                  numbUpdate=new Random().nextInt(numbArryaPos);
                  numbCall=0;
            }
            else
                  newPosition=new Random().nextInt(currentPosition+dis)-dis;
            if(newPosition<=0)
                  newPosition=1;
            if(newPosition>=maxValue)
                  newPosition=maxValue-1;*/
            int sum=0;
            for(int i=0;i<100;i++)
                  sum+=rc4.getUnsignedInt()%maxValue;
            newPosition=(int)(sum/100);
            return newPosition;
      }
      public int generateTimeDelay()
      {
            int del=new Random().nextInt((3*Delay));
            return (int)(del+(abs(newPosition-currentPosition)%new Random().nextInt(100)));
      }
}
