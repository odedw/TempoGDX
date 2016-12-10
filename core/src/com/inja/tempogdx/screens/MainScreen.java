package com.inja.tempogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.inja.tempogdx.Constants;
import com.inja.tempogdx.metronome.BeatEventListener;
import com.inja.tempogdx.metronome.Metronome;
import com.inja.tempogdx.metronome.TapTempoCalculator;
import com.inja.tempogdx.utilities.Assets;
import com.inja.tempogdx.metronome.BpmNameConverter;
import com.inja.tempogdx.utilities.SkinFactory;

public class MainScreen implements Screen {
  private final Stage stage;
  private final Metronome metronome;
  private final TapTempoCalculator tapTempoCalculator;
  private ImageButton startButton;
  private Slider slider;
  private Label bpmLabel;
  private Label bpmNameLabel;
  private Array<Image> beatIndicators = new Array<>();
  private int currentBeatIndicator = -1;
  private MainScreenDelegate delegate;
  private Table table;
  private boolean firstShow = true;
  private ImageButton infoButton;
  private Integer margin;


  public MainScreen(Viewport viewport, MainScreenDelegate delegate) {
    this.delegate = delegate;
    metronome = new Metronome();
    tapTempoCalculator = new TapTempoCalculator(Constants.NUMBER_OF_TAPS, Constants.MIN_BPM);
    stage = new Stage(viewport);

    createLayout();
  }

  private void createLayout() {
    Skin skin = SkinFactory.create();
    int buttonHeight = skin.get("button-height", Integer.class);
    margin = skin.get("margin", Integer.class);
    final int uiWidth = skin.get("ui-width", Integer.class);

    //Main Table
    table = new Table(skin);
    stage.addActor(table);
    table.center();
    table.setWidth(uiWidth);
    table.setHeight(stage.getHeight());
    table.setX(stage.getWidth() / 2 - table.getWidth() / 2);

    //BPM Table
    Table bpmTable = new Table();
    bpmTable.background(skin.getDrawable("list"));
    bpmLabel = new Label(Integer.toString(metronome.getBpm()), skin, "big");
    bpmTable.add(bpmLabel).height(20).padTop(margin).row();
    bpmNameLabel = new Label(BpmNameConverter.getName(metronome.getBpm()), skin, "small");
    bpmTable.add(bpmNameLabel).padTop(margin);
    table.add(bpmTable).colspan(2).height(120).fillX().expandX().row();

    //Tap Tempo Button
    ImageButton tapButton = new ImageButton(skin, "tap");
    tapButton.addListener(new ActorGestureListener(){
      @Override
      public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
        Integer bpm = tapTempoCalculator.tap();
        if (bpm != null) setBpm(bpm);
      }
    });
    table.add(tapButton).colspan(2).height(buttonHeight).padTop(margin).fillX().expandX().row();

    //Tempo Slider
    slider = new Slider(Constants.MIN_BPM, Constants.MAX_BPM, 1, false, skin);
    slider.setValue(metronome.getBpm());
    slider.setWidth(stage.getWidth() - 20);
    slider.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        setBpm((int) slider.getValue());
      }
    });
    table.add(slider).padTop(margin).colspan(2).fillX().expandX().row();

    //Increase/Decrease Buttons
    final ImageButton slowerButton = new ImageButton(skin, "slower");
    slowerButton.addListener(new MetronomeButtonGestureListener(-Constants.BIG_INCREMENT, -1));
    table.add(slowerButton).height(buttonHeight).padTop(margin).padRight(10).width((table.getWidth() - 20) / 2);
    ImageButton fasterButton = new ImageButton(skin, "faster");
    fasterButton.addListener(new MetronomeButtonGestureListener(Constants.BIG_INCREMENT, 1));
    table.add(fasterButton).height(buttonHeight).padTop(margin).padLeft(10).width((table.getWidth() - 20) / 2).row();

    //Start/Stop Button
    startButton = new ImageButton(skin, "play");
    startButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        toggleMetronome();
      }
    });
    table.add(startButton).height(buttonHeight).padTop(20).colspan(2).fillX().expandX().row();

    //Beat Indicator
    Table indicatorTable = new Table();
    indicatorTable.background(skin.getDrawable("list"));
    for (int i = 0; i < 4; i++) {
      Image image = new Image(Assets.getDrawable("beatOff"));
      beatIndicators.add(image);
      indicatorTable.add(image).pad(0, margin, 0, margin).width(20).height(20);
    }
    table.add(indicatorTable).colspan(2).padTop(20).height(50).fillX().expandX().row();

    //Info Button
    infoButton = new ImageButton(skin, "info");
    infoButton.setWidth(40);
    infoButton.setHeight(40);
    infoButton.setX(stage.getWidth() - margin - infoButton.getWidth());
    infoButton.setY(stage.getHeight() - margin - infoButton.getHeight());
    infoButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        infoButton.addAction(Actions.moveBy(0, margin + infoButton.getHeight(), 0.2f, Interpolation.exp5In));
        table.addAction(Actions.sequence(
                Actions.moveBy(-stage.getWidth(), 0, 0.2f, Interpolation.exp5In),
                new Action() {
                  @Override
                  public boolean act(float delta) {
                    delegate.infoClicked();
                    return true;
                  }
                }

        ));
      }
    });
    stage.addActor(infoButton);

    //Metronome Beat Event
    metronome.addListener(new BeatEventListener() {
      @Override
      public void onBeat(BeatEvent event) {
        if (currentBeatIndicator >= 0 && currentBeatIndicator < beatIndicators.size)
          beatIndicators.get(currentBeatIndicator).setDrawable(Assets.getDrawable("beatOff"));

        currentBeatIndicator++;
        if (currentBeatIndicator == beatIndicators.size) currentBeatIndicator = 0;
        Assets.getSound(currentBeatIndicator == 0 ? "clickFirst" : "click").play();
        beatIndicators.get(currentBeatIndicator).setDrawable(Assets.getDrawable("beatOn"));
      }
    });
  }

  private void toggleMetronome() {
    if (startButton.isChecked()) {
      startMetronome();
    } else {
      stopMetronome();
    }
  }

  private void startMetronome() {
    metronome.start();
  }

  private void stopMetronome() {
    if (currentBeatIndicator >= 0 && currentBeatIndicator < beatIndicators.size) {
      beatIndicators.get(currentBeatIndicator).setDrawable(Assets.getDrawable("beatOff"));
    }
    currentBeatIndicator = -1;
    metronome.stop();
  }

  private void setBpm(int value) {
    value = MathUtils.clamp(value, Constants.MIN_BPM, Constants.MAX_BPM);
    metronome.setBpm(value);
    slider.setValue(value);
    bpmLabel.setText(Integer.toString(metronome.getBpm()));
    bpmNameLabel.setText(BpmNameConverter.getName(metronome.getBpm()));
  }


  @Override
  public void show() {
    Gdx.input.setInputProcessor(stage);
    if (firstShow) {
      firstShow = false;
      return;
    }

    table.addAction(Actions.moveBy(stage.getWidth(), 0, 0.2f, Interpolation.exp5Out));
    infoButton.addAction(Actions.moveBy(0, -(margin + infoButton.getHeight()), 0.3f, Interpolation.exp5Out));
  }

  @Override
  public void render(float delta) {
    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void pause() {
    stopMetronome();
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
    metronome.stop();
  }

  private class MetronomeButtonGestureListener extends ActorGestureListener {
    private int bigIncrement;
    private int smallIncrement;

    MetronomeButtonGestureListener(int bigIncrement, int smallIncrement) {
      super(20, 0.4f, 0.5f, 0.15f);
      this.bigIncrement = bigIncrement;
      this.smallIncrement = smallIncrement;
    }

    public boolean longPress(Actor actor, float x, float y) {
      Gdx.input.vibrate(10);
      setBpm(metronome.getBpm() + bigIncrement);

      return true;
    }

    public void tap(InputEvent event, float x, float y, int count, int button) {
      setBpm(metronome.getBpm() + smallIncrement);
    }
  }

  public interface MainScreenDelegate {
    void infoClicked();
  }
}
