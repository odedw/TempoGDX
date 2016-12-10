package com.inja.tempogdx.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by oded on 26/11/2016.
 */
public class SkinFactory {
  private static Skin skin;
  public static Skin create() {
    if (skin == null) {
      skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

      //Labels
      FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/DroidSansMono.ttf"));
      FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
      parameter.minFilter = Texture.TextureFilter.Linear;
      parameter.magFilter = Texture.TextureFilter.Linear;
      skin.add("big", generateLabelStyle(generator, parameter, 50), Label.LabelStyle.class);
      skin.add("small", generateLabelStyle(generator, parameter, 30), Label.LabelStyle.class);
      skin.add("default", generateLabelStyle(generator, parameter, 14), Label.LabelStyle.class);
      Label.LabelStyle labelStyle = generateLabelStyle(generator, parameter, 14);
      labelStyle.fontColor = Color.valueOf("4cb4e8");
      skin.add("link", labelStyle, Label.LabelStyle.class);
      generator.dispose();

      //Image Buttons
      skin.add("slower", generateImageButtonStyle("slower", skin));
      skin.add("faster", generateImageButtonStyle("faster", skin));
      skin.add("tap", generateImageButtonStyle("tap", skin));
      ImageButton.ImageButtonStyle imageButtonStyle = generateImageButtonStyle("play", skin);
      imageButtonStyle.imageCheckedOver = Assets.getDrawable("pause", new Color(0.8f, 0.8f, 0.8f, 1f));
      imageButtonStyle.imageChecked = Assets.getDrawable("pause", new Color(1f, 1f, 1f, 1));
      skin.add("play", imageButtonStyle);
      imageButtonStyle = new ImageButton.ImageButtonStyle();
      imageButtonStyle.down = Assets.getDrawable("info", new Color(0.8f, 0.8f, 0.8f, 1));
      imageButtonStyle.up = Assets.getDrawable("info", new Color(1f, 1f, 1f, 1));
      skin.add("info", imageButtonStyle);

      //Text Buttons
      TextButton.TextButtonStyle textButtonStyle = skin.get(TextButton.TextButtonStyle.class);
      textButtonStyle.font = skin.get(Label.LabelStyle.class).font;

      //Constants
      skin.add("button-height", 60);
      skin.add("margin", 15);
      skin.add("ui-width", 240);
    }

    return skin;
  }

  private static Label.LabelStyle generateLabelStyle(FreeTypeFontGenerator generator, FreeTypeFontGenerator.FreeTypeFontParameter parameter, int size) {
    Label.LabelStyle labelStyle = new Label.LabelStyle();
    parameter.size = size;
    labelStyle.font = generator.generateFont(parameter);
    return labelStyle;
  }

  private static ImageButton.ImageButtonStyle generateImageButtonStyle(String drawable, Skin skin) {
    ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle(skin.get(ImageButton.ImageButtonStyle.class));
    imageButtonStyle.imageOver = Assets.getDrawable(drawable, new Color(0.8f, 0.8f, 0.8f, 1));
    imageButtonStyle.imageUp = Assets.getDrawable(drawable, new Color(1f, 1f, 1f, 1));
    return imageButtonStyle;
  }

}
