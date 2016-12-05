package com.inja.tempogdx.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.inja.tempogdx.utilities.SkinFactory;

/**
 * Created by oded on 05/12/2016.
 */
public class InfoScreen implements Screen {
  private Skin skin;
  private Stage stage;
  private InfoScreenDelegate delegate;

  public InfoScreen(Viewport viewport, InfoScreenDelegate delegate) {
    stage = new Stage(viewport);
    this.delegate = delegate;
    skin = SkinFactory.create();

    createLayout();
  }

  private void createLayout() {
    int buttonHeight = skin.get("button-height", Integer.class);
    int margin = skin.get("margin", Integer.class);
    int stageWidth = skin.get("stage-width", Integer.class);

    Table table = new Table(skin);
    stage.addActor(table);
    table.center();
    table.setWidth(stageWidth);
    table.setHeight(stage.getHeight());
    table.setX(stage.getWidth() / 2 - table.getWidth() / 2);

    table.add(new Label("test", skin));

  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {

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

  }

  public interface InfoScreenDelegate {
    void backClicked();
  }
}
