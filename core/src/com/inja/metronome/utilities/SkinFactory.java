package com.inja.metronome.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by oded on 26/11/2016.
 */
public class SkinFactory {
  public static Skin create() {
    Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("DroidSansMono.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    parameter.size = 40;
    Label.LabelStyle labelStyle = new Label.LabelStyle();
    labelStyle.font = generator.generateFont(parameter);
    skin.add("default", labelStyle, Label.LabelStyle.class);
    labelStyle = new Label.LabelStyle();
    parameter.size = 20;
    labelStyle.font = generator.generateFont(parameter);
    skin.add("small", labelStyle, Label.LabelStyle.class);
    generator.dispose();

    ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle(skin.get(ImageButton.ImageButtonStyle.class));
    imageButtonStyle.imageOver = Assets.getDrawable("slower", new Color(0.8f, 0.8f, 0.8f, 1));
    imageButtonStyle.imageUp  = Assets.getDrawable("slower", new Color(1f, 1f, 1f, 1));
    skin.add("slower", imageButtonStyle);
    imageButtonStyle = new ImageButton.ImageButtonStyle(skin.get(Button.ButtonStyle.class));
    imageButtonStyle.imageOver = Assets.getDrawable("faster", new Color(0.8f, 0.8f, 0.8f, 1));
    imageButtonStyle.imageUp = Assets.getDrawable("faster", new Color(1f, 1f, 1f, 1));
    skin.add("faster", imageButtonStyle);
    imageButtonStyle = new ImageButton.ImageButtonStyle(skin.get(Button.ButtonStyle.class));
    imageButtonStyle.imageOver = Assets.getDrawable("play", new Color(0.8f, 0.8f, 0.8f, 1f));
    imageButtonStyle.imageUp = Assets.getDrawable("play", new Color(1f, 1f, 1f, 1f));
    imageButtonStyle.imageCheckedOver = Assets.getDrawable("pause", new Color(0.8f, 0.8f, 0.8f, 1f));
    imageButtonStyle.imageChecked = Assets.getDrawable("pause", new Color(1f,1f, 1f, 1));

    skin.add("play", imageButtonStyle);
    return skin;
  }
}
