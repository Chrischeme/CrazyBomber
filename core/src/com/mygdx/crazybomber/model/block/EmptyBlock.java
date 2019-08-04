package com.mygdx.crazybomber.model.block;

public class EmptyBlock extends Block {
    public EmptyBlock(byte x, byte y) {
        setXCoord(x);
        setYCoord(y);
        setX(x*48);
        setY(y*48);
    }
}
