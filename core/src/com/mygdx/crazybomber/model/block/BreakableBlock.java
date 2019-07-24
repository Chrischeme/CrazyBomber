package com.mygdx.crazybomber.model.block;

import com.mygdx.crazybomber.model.item.Item;

public class BreakableBlock extends Block {
    private Item _item;

    public BreakableBlock(int xCoordinate, int yCoordinate) {
        setXCoordinate(xCoordinate);
        setYCoordinate(yCoordinate);
        setItem(null);
    }

    public Item getItem() {
        return _item;
    }

    public void setItem(Item _item) {
        this._item = _item;
    }
}