package com.mygdx.crazybomber.model.block;

import com.mygdx.crazybomber.model.item.Item;
import com.mygdx.crazybomber.model.item.ItemTypes;

public class BreakableBlock extends Block {
    private Item _item;

    public BreakableBlock(byte x, byte y) {
        setX(x);
        setY(y);
        setItem(new Item(x, y, ItemTypes.Empty));
    }

    public Item getItem() {
        return _item;
    }

    public void setItem(Item _item) {
        this._item = _item;
    }
}