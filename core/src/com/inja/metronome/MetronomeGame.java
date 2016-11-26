package com.inja.metronome;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inja.metronome.screens.MainScreen;

public class MetronomeGame extends Game {
	SpriteBatch batch;

	@Override
	public void create () {
      batch = new SpriteBatch();
      setScreen(new MainScreen());
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
