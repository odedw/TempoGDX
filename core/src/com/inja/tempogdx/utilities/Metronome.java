package com.inja.tempogdx.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by oded on 26/11/2016.
 */
public class Metronome {
  private int bpm;
  private long nanoPerClick;
  private long lastClicks;
  private long startTime;
  private Thread thread;
  private boolean isRunning;
  private EventListener listener;

  public Metronome() {
    setBpm(120);
  }

  public void start() {
    reset();
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

    long clicks = timeSinceStart / nanoPerClick;

    if (clicks > lastClicks) {
      lastClicks = clicks;
      beat();
    }
  }

  private void beat() {
    if (listener != null) {
      listener.handle(new BeatEvent());
    }
  }

  public void setBpm(int val) {
    reset();
    bpm = val;
    nanoPerClick = (long) ((60f / bpm) * 1000000000);
  }

  private void reset() {
    startTime = TimeUtils.nanoTime();
    lastClicks = 0;
  }

  public int getBpm() {return bpm;}

  public void setListener(EventListener listener) {
    this.listener = listener;
  }

  public class BeatEvent extends Event {

  }
}
