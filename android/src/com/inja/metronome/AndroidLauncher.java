package com.inja.tempogdx;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.inja.tempogdx.TempoGdxGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.numSamples = 2;
        config.useCompass = config.useAccelerometer = false;
		initialize(new TempoGdxGame(), config);
	}
}
