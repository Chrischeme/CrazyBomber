package com.mygdx.crazybomber.model.item;

public class SpeedUp extends Item{
    public SpeedUp(int blockXCoordinate, int blockYCoordinate){
        setDropChance(0.1);
        setXCoordinate(blockXCoordinate);
        setYCoordinate(blockYCoordinate);
    }
}
