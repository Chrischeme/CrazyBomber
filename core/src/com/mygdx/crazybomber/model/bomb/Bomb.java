package com.mygdx.crazybomber.model.bomb;

import com.mygdx.crazybomber.model.map.Map;
import com.mygdx.crazybomber.model.player.Player;

import java.util.Stack;

public class Bomb {
    private int _rangeBomb;
    private byte _x;
    private byte _y;
    private Stack<Bomb> _bombStack;
    private Map _map;
    private Player _bombOwner;

    public Bomb(Player player, Stack<Bomb> bombStack) {
        setX((byte) Math.round(player.getX()));
        setY((byte) Math.round(player.getY()));
        setRangeBomb(player.getNumRangeUpgrades() + 1);
        _bombStack = bombStack;
        _map = player.getMap();
        _bombOwner = player;
    }

    public Bomb(byte x, byte y) {
        setX(x);
        setY(y);
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

    public void explodeBombsInRange() {
        int explodedBombXCoordinate = getX();
        int explodedBombYCoordinate = getY();
        int explodedBombRange = getRangeBomb();
        for (Bomb activeBomb : getMap().getActiveBombArray()) {
            if (activeBomb.getY() == explodedBombYCoordinate &&
                    activeBomb.getX() >= explodedBombXCoordinate - explodedBombRange &&
                    activeBomb.getX() <= explodedBombXCoordinate + explodedBombRange) {
                activeBomb.getBombOwner().getScheduledFuture().cancel(true);
                activeBomb.explode();
            }
            if (activeBomb.getX() == explodedBombXCoordinate &&
                    activeBomb.getY() >= explodedBombYCoordinate - explodedBombRange &&
                    activeBomb.getY() <= explodedBombYCoordinate + explodedBombRange) {
                activeBomb.getBombOwner().getScheduledFuture().cancel(true);
                activeBomb.explode();
            }
        }
    }

    public Player getBombOwner() {
        return _bombOwner;
    }

    public void explodeBreakableBlocksInRange() {
        int explodedBombXCoordinate = getX();
        int explodedBombYCoordinate = getY();
        int explodedBombRange = getRangeBomb();
    }

    public byte getX() {
        return _x;
    }

    public void setX(byte x) {
        this._x = x;
    }

    public byte getY() {
        return _y;
    }

    public void setY(byte y) {
        this._y = y;
    }
}