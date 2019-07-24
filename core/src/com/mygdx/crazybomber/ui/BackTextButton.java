package com.mygdx.crazybomber.ui;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class BackTextButton extends TextButton {
    public BackTextButton(Skin skin) {
        super("BACK", skin);
        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(repository.getRoom(j));
            }
        });
    }
}
