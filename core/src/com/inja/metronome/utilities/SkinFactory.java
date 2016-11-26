package com.inja.metronome.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

/**
 * Created by oded on 26/11/2016.
 */
public class SkinFactory {
  public static Skin create() {
    Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    parameter.size = 45;
    Label.LabelStyle labelStyle = new Label.LabelStyle();
    labelStyle.font = generator.generateFont(parameter);
    skin.add("default", labelStyle, Label.LabelStyle.class);
    generator.dispose();

    return skin;
  }
}
