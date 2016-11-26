package com.inja.metronome.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.inja.metronome.Constants;
import com.inja.metronome.utilities.SkinFactory;

/**
 * Created by oded on 26/11/2016.
 */
public class MainScreen implements Screen {
  private final OrthographicCamera camera;
  private final FitViewport viewport;
  private final Stage stage;

  public MainScreen() {
    camera = new OrthographicCamera();
    viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
    viewport.apply();
    camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2 ,0);
    stage = new Stage(viewport);
    createLayout();
  }

  private void createLayout() {
    Skin skin = SkinFactory.create();

    Table table = new Table(skin);
    stage.addActor(table);
    table.setFillParent(true);

    Label label = new Label("Hi", skin);
    table.add(label);
    table.row();

  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    viewport.update(width,height);
    camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {
    stage.dispose();
  }
}
