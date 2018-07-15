package com.example.alexsandr.seesee;

import java.util.Random;

public class GenerateNewLinePosition {
      private int maxValue=0;
      public GenerateNewLinePosition(int mV)
      {
          maxValue=mV;
      }
      public int generate(int current)
      {
          return new Random().nextInt(maxValue);
      }
}
