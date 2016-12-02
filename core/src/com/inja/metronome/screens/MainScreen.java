package com.inja.metronome.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.inja.metronome.Constants;
import com.inja.metronome.utilities.Assets;
import com.inja.metronome.utilities.BpmNameConverter;
import com.inja.metronome.utilities.Metronome;
import com.inja.metronome.utilities.SkinFactory;

/**
 * Created by oded on 26/11/2016.
 */
public class MainScreen implements Screen, EventListener {
  private final FitViewport viewport;
  private final Stage stage;
  private final Metronome metronome;
  private final Skin skin;
  private ImageButton startButton;
  private Slider slider;
  private Label bpmLabel;
  private SpriteBatch batch;
  private Texture background = Assets.getTexture("background");
  private Label bpmNameLabel;
  private Array<Image> beatIndicators = new Array<>();
  private Drawable beatOffImage;
  private Drawable beatOnImage;
  private int currentBeatIndicator = -1;
  private long silenceLoopId;


  public MainScreen(SpriteBatch batch) {
    background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    metronome = new Metronome();
    metronome.setListener(this);
    viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
    viewport.apply();
    stage = new Stage(viewport);
    skin = SkinFactory.create();

    createLayout();
    this.batch = batch;
  }

  private void createLayout() {

    Table table = new Table(skin);
    stage.addActor(table);
    table.setFillParent(true);
    table.align(Align.center | Align.top);
    table.padTop(60);

    Table bpmTable = new Table();
    bpmTable.background(skin.getDrawable("list"));
    bpmLabel = new Label(Integer.toString(metronome.getBpm()), skin);
    bpmTable.add(bpmLabel);
    bpmTable.row();
    bpmNameLabel = new Label(BpmNameConverter.getName(metronome.getBpm()), skin, "small");
    bpmTable.add(bpmNameLabel).padTop(10);
    table.add(bpmTable).colspan(4).width(180).height(120);
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
    table.add(slider).width(180).padTop(10).colspan(4);
    table.row();

    final ImageButton slowerButton = new ImageButton(skin, "slower");
    slowerButton.addListener(new MetronomeButtonGestureListener(-Constants.BIG_INCREMENT, -1));
    table.add(slowerButton).width(80).height(60).padTop(10).padRight(10).colspan(2);
    ImageButton fasterButton = new ImageButton(skin, "faster");
    fasterButton.addListener(new MetronomeButtonGestureListener(Constants.BIG_INCREMENT, 1));
    table.add(fasterButton).width(80).height(60).padTop(10).padLeft(10).colspan(2);
    table.row();

    startButton = new ImageButton(skin, "play");
    startButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        toggleMetronome();
      }
    });
    table.add(startButton).width(180).height(70).padTop(20).colspan(4);
    table.row();

    Table indicatorTable = new Table();
    indicatorTable.background(skin.getDrawable("list"));
    beatOffImage = skin.getDrawable("radio-button-off");
    beatOnImage = skin.getDrawable("radio-button");
    for (int i = 0; i < 4; i++) {
      Image image = new Image(beatOffImage);
      beatIndicators.add(image);
      indicatorTable.add(image).pad(0, 15, 0, 15).width(15);
    }
//  indicatorTable.setDebug(true);
    table.add(indicatorTable).colspan(4).width(180).padTop(20).height(50);

    table.row();

//    table.setDebug(true);
  }

  private void toggleMetronome() {
    if (startButton.isChecked()) {
      metronome.start();
    } else {
      beatIndicators.get(currentBeatIndicator).setDrawable(beatOffImage);
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
    silenceLoopId = Assets.getSound("silence").loop();
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0,0,0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.begin();
    batch.draw(background, 0, 0, viewport.getScreenWidth(), viewport.getScreenHeight());
    batch.end();
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
    Assets.getSound("silence").stop(silenceLoopId);
  }

  @Override
  public void dispose() {
    stage.dispose();
  }

  @Override
  public boolean handle(Event event) {
    if (currentBeatIndicator >= 0 && currentBeatIndicator < beatIndicators.size)
      beatIndicators.get(currentBeatIndicator).setDrawable(beatOffImage);

    currentBeatIndicator++;
    if (currentBeatIndicator == beatIndicators.size) currentBeatIndicator = 0;
    Assets.getSound(currentBeatIndicator == 0 ? "clickFirst" : "click").play();
    beatIndicators.get(currentBeatIndicator).setDrawable(beatOnImage);

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
}
