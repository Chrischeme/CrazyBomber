package com.mygdx.crazybomber;
import com.badlogic.gdx.Game;

public class CrazyBomber extends Game {
    public static final String TITLE = "CrazyBomber";
    @Override
    public void create() {
        setScreen(new LoadScreen());
    }
}