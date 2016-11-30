package com.inja.metronome;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inja.metronome.screens.MainScreen;
import com.inja.metronome.utilities.Assets;

public class MetronomeGame extends Game {
	SpriteBatch batch;

	@Override
	public void create () {
      batch = new SpriteBatch();
      Assets.load();
      Assets.finishLoading();
      setScreen(new MainScreen(batch));
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
