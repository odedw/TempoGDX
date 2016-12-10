package com.inja.tempogdx.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HyperlinkLabel extends Label{
  public HyperlinkLabel(CharSequence text, Skin skin, final String url) {
    super(text, skin, "link");
    this.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        Gdx.net.openURI(url);
      }
    });
  }
}
