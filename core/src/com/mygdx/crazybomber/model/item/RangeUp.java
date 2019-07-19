package com.mygdx.crazybomber.model.item;

public class RangeUp extends Item {
    public RangeUp(int blockXCoordinate, int blockYCoordinate, byte itemId){
        setXCoordinate(blockXCoordinate);
        setYCoordinate(blockYCoordinate);
        setItemID(itemId);
        setItemType((byte) 1);
    }
}
