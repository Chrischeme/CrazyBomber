package com.mygdx.crazybomber.model.item;

public class BombUp extends Item {
    public BombUp(int blockXCoordinate, int blockYCoordinate){
        setDropChance(0.1);
        setXCoordinate(blockXCoordinate);
        setYCoordinate(blockYCoordinate);
    }
}
