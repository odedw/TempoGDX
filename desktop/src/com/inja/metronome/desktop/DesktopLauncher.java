package com.inja.metronome.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.inja.metronome.MetronomeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = 1280;
        config.width = 720;
        config.samples = 8;
		new LwjglApplication(new MetronomeGame(), config);
	}
}
