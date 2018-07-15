package com.example.alexsandr.seesee;

import java.util.Random;

public class GenerateNewLinePosition {
      private int maxValue=0;
      private int newPosition=0;
      private int currentPosition=0;
      private int arraynewPosition[]=new int[100];
      private int numbArryaPos=0;
      private int numbUpdate=0;
      private int numbCall=0;
      private int Dis=0;
      public GenerateNewLinePosition(int mV,int numbarrayPosition,int dis)
      {
            maxValue=mV;
            Dis=dis;
            numbArryaPos=numbarrayPosition;
            arraynewPosition=new int[numbArryaPos];
            for(int i=0;i<numbArryaPos;i++)
                  arraynewPosition[i]=new Random().nextInt(maxValue);
            numbUpdate=new Random().nextInt(numbArryaPos);
      }
      public int generate(int current)
      {
            numbCall++;
            int sum=0;
            currentPosition=current;
            int dis=currentPosition/Dis;
            for(int i=0;i<numbArryaPos;i++)
                  sum+=arraynewPosition[i];
            sum=sum/numbArryaPos;
            newPosition=sum;
            int numbPos=new Random().nextInt(numbArryaPos);
            if(numbUpdate==numbCall) {
                  for (int i = 0; i < numbArryaPos; i++)
                        arraynewPosition[i] = new Random().nextInt(maxValue);
                  numbUpdate=new Random().nextInt(maxValue);
                  numbCall=0;
            }
            else
                  for(int i=0;i<numbPos;i++)
                        arraynewPosition[new Random().nextInt(numbArryaPos)]=(new Random().nextInt(maxValue+dis))-dis;
            return newPosition;
      }
      public int generateTimeDelay()
      {
            return 1;
      }
}
