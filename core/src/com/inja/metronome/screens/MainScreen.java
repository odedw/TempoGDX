package com.inja.metronome.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.inja.metronome.Constants;
import com.inja.metronome.utilities.Metronome;
import com.inja.metronome.utilities.MetronomeDelegate;
import com.inja.metronome.utilities.SkinFactory;

/**
 * Created by oded on 26/11/2016.
 */
public class MainScreen implements Screen, MetronomeDelegate {
//  private final OrthographicCamera camera;
  private final FitViewport viewport;
  private final Stage stage;
  private final Metronome metronome;
  private TextButton startButton;
  private Sound beatSound = Gdx.audio.newSound(Gdx.files.internal("click.ogg"));

  public MainScreen() {
    metronome = new Metronome(this);
    viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
    viewport.apply();
    stage = new Stage(viewport);
    createLayout();
  }

  private void createLayout() {
    Skin skin = SkinFactory.create();

    Table table = new Table(skin);
    stage.addActor(table);
    table.setFillParent(true);
    table.align(Align.center | Align.top);
    table.padTop(60);

    Label label = new Label("Metronome", skin);
    table.add(label).top();
    table.row();

    Slider slider = new Slider(60, 160, 1, false, skin);
    slider.setValue(120);
    slider.setWidth(stage.getWidth() - 20);
    table.add(slider);
    table.row();


    startButton = new TextButton("Start", skin);
    startButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        if (startButton.isChecked()) {
          startButton.setText("Stop");
          metronome.start();
        } else {
          startButton.setText("Start");
        }

      }
    });
    table.add(startButton).width(120).height(40).padTop(60);
    table.row();

//    table.setDebug(true);
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    stage.act(delta);
    stage.draw();

    if (startButton.isChecked()) {
      metronome.step();
    }
  }

  @Override
  public void resize(int width, int height) {
    viewport.update(width,height);
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

  @Override
  public void beat(long lag) {
    beatSound.play();
  }
}
