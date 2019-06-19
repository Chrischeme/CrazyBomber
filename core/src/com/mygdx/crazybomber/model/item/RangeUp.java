package com.mygdx.crazybomber.model.item;

public class RangeUp extends Item {
    public RangeUp(int blockXCoordinate, int blockYCoordinate){
        setDropChance(0.1);
        setXCoordinate(blockXCoordinate);
        setYCoordinate(blockYCoordinate);
    }
}
