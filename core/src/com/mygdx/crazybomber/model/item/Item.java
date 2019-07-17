package com.mygdx.crazybomber.model.item;

public abstract class Item {
    private int _xCoordinate;
    private int _yCoordinate;
    private double _dropChance;
    private byte _itemId;
    private byte _itemType;

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

    public double getDropChance() {
        return _dropChance;
    }

    public void setDropChance(double _dropChance) {
        this._dropChance = _dropChance;
    }

    public byte getItemID() {
        return _itemId;
    }

    public void setItemID(byte _itemID) {
        this._itemId = _itemID;
    }

    public byte getItemType() {
        return _itemType;
    }

    public void setItemType(byte _itemType) {
        this._itemType = _itemType;
    }

}
