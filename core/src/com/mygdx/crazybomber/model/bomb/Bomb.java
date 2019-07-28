package com.mygdx.crazybomber.model.bomb;

import com.mygdx.crazybomber.model.player.Player;
import com.mygdx.crazybomber.model.map.Map;

import java.util.Stack;

public class Bomb {
    private int _rangeBomb;
    private int _xCoordinate;
    private int _yCoordinate;
    private Stack<Bomb> _bombStack;
    private Map _map;
    private Player _bombOwner;

    public Bomb(Player player, Stack<Bomb> bombStack) {
        setXCoordinate(Math.round(player.getX()));
        setYCoordinate(Math.round(player.getY()));
        setRangeBomb(player.getNumRangeUpgrades() + 1);
        _bombStack = bombStack;
        _map = player.getMap();
        _bombOwner = player;
    }

    public Bomb(int x, int y){
        setXCoordinate(x);
        setYCoordinate(y);
    }

    public int getRangeBomb() {
        return _rangeBomb;
    }

    public void setRangeBomb(int _rangeBombs) {
        this._rangeBomb = _rangeBombs;
    }

    public Stack<Bomb> getBombStack() {
        return _bombStack;
    }

    public int getXCoordinate() {
        return _xCoordinate;
    }

    public void setXCoordinate(int _xCoordinate) {
        this._xCoordinate = _xCoordinate;
    }

    public int getYCoordinate() {
        return _yCoordinate;
    }

    public void setYCoordinate(int _yCoordinate) {
        this._yCoordinate = _yCoordinate;
    }

    public Map getMap() {
        return _map;
    }

    public void explode() {
        //todo to factor in walls
        //todo System.out.println("bomb x coordinate is " + getXCoordinate());
        //todo System.out.println("bomb y coordinate is " + getYCoordinate());
        //todo System.out.println("bomb exploded at " + (double)System.nanoTime()/(1000000000));

        getMap().getActiveBombArray().remove(this);
        getBombStack().push(this);
        explodeBreakableBlocksInRange();
        explodeBombsInRange();
    }

    public void explodeBombsInRange(){
        int explodedBombXCoordinate = getXCoordinate();
        int explodedBombYCoordinate = getYCoordinate();
        int explodedBombRange = getRangeBomb();
        for (Bomb activeBomb : getMap().getActiveBombArray()) {
            if (activeBomb.getYCoordinate() == explodedBombYCoordinate &&
                    activeBomb.getXCoordinate() >= explodedBombXCoordinate - explodedBombRange &&
                    activeBomb.getXCoordinate() <= explodedBombXCoordinate + explodedBombRange) {
                activeBomb.getBombOwner().getScheduledFuture().cancel(true);
                activeBomb.explode();
            }
            if (activeBomb.getXCoordinate() == explodedBombXCoordinate &&
                    activeBomb.getYCoordinate() >= explodedBombYCoordinate - explodedBombRange &&
                    activeBomb.getYCoordinate() <= explodedBombYCoordinate + explodedBombRange) {
                activeBomb.getBombOwner().getScheduledFuture().cancel(true);
                activeBomb.explode();
            }
        }
    }
    public Player getBombOwner() {
        return _bombOwner;
    }

    public void explodeBreakableBlocksInRange(){
        int explodedBombXCoordinate = getXCoordinate();
        int explodedBombYCoordinate = getYCoordinate();
        int explodedBombRange = getRangeBomb();
    }
}