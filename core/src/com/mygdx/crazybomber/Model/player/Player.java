package com.mygdx.crazybomber.model.player;

public class Player {
    private boolean _isKnockedUp;
    private boolean _isAlive;
    private int _numBombs;
    private double _speed;
    private boolean onItem;
    private double _xCoordinate;
    private double _yCoordinate;


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

    public void setSpeed(int _speed) {
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
}
