package com.inja.tempogdx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.inja.tempogdx.TempoGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = 1280;
        config.width = 720;
        config.samples = 8;
		new LwjglApplication(new TempoGdxGame(), config);
	}
}
