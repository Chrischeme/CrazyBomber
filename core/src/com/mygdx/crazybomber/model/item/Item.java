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
        setX(y*48+180);
        setY((14-x)*48);
        _itemType = itemType;
        switch (itemType) {
            case BombUp:
                this.setTexture(new Texture("object/itembomb.png"));
                break;
            case RangeUp:
                this.setTexture(new Texture("object/itemrange.png"));
                break;
            case SpeedUp:
                this.setTexture(new Texture("object/itemspeed.png"));
                break;
            default:
        }
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
