package com.mygdx.crazybomber.model.block;

import com.mygdx.crazybomber.model.item.Item;
import com.mygdx.crazybomber.model.item.ItemTypes;

public class BreakableBlock extends Block {
    private Item _item;

    public BreakableBlock(int xCoordinate, int yCoordinate) {
        setXCoordinate(xCoordinate);
        setYCoordinate(yCoordinate);
        setItem(new Item(xCoordinate, yCoordinate, (byte) 0, ItemTypes.Empty));
    }

    public Item getItem() {
        return _item;
    }

    public void setItem(Item _item) {
        this._item = _item;
    }
}