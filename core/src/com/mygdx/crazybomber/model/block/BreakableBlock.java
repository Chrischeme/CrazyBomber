package com.mygdx.crazybomber.model.block;

import com.mygdx.crazybomber.model.item.BombUp;
import com.mygdx.crazybomber.model.item.Item;
import com.mygdx.crazybomber.model.item.RangeUp;
import com.mygdx.crazybomber.model.item.SpeedUp;

public class BreakableBlock extends Block {
    private Item _item;

    public BreakableBlock(int xCoordinate, int yCoordinate) {
        setXCoordinate(xCoordinate);
        setYCoordinate(yCoordinate);
        setItem(null);
    }

    public BreakableBlock(int xCoordinate, int yCoordinate, byte itemID) {
        setXCoordinate(xCoordinate);
        setYCoordinate(yCoordinate);
        double randNum = Math.random();
        if (randNum < 0.066) {
            setItem(new BombUp(getXCoordinate(), getYCoordinate(), itemID));
        } else if (randNum < 0.132) {
            setItem(new RangeUp(getXCoordinate(), getYCoordinate(), itemID));
        } else if (randNum < 0.20) {
            setItem(new SpeedUp(getXCoordinate(), getYCoordinate(), itemID));
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