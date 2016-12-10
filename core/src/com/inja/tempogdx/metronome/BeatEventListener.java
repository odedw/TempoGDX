package com.inja.tempogdx.metronome;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

/**
 * Created by oded on 10/12/2016.
 */
abstract public class BeatEventListener implements EventListener {
  public boolean handle (Event event) {
    if (!(event instanceof BeatEvent)) return false;
    onBeat((BeatEvent)event);
    return false;
  }

  abstract public void onBeat (BeatEvent event);

  static public class BeatEvent extends Event {
    public long beatCount;
  }
}