package com.fredaas.game.android;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.fredaas.game.Game;
import android.os.Bundle;

public class AndroidLauncher extends AndroidApplication {
    
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		initialize(new Game(), cfg);
	}
	
}
