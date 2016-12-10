package com.inja.tempogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
  private Table table;

  public InfoScreen(Viewport viewport, InfoScreenDelegate delegate) {
    stage = new Stage(viewport);
    this.delegate = delegate;
    skin = SkinFactory.create();

    createLayout();
  }

  private void createLayout() {
    int buttonHeight = skin.get("button-height", Integer.class);
    int margin = skin.get("margin", Integer.class);
    int uiWidth = skin.get("ui-width", Integer.class);

    table = new Table(skin);
    stage.addActor(table);
    table.center();
    table.setWidth(uiWidth);
    table.setHeight(stage.getHeight());
    table.setX(stage.getWidth() / 2 - table.getWidth() / 2);

    Table infoTable = new Table(skin);
    infoTable.center();
    infoTable.setBackground(skin.getDrawable("list"));
    Label desc = new Label("TempoGDX is an open source metronome built with libGDX. The source code can be found at", skin);
    desc.setWrap(true);
    desc.setAlignment(Align.center);
    infoTable.add(desc).padTop(margin).expandX().fillX().row();
    HyperlinkLabel repo = new HyperlinkLabel("https://github.com/odedw/TempoGDX", skin, "https://github.com/odedw/TempoGDX");
    repo.setAlignment(Align.center);
    infoTable.add(repo).fillX().expandX().padTop(3).padLeft(margin).padRight(margin).row();
    infoTable.add(new Label("Shade UI Skin by ", skin)).padTop(margin*2).row();
    infoTable.add(new HyperlinkLabel("Raymond \"Raeleus\" Buckley", skin, "https://github.com/czyzby/gdx-skins/tree/master/shade")).padTop(3).row();
    infoTable.add(new Label("The font Droid Sans Mono from", skin)).padTop(margin*2).row();
    infoTable.add(new HyperlinkLabel("Google Fonts", skin, "https://fonts.google.com/specimen/Droid+Sans+Mono")).padTop(3).padBottom(margin).row();
    table.add(infoTable).fillX().expandX().row();

    TextButton backButton = new TextButton("Back", skin);
    backButton.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        table.addAction(Actions.sequence(
                Actions.moveBy(stage.getWidth(), 0, 0.2f, Interpolation.exp5In),
                new Action() {
                  @Override
                  public boolean act(float delta) {
                    delegate.backClicked();
                    return true;
                  }
                }

        ));
      }
    });
    table.add(backButton).height(buttonHeight).expandX().fillX().padTop(margin);
    table.moveBy(stage.getWidth(), 0);

  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(stage);
    table.addAction(Actions.moveBy(-stage.getWidth(), 0, 0.2f, Interpolation.exp5Out));
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
