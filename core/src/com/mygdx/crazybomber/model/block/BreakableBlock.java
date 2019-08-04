package com.mygdx.crazybomber.model.block;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.crazybomber.model.item.Item;
import com.mygdx.crazybomber.model.item.ItemTypes;

public class BreakableBlock extends Block {
    private Item _item;
    public BreakableBlock(byte x, byte y) {
        super(new Texture("object/breakableblock.png"));
        setXCoord(x);
        setYCoord(y);
        setX(y*48+180);
        setY((14-x)*48);
        setItem(new Item(x, y, ItemTypes.Empty));
    }

    public Item getItem() {
        return _item;
    }

    public void setItem(Item _item) {
        this._item = _item;
    }
}