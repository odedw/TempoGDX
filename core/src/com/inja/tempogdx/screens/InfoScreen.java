package com.inja.tempogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.inja.tempogdx.utilities.HyperlinkLabel;
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

    Table infoTable = new Table(skin);
    infoTable.center();
    infoTable.setBackground(skin.getDrawable("list"));
    Label desc = new Label("TempoGDX is an open source metronome built with libGDX. The code is at", skin);
    desc.setWrap(true);
    desc.setAlignment(Align.center);
    infoTable.add(desc).padTop(margin).expandX().fillX().colspan(2);
    infoTable.row();
    HyperlinkLabel repo = new HyperlinkLabel("https://github.com/odedw/TempoGDX", skin, "https://github.com/odedw/TempoGDX");
    repo.setAlignment(Align.center);
    infoTable.add(repo).colspan(2).fillX().expandX().padTop(3);
    infoTable.row();
    Label skinCredit = new Label("UI Skin by ", skin);
    infoTable.add(skinCredit).padTop(margin).padLeft(margin).padBottom(margin);
    HyperlinkLabel skinUrl = new HyperlinkLabel("Raymond \"Raeleus\" Buckley", skin, "https://github.com/czyzby/gdx-skins/tree/master/shade");
    infoTable.add(skinUrl).padTop(margin).padRight(margin).padBottom(margin);
    table.add(infoTable).fillX().expandX();
    table.row();

    TextButton backButton = new TextButton("Back", skin);
    backButton.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        delegate.backClicked();
      }
    });
    table.add(backButton).height(buttonHeight).expandX().fillX().padTop(margin);

  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(stage);
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

  }

  public interface InfoScreenDelegate {
    void backClicked();
  }
}
