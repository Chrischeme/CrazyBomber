package com.mygdx.crazybomber.model.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Item extends Sprite {
    private byte _x;
    private byte _y;
    private ItemTypes _itemType;
    public Item(byte x, byte y, ItemTypes itemType) {
        setXCoord(x);
        setYCoord(y);
        setX(x*48);
        setY(y*48);
        _itemType = itemType;
        this.setTexture(new Texture("object/item.png"));
    }

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

    public ItemTypes getItemType() {
        return _itemType;
    }

    public void setItemType(ItemTypes _itemType) {
        this._itemType = _itemType;
    }

}
