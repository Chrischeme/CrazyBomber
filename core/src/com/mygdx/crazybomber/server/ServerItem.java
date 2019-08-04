package com.mygdx.crazybomber.server;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.crazybomber.model.item.ItemTypes;

public class ServerItem extends Sprite{
        private byte _x;
        private byte _y;
        private ItemTypes _itemType;
        public ServerItem(byte x, byte y, ItemTypes itemType) {
            setXCoord(x);
            setYCoord(y);
            setX(x*48);
            setY(y*48);
            _itemType = itemType;
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