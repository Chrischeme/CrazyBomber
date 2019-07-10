package com.mygdx.crazybomber.model.item;

public class SpeedUp extends Item {
    public SpeedUp(int blockXCoordinate, int blockYCoordinate, byte itemId) {
        setXCoordinate(blockXCoordinate);
        setYCoordinate(blockYCoordinate);
        setItemID(itemId);
        setItemType((byte) 2);
    }
}
