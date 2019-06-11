package com.mygdx.crazybomber.model.player;

import com.mygdx.crazybomber.model.bomb.Bomb;

import java.util.Stack;

public class Player {
    private boolean _isKnockedUp;
    private boolean _isAlive;
    private int _numBombs;
    private int _rangeBombs;
    private double _speed;
    private boolean onItem;
    private double _xCoordinate;
    private double _yCoordinate;
    private Stack<Bomb> _bombStack;


    public double getXCoordinate() {
        return _xCoordinate;
    }

    public void setXCoordinate(double _xCoordinate) {
        this._xCoordinate = _xCoordinate;
    }

    public double getYCoordinate() {
        return _yCoordinate;
    }

    public void setYCoordinate(double _yCoordinate) {
        this._yCoordinate = _yCoordinate;
    }

    public double getSpeed() {
        return _speed;
    }

    public void setSpeed(double _speed) {
        this._speed = _speed;
    }

    public boolean getIsKnockedUp() {
        return _isKnockedUp;
    }

    public void setIsKnockedUp(boolean _isKnockedUp) {
        this._isKnockedUp = _isKnockedUp;
    }

    public boolean getIsAlive() {
        return _isAlive;
    }

    public void setIsAlive(boolean _isAlive) {
        this._isAlive = _isAlive;
    }

    public int getNumBombs() {
        return _numBombs;
    }

    public void setNumBombs(int _numBombs) {
        this._numBombs = _numBombs;
    }

    public boolean getIsOnItem() {
        return onItem;
    }

    public void setOnItem(boolean onItem) {
        this.onItem = onItem;
    }

    public void pickUpItem(int itemXCoordinate, int itemYCoordinate) {
    }

    public int getRangeBombs() {
        return _rangeBombs;
    }

    public void setRangeBombs(int _rangeBombs) {
        this._rangeBombs = _rangeBombs;
    }

    public void move(char direction) {
        switch (direction) {
            case 'W':
                setYCoordinate(getYCoordinate() + getSpeed());
                break;
            case 'A':
                setXCoordinate(getXCoordinate() - getSpeed());
                break;
            case 'S':
                setYCoordinate(getYCoordinate() - getSpeed());
                break;
            case 'D':
                setXCoordinate(getXCoordinate() + getSpeed());
                break;
        }
    }

    public Bomb dropBomb() {
        Bomb droppedBomb =_bombStack.pop();
        droppedBomb.setRangeBombs(getRangeBombs());
        droppedBomb.setXCoordinate((int)getXCoordinate());
        droppedBomb.setYCoordinate((int)getYCoordinate());
        return droppedBomb;
    }

    public Player(double playerSpawnXCoordinate, double playerSpawnYCoordinate) {
        Stack<Bomb> bombStack = new Stack<Bomb>();
        setIsAlive(false);
        setIsKnockedUp(false);
        setNumBombs(1);
        setOnItem(false);
        setSpeed(1.5);
        setXCoordinate(playerSpawnXCoordinate);
        setYCoordinate(playerSpawnYCoordinate);
        Bomb bomb = new Bomb((int) getXCoordinate(), (int) getYCoordinate(), getRangeBombs());
        _bombStack.push(bomb);
    }


}
