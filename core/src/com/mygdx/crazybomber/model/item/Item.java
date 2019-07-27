package com.mygdx.crazybomber.model.item;

public class Item {
    private int _xCoordinate;
    private int _yCoordinate;
    private double _dropChance;
    private byte _itemId;
    private ItemTypes _itemType;

    public Item(int blockXCoordinate, int blockYCoordinate,ItemTypes itemType) {
        _xCoordinate = blockXCoordinate;
        _yCoordinate = blockYCoordinate;
        _itemType = itemType;
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

    public double getDropChance() {
        return _dropChance;
    }

    public void setDropChance(double _dropChance) {
        this._dropChance = _dropChance;
    }

    public ItemTypes getItemType() {
        return _itemType;
    }

    public void setItemType(ItemTypes _itemType) {
        this._itemType = _itemType;
    }

}
