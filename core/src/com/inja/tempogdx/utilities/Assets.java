package com.inja.tempogdx.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by oded on 30/11/2016.
 */
public class Assets {
  private static AssetManager manager = new AssetManager();

  public static void load() {
    for (FileHandle entry : Gdx.files.internal("textures").list()) {
      manager.load("textures/" + entry.name(), Texture.class);
    }

    for (FileHandle entry : Gdx.files.internal("sounds").list()) {
      if (entry.extension().equals("ogg")) {
        manager.load("sounds/" + entry.name(), Sound.class);
      }
    }
  }

  public static void dispose() {
    manager.dispose();
  }

  public static Drawable getDrawable(String name) {
    return new TextureRegionDrawable(new TextureRegion(manager.get("textures/" + name + ".png", Texture.class)));
  }

  public static Drawable getDrawable(String name, Color color) {
    return new TextureRegionDrawable(new TextureRegion(manager.get("textures/" + name + ".png", Texture.class))).tint(color);
  }

  public static Texture getTexture(String name) {
    return manager.get("textures/" + name + ".png", Texture.class);
  }

  public static Sound getSound(String name) {
    return manager.get("sounds/" + name + ".ogg", Sound.class);
  }
  public static <T> T get(String name, Class<T> clazz){
    return manager.get(name, clazz);
  }

  public static void finishLoading() {
    manager.finishLoading();
  }
}
