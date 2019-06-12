package com.mygdx.crazybomber.model.bomb;

import java.util.Stack;

public class Bomb {
    private int _rangeBombs;
    private int _xCoordinate;
    private int _yCoordinate;
    private Stack<Bomb> _bombStack;

    public void explode() {
    }

    public Bomb(int playerXCoordinate, int playerYCoordinate, int rangeBombs, Stack<Bomb> bombStack) {
        setXCoordinate(playerXCoordinate);
        setYCoordinate(playerYCoordinate);
        setRangeBombs(rangeBombs);
        _bombStack = bombStack;

    }

    public int getRangeBombs() {
        return _rangeBombs;
    }

    public void setRangeBombs(int _rangeBombs) {
        this._rangeBombs = _rangeBombs;
    }

    public int getXCoordinate() {
        return _xCoordinate;
    }

    public void setXCoordinate(int _xCoordinate) {
        this._xCoordinate = _xCoordinate;
    }

    public int getYCoordinate() {
        return _yCoordinate;
    }

    public void setYCoordinate(int _yCoordinate) {
        this._yCoordinate = _yCoordinate;
    }

}
