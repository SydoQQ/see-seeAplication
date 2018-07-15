package com.example.alexsandr.seesee;

import java.util.Random;

public class GenerateNewLinePosition {
      private int maxValue=0;
      private int newPosition=0;
      private int currentPosition=0;
      public GenerateNewLinePosition(int mV)
      {
          maxValue=mV;
      }
      public int generate(int current)
      {
          return new Random().nextInt(maxValue);
      }
      public int generateTimeDelay(){return 15;}
}
