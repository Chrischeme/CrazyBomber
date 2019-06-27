package com.mygdx.crazybomber.model.bomb;

import com.mygdx.crazybomber.model.map.Map;
import com.mygdx.crazybomber.model.player.Player;

import java.util.Stack;

public class Bomb {
    private int _rangeBomb;
    private int _xCoordinate;
    private int _yCoordinate;
    private Stack<Bomb> _bombStack;
    private Map _map;
    private Player _bombOwner;

    public Bomb(Player player, int playerXCoordinate, int playerYCoordinate, int rangeBombs, Stack<Bomb> bombStack, Map map) {
        setXCoordinate(playerXCoordinate);
        setYCoordinate(playerYCoordinate);
        setRangeBomb(rangeBombs);
        _bombStack = bombStack;
        _map = map;
        _bombOwner = player;
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
        //System.out.println("bomb x coordinate is " + getXCoordinate());
        //System.out.println("bomb y coordinate is " + getYCoordinate());
        //System.out.println("bomb exploded at " + (double)System.nanoTime()/(1000000000));
        int explodedBombXCoordinate = getXCoordinate();
        int explodedBombYCoordinate = getYCoordinate();
        int explodedBombRange = getRangeBomb();
        getMap().getActiveBombArray().remove(this);
        getBombStack().push(this);
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

}