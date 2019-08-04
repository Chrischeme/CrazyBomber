package com.mygdx.crazybomber.model.block;

import com.badlogic.gdx.graphics.Texture;

public class UnbreakableBlock extends Block {
    public UnbreakableBlock(byte x, byte y) {
        setXCoord(x);
        setYCoord(y);
        setX(x*48);
        setY(y*48);
        this.setTexture(new Texture("object/unbreakableblock.png"));
    }
}
