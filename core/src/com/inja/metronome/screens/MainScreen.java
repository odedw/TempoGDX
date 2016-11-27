package com.inja.metronome.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.inja.metronome.Constants;
import com.inja.metronome.utilities.Metronome;
import com.inja.metronome.utilities.SkinFactory;

/**
 * Created by oded on 26/11/2016.
 */
public class MainScreen implements Screen{
  private final FitViewport viewport;
  private final Stage stage;
  private final Metronome metronome;
  private TextButton startButton;
  private Slider slider;
  private Label label;

  public MainScreen() {
    metronome = new Metronome();
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

    label = new Label(Integer.toString(metronome.getBpm()), skin);
    table.add(label).top().colspan(2);
    table.row();

    slider = new Slider(Constants.MIN_BPM, Constants.MAX_BPM, 1, false, skin);
    slider.setValue(120);
    slider.setWidth(stage.getWidth() - 20);
    slider.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        setBpm((int) slider.getValue());
      }
    });
    table.add(slider).width(180).padTop(10).colspan(2);
    table.row();

    final Button slowerButton = new TextButton("-", skin);
    slowerButton.addListener(new MetronomeButtonGestureListener(-Constants.BIG_INCREMENT, -1));
    table.add(slowerButton).width(80).height(40).padTop(10);
    Button fasterButton = new TextButton("+", skin);
    fasterButton.addListener(new MetronomeButtonGestureListener(Constants.BIG_INCREMENT, 1));
    table.add(fasterButton).width(80).height(40).padTop(10);
    table.row();

    startButton = new TextButton("Start", skin, "toggle");
    startButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        if (startButton.isChecked()) {
          startButton.setText("Stop");
          metronome.start();
        } else {
          metronome.stop();
          startButton.setText("Start");
        }
      }
    });
    table.add(startButton).width(170).height(40).padTop(20).colspan(2);
    table.row();

//    table.setDebug(true);
  }

  private void setBpm(int value) {
    value = MathUtils.clamp(value, Constants.MIN_BPM, Constants.MAX_BPM);
    metronome.setBpm(value);
    slider.setValue(value);
    label.setText(Integer.toString(metronome.getBpm()));
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

  class MetronomeButtonGestureListener extends ActorGestureListener {
    private int bigIncrement;
    private int smallIncrement;

    MetronomeButtonGestureListener(int bigIncrement, int smallIncrement) {
      super(20, 0.4f, 0.5f, 0.15f);
      this.bigIncrement = bigIncrement;
      this.smallIncrement = smallIncrement;
    }

    public boolean longPress (Actor actor, float x, float y) {
      setBpm(metronome.getBpm() + bigIncrement);
      return true;
    }
    public void tap (InputEvent event, float x, float y, int count, int button) {
      setBpm(metronome.getBpm() + smallIncrement);
    }
  }
}
