package com.mygdx.crazybomber;
import com.badlogic.gdx.Game;
import com.mygdx.crazybomber.ui.SplashScreen;

public class CrazyBomber extends Game {
    public static final String TITLE = "CrazyBomber";
    @Override
    public void create() {
        setScreen(new SplashScreen());
    }
}