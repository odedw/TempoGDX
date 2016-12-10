package com.inja.tempogdx.metronome;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by oded on 26/11/2016.
 */
public class Metronome {
  private int bpm;
  private long nanoPerBeat;
  private long lastBeatCount;
  private long startTime;
  private Thread thread;
  private boolean isRunning;
  private final DelayedRemovalArray<BeatEventListener> listeners = new DelayedRemovalArray(0);


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
      if (thread != null) {
        thread.join(2 * nanoPerBeat / 1000000);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void step() {
    long timeSinceStart = TimeUtils.timeSinceNanos(startTime);

    long currentBeatCount = timeSinceStart / nanoPerBeat;

    if (currentBeatCount > lastBeatCount) {
      lastBeatCount = currentBeatCount;
      fire(currentBeatCount);
    }
  }

  private void fire(long beatCount) {
    BeatEventListener.BeatEvent event = Pools.obtain(BeatEventListener.BeatEvent.class);
    event.beatCount = beatCount;

    listeners.begin();
    for (int i = 0, n = listeners.size; i < n; i++) {
      listeners.get(i).handle(event);
    }
    listeners.end();

    Pools.free(event);
  }

  public void setBpm(int val) {
    reset();
    bpm = val;
    nanoPerBeat = (long) ((60f / bpm) * 1000000000);
  }

  private void reset() {
    startTime = TimeUtils.nanoTime();
    lastBeatCount = 0;
  }

  public int getBpm() {return bpm;}

  public boolean addListener (BeatEventListener listener) {
    if (listener == null) throw new IllegalArgumentException("listener cannot be null.");
    if (!listeners.contains(listener, true)) {
      listeners.add(listener);
      return true;
    }
    return false;
  }

  public boolean removeListener (BeatEventListener listener) {
    if (listener == null) throw new IllegalArgumentException("listener cannot be null.");
    return listeners.removeValue(listener, true);
  }

  public void clearListeners () {
    listeners.clear();
  }

  public Array<BeatEventListener> getListeners () {
    return listeners;
  }
}
