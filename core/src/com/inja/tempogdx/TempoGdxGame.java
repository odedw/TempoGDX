package com.inja.tempogdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inja.tempogdx.screens.MainScreen;
import com.inja.tempogdx.utilities.Assets;

public class TempoGdxGame extends Game {
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
