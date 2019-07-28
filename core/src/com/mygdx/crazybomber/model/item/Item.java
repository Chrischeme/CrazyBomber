package com.mygdx.crazybomber.model.item;

public class Item {
    private byte _x;
    private byte _y;
    private ItemTypes _itemType;

    public Item(byte x, byte y, ItemTypes itemType) {
        setX(x);
        setY(y);
        _itemType = itemType;
    }

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

    public ItemTypes getItemType() {
        return _itemType;
    }

    public void setItemType(ItemTypes _itemType) {
        this._itemType = _itemType;
    }

}
