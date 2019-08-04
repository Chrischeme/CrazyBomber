package com.mygdx.crazybomber.model.block;

import com.badlogic.gdx.graphics.Texture;

public class UnbreakableBlock extends Block {
    private Texture texture = new Texture("object/unbreakableblock.png");
    public UnbreakableBlock(byte x, byte y) {
        setXCoord(x);
        setYCoord(y);
        setX(x*48);
        setY(y*48);
    }
}
