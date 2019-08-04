package com.mygdx.crazybomber.server;

import com.mygdx.crazybomber.model.block.Block;
import com.mygdx.crazybomber.model.item.Item;
import com.mygdx.crazybomber.model.item.ItemTypes;

public class ServerBreakableBlock extends Block {
    private ServerItem _item;

    public ServerBreakableBlock(byte x, byte y, byte itemID) {
        setXCoord(x);
        setYCoord(y);
        double randNum = Math.random();
        if (randNum <= 0.1) {
            setItem(new ServerItem(getXCoord(), getYCoord(), ItemTypes.BombUp));
        } else if (randNum <= 0.2) {
            setItem(new ServerItem(getXCoord(), getYCoord(), ItemTypes.RangeUp));
        } else if (randNum <= 0.30) {
            setItem(new ServerItem(getXCoord(), getYCoord(), ItemTypes.SpeedUp));
        } else {
            setItem(new ServerItem(getXCoord(), getYCoord(), ItemTypes.Empty));
        }
    }

    public ServerItem getItem() {
        return _item;
    }

    public void setItem(ServerItem _item) {
        this._item = _item;
    }
}