package com.mygdx.crazybomber.model.block;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public abstract class Block extends Sprite {
    public Block(Texture texture)
    {
        super(texture);
    }
    public Block()
    {

    }
    private byte _x; // change the name & getXcoord
    private byte _y; // delete the fields and methods, cast it as a byte

    public byte getXCoord() {
        return _x;
    }

    public void setXCoord(byte x) {
        this._x = x;
    }

    public byte getYCoord() {
        return _y;
    }

    public void setYCoord(byte y) {
        this._y = y;
    }
}
