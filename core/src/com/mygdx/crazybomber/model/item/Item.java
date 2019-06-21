package com.mygdx.crazybomber.model.item;

public abstract class Item {
    private int _xCoordinate;
    private int _yCoordinate;
    private double _dropChance;

    public int getSCoordinate() {
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

    public double getDropChance() {
        return _dropChance;
    }

    public void setDropChance(double _dropChance) {
        this._dropChance = _dropChance;
    }
}
