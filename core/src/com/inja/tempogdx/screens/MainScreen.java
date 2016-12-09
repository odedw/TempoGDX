package com.inja.tempogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.inja.tempogdx.Constants;
import com.inja.tempogdx.utilities.Assets;
import com.inja.tempogdx.utilities.BpmNameConverter;
import com.inja.tempogdx.utilities.Metronome;
import com.inja.tempogdx.utilities.SkinFactory;

/**
 * Created by oded on 26/11/2016.
 */
public class MainScreen implements Screen, EventListener {
  private final Stage stage;
  private final Metronome metronome;
  private final Skin skin;
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
    metronome.setListener(this);

    stage = new Stage(viewport);
    skin = SkinFactory.create();

    createLayout();
  }

  private void createLayout() {
    int buttonHeight = skin.get("button-height", Integer.class);
    margin = skin.get("margin", Integer.class);
    final int uiWidth = skin.get("ui-width", Integer.class);

    table = new Table(skin);
    stage.addActor(table);
    table.center();
    table.setWidth(uiWidth);
    table.setHeight(stage.getHeight());
    table.setX(stage.getWidth() / 2 - table.getWidth() / 2);

    Table bpmTable = new Table();
    bpmTable.background(skin.getDrawable("list"));
    bpmLabel = new Label(Integer.toString(metronome.getBpm()), skin, "big");
    bpmTable.add(bpmLabel).height(20).padTop(margin);
    bpmTable.row();
    bpmNameLabel = new Label(BpmNameConverter.getName(metronome.getBpm()), skin, "small");
    bpmTable.add(bpmNameLabel).padTop(margin);
    table.add(bpmTable).colspan(2).height(120).fillX().expandX();
    table.row();

    ImageButton tapButton = new ImageButton(skin, "tap");
    table.add(tapButton).colspan(2).height(buttonHeight).padTop(margin).fillX().expandX();
    table.row();

    slider = new Slider(Constants.MIN_BPM, Constants.MAX_BPM, 1, false, skin);
    slider.setValue(metronome.getBpm());
    slider.setWidth(stage.getWidth() - 20);
    slider.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        setBpm((int) slider.getValue());
      }
    });
    table.add(slider).padTop(margin).colspan(2).fillX().expandX();
    table.row();

    final ImageButton slowerButton = new ImageButton(skin, "slower");
    slowerButton.addListener(new MetronomeButtonGestureListener(-Constants.BIG_INCREMENT, -1));
    table.add(slowerButton).height(buttonHeight).padTop(margin).padRight(10).width((table.getWidth() - 20)/ 2);
    ImageButton fasterButton = new ImageButton(skin, "faster");
    fasterButton.addListener(new MetronomeButtonGestureListener(Constants.BIG_INCREMENT, 1));
    table.add(fasterButton).height(buttonHeight).padTop(margin).padLeft(10).width((table.getWidth() - 20)/ 2);
    table.row();

    startButton = new ImageButton(skin, "play");
    startButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        toggleMetronome();
      }
    });
    table.add(startButton).height(buttonHeight).padTop(20).colspan(2).fillX().expandX();
    table.row();

    Table indicatorTable = new Table();
    indicatorTable.background(skin.getDrawable("list"));
    for (int i = 0; i < 4; i++) {
      Image image = new Image(Assets.getDrawable("beatOff"));
      beatIndicators.add(image);
      indicatorTable.add(image).pad(0, margin, 0, margin).width(20).height(20);
    }
    table.add(indicatorTable).colspan(2).padTop(20).height(50).fillX().expandX();
    table.row();

    infoButton = new ImageButton(skin, "info");
    infoButton.setWidth(20);
    infoButton.setHeight(20);
    infoButton.setX(stage.getWidth() - margin - infoButton.getWidth());
    infoButton.setY(stage.getHeight() - margin - infoButton.getHeight());
    infoButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        infoButton.addAction(Actions.moveBy(0, margin + infoButton.getHeight(), 0.3f, Interpolation.exp5In));
        table.addAction(Actions.sequence(
                Actions.moveBy(-stage.getWidth(), 0, 0.3f, Interpolation.exp5In),
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
  }

  private void toggleMetronome() {
    if (startButton.isChecked()) {
      metronome.start();
    } else {
      if (currentBeatIndicator >= 0 && currentBeatIndicator < beatIndicators.size) {
        beatIndicators.get(currentBeatIndicator).setDrawable(Assets.getDrawable("beatOff"));
      }
      currentBeatIndicator = -1;
      metronome.stop();
    }
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

    table.addAction(Actions.moveBy(stage.getWidth(), 0, 0.3f, Interpolation.exp5Out));
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
  public boolean handle(Event event) {
    if (currentBeatIndicator >= 0 && currentBeatIndicator < beatIndicators.size)
      beatIndicators.get(currentBeatIndicator).setDrawable(Assets.getDrawable("beatOff"));

    currentBeatIndicator++;
    if (currentBeatIndicator == beatIndicators.size) currentBeatIndicator = 0;
    Assets.getSound(currentBeatIndicator == 0 ? "clickFirst" : "click").play();
    beatIndicators.get(currentBeatIndicator).setDrawable(Assets.getDrawable("beatOn"));

    return true;
  }

  private class MetronomeButtonGestureListener extends ActorGestureListener {
    private int bigIncrement;
    private int smallIncrement;

    MetronomeButtonGestureListener(int bigIncrement, int smallIncrement) {
      super(20, 0.4f, 0.5f, 0.15f);
      this.bigIncrement = bigIncrement;
      this.smallIncrement = smallIncrement;
    }

    public boolean longPress (Actor actor, float x, float y) {
      Gdx.input.vibrate(10);
      setBpm(metronome.getBpm() + bigIncrement);

      return true;
    }
    public void tap (InputEvent event, float x, float y, int count, int button) {
      setBpm(metronome.getBpm() + smallIncrement);
    }
  }

  public interface MainScreenDelegate{
    void infoClicked();
  }
}
