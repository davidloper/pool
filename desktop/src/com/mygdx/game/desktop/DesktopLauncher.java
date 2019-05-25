package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Pool;

import static com.mygdx.game.Pool.TILE_SCALE;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 16 * 24 * (int) TILE_SCALE;
		config.height = 16 * 12 * (int) TILE_SCALE;
		new LwjglApplication(new Pool(), config);
	}
}
