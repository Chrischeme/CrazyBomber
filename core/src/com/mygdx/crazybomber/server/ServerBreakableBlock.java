package com.mygdx.crazybomber.server;

import com.mygdx.crazybomber.model.block.Block;
import com.mygdx.crazybomber.model.item.Item;
import com.mygdx.crazybomber.model.item.ItemTypes;

public class ServerBreakableBlock extends Block {
    private Item _item;

    public ServerBreakableBlock(int xCoordinate, int yCoordinate) {
        setXCoordinate(xCoordinate);
        setYCoordinate(yCoordinate);
        setItem(null);
    }

    public ServerBreakableBlock(int xCoordinate, int yCoordinate, byte itemID) {
        setXCoordinate(xCoordinate);
        setYCoordinate(yCoordinate);
        double randNum = Math.random();
        if (randNum <= 0.1) {
            setItem(new Item(getXCoordinate(), getYCoordinate(), itemID, ItemTypes.BombUp));
        } else if (randNum <= 0.2) {
            setItem(new Item(getXCoordinate(), getYCoordinate(), itemID, ItemTypes.RangeUp));
        } else if (randNum <= 0.30) {
            setItem(new Item(getXCoordinate(), getYCoordinate(), itemID, ItemTypes.SpeedUp));
        } else {
            setItem(null);
        }
    }

    public Item getItem() {
        return _item;
    }

    public void setItem(Item _item) {
        this._item = _item;
    }
}