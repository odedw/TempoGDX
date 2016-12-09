package com.inja.tempogdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.inja.tempogdx.screens.InfoScreen;
import com.inja.tempogdx.screens.MainScreen;
import com.inja.tempogdx.utilities.Assets;

public class TempoGdxGame extends Game implements MainScreen.MainScreenDelegate, InfoScreen.InfoScreenDelegate {
  private SpriteBatch batch;
  private Texture background;
  private FitViewport viewport;
  private MainScreen mainScreen;
  private InfoScreen infoScreen;
  private long silenceLoopId;

  @Override

  public void create() {
    batch = new SpriteBatch();
    Assets.load();
    Assets.finishLoading();
    background = Assets.getTexture("background");
    background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    viewport = new FitViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
    viewport.apply();
    mainScreen = new MainScreen(viewport, this);
    infoScreen = new InfoScreen(viewport, this);
    silenceLoopId = Assets.getSound("silence").loop();

    setScreen(mainScreen);
  }

  @Override
  public void resize(int width, int height) {
    viewport.update(width, height);
    if (screen != null) screen.resize(width, height);
  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.begin();
    batch.draw(background, 0, 0, viewport.getScreenWidth(), viewport.getScreenHeight());
    batch.end();

    if (screen != null) screen.render(Gdx.graphics.getDeltaTime());
  }

  @Override
  public void dispose() {
    batch.dispose();
    Assets.getSound("silence").stop(silenceLoopId);
  }

  @Override
  public void infoClicked() {
    setScreen(infoScreen);
  }

  @Override
  public void backClicked() {
    setScreen(mainScreen);
  }
}
