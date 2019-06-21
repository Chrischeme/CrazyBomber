package com.mygdx.crazybomber.model.block;

import com.mygdx.crazybomber.model.item.BombUp;
import com.mygdx.crazybomber.model.item.Item;
import com.mygdx.crazybomber.model.item.RangeUp;
import com.mygdx.crazybomber.model.item.SpeedUp;

public class BreakableBlock extends Block {
    private Item _item;
    private double randNum;

    public BreakableBlock(int xCoordinate, int yCoordinate) {
        setXCoordinate(xCoordinate);
        setYCoordinate(yCoordinate);
        randNum = Math.random();
        if (randNum < 0.066) {
            _item = new BombUp(getXCoordinate(), getYCoordinate());
        } else if (randNum < 0.132) {
            _item = new RangeUp(getXCoordinate(), getYCoordinate());
        } else if (randNum < 0.20) {
            _item = new SpeedUp(getXCoordinate(), getYCoordinate());
        } else {
            _item = null;
        }


    }
}
