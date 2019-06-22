package com.mygdx.bomberbois;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx; //using this to log whats going on
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BombermanWithFriends extends Game {
	public static final String TITLE = "Bomberman";
	@Override
	public void create() {
		setScreen(new LoadScreen());
	}
}
