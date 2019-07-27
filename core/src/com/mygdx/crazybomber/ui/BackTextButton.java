package com.mygdx.crazybomber.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class BackTextButton extends TextButton {
    public BackTextButton(Skin skin, final Repository repository) {
        super("BACK", skin);
        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(repository.getListOfStates().pop());
            }
        });
    }
}
