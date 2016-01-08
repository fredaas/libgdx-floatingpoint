package com.fredaas.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.fredaas.game.Game;

public class DesktopLauncher {
    
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = 350;
		cfg.height = 620;
		new LwjglApplication(new Game(), cfg);
	}
	
}
