package com.mygdx.crazybomber.model.block;

public abstract class Block {
    private byte _x;
    private byte _y;

    public byte getX() {
        return _x;
    }

    public void setX(byte x) {
        this._x = x;
    }

    public byte getY() {
        return _y;
    }

    public void setY(byte y) {
        this._y = y;
    }
}
