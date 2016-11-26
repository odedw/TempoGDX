package com.inja.metronome.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by oded on 26/11/2016.
 */
public class Metronome {
  private final MetronomeDelegate delegate;
  private int bpm;
  private long nanoPerClick;
  private long clicks;
  private long lastClicks;
  private long startTime;
  private Thread thread;
  private boolean isRunning;

  public Metronome(MetronomeDelegate delegate) {
    this.delegate = delegate;
    setBpm(120);
  }

  public void start() {
    startTime = TimeUtils.nanoTime();
    lastClicks = 0;
    isRunning = true;
    thread = new Thread(new Runnable() {
      @Override
      public void run() {
        while(isRunning) {
          step();
        }
      }
    });
    thread.start();
  }

  public void stop() {
    isRunning = false;
    try {
      thread.join(2 * nanoPerClick / 1000000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void step() {
    long timeSinceStart = TimeUtils.timeSinceNanos(startTime);

    clicks = timeSinceStart / nanoPerClick;
    if (clicks > lastClicks) {
      delegate.beat((timeSinceStart % nanoPerClick));
      lastClicks = clicks;
      Gdx.app.log("metronome", (timeSinceStart % nanoPerClick) / 1000000 + "");
    }
  }

  public void setBpm(int val) {
    bpm = val;
    nanoPerClick = (long) ((60f / bpm) * 1000000000);
  }

  public int getBpm() {return bpm;}
}
