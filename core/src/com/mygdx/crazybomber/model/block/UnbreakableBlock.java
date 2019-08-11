package com.mygdx.crazybomber.model.block;

import com.badlogic.gdx.graphics.Texture;

public class UnbreakableBlock extends Block {
    public UnbreakableBlock(byte x, byte y) {
        super(new Texture("object/unbreakableblock.png"));
        setXCoord(x);
        setYCoord(y);
        setX(y*48+180);
        setY((14-x)*48);
    }
}
