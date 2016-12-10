package com.inja.tempogdx.metronome;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

/**
 * Created by oded on 10/12/2016.
 */
public class TapTempoCalculator {
  private final float clearInterval;
  private ArrayList<Long> tapTimes = new ArrayList<>();
  private int numberOfTaps;
  private Timer.Task clearTask;
  private Timer.Task currentClearTask;

  public TapTempoCalculator(int numberOfTaps, int minBpm) {
    this.numberOfTaps = numberOfTaps;
    clearInterval = (60f / minBpm);
    clearTask = new Timer.Task() {
      @Override
      public void run() {
        tapTimes.clear();
      }
    };
  }

  public Integer tap() {
    if (currentClearTask != null) {
      currentClearTask.cancel();
    }

    currentClearTask = Timer.schedule(clearTask, clearInterval);

    if (tapTimes.size() == numberOfTaps) {
      tapTimes.remove(0);
    }

    tapTimes.add(TimeUtils.nanoTime());

    long sum = 0;
    if (tapTimes.size() > 1) {
      for (int i = 1; i < tapTimes.size(); i++) {
        sum += tapTimes.get(i) - tapTimes.get(i - 1);
      }
      float averageSeconds = sum / (tapTimes.size() - 1) / 1000000000f;
      int bpm = (int) (60f / averageSeconds);
      return bpm;
    } else {
      return null;
    }
  }
}
