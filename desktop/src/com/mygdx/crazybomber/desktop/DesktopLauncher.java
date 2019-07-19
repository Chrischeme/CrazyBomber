package com.mygdx.crazybomber.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.crazybomber.CrazyBomber;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Bomberman";
		config.width = 1080;
		config.height = 720;
		config.vSyncEnabled = true; //syncing framerate with update, lower frames = less cpu
		new LwjglApplication(new CrazyBomber(), config);
	}
}
