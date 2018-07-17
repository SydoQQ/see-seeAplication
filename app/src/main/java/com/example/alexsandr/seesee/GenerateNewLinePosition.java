package com.example.alexsandr.seesee;

import java.util.Random;

import static java.lang.Math.abs;

public class GenerateNewLinePosition {
      private int maxValue=0;
      private int newPosition=0;
      private int currentPosition=0;
      private int numbArryaPos=0;
      private int Dis=0;
      private int Delay=0;
      private RC4 rc4;
      public GenerateNewLinePosition(int mV,int numbarrayPosition,int dis,int delayChange)
      {
            maxValue=mV;
            Dis=dis;
            numbArryaPos=numbarrayPosition;
            Delay=delayChange;
            byte b[]=new byte[256];
            for(int i=0;i<256;i++)
                  b[i]=(byte)(new Random().nextInt(256));
            rc4=new RC4(b);
            currentPosition=rc4.getUnsignedInt();
      }
      public int generate(int current)
      {
            currentPosition=newPosition;
            newPosition=rc4.getUnsignedInt();
            int pos=abs(newPosition-currentPosition);
            pos=(int)pos%maxValue;
            return pos;
      }
      public int generateTimeDelay()
      {
            return ((int)rc4.getUnsignedInt()%(Delay))+1;
      }
}
