package com.mygdx.crazybomber.model.item;

public class BombUp extends Item {
    public BombUp(int blockXCoordinate, int blockYCoordinate, byte itemId) {
        setXCoordinate(blockXCoordinate);
        setYCoordinate(blockYCoordinate);
        setItemID(itemId);
        setItemType((byte) 0);
    }
}
