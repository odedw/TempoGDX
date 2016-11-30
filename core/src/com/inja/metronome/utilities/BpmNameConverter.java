package com.inja.metronome.utilities;

/**
 * Created by oded on 30/11/2016.
 */
public class BpmNameConverter {
  public static String getName(int bpm) {
    if (bpm <= 40)
      return "Largamente";
    else if (bpm <= 45)
      return "Grave";
    else if (bpm <= 51)
      return "Largo";
    else if (bpm <= 55)
      return "Lento";
    else if (bpm <= 59)
      return "Adagio";
    else if (bpm <= 65)
      return "Larghetto";
    else if (bpm <= 71)
      return "Adagietto";
    else if (bpm <= 79)
      return "Andante";
    else if (bpm <= 87)
      return "Andantino";
    else if (bpm <= 95)
      return "Maestoso";
    else if (bpm <= 107)
      return "Moderato";
    else if (bpm <= 119)
      return "Allegretto";
    else if (bpm <= 131)
      return "Animato";
    else if (bpm <= 143)
      return "Allegro";
    else if (bpm <= 159)
      return "Vivace";
    else if (bpm <= 191)
      return "Presto";
    else if (bpm <= 207)
      return "Vivacissimo";
    else // > 207
      return "Prestissimo";
  }
}
