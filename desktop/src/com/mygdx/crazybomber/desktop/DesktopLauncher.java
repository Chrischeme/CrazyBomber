package com.mygdx.bomberbois.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.bomberbois.BombermanWithFriends;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration(); //java game
		config.title = "Bomberman";
		config.width = 1280;
		config.height = 720;
		config.vSyncEnabled = true; //syncing framerate with update, lower frames = less cpu
		new LwjglApplication(new BombermanWithFriends(), config);
	}
}
