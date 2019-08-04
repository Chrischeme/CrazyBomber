package com.mygdx.crazybomber.model.bomb;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.crazybomber.model.map.Map;
import com.mygdx.crazybomber.model.player.Player;

import java.util.Stack;

public class Bomb extends Sprite {
    private int _rangeBomb;
    private byte _x;
    private byte _y;
    private Stack<Bomb> _bombStack;
    private Map _map;
    private Player _bombOwner;
    private Texture texture = new Texture("object/bomb.png");

    public Bomb(Player player, Stack<Bomb> bombStack) {
        setXCoord((byte) Math.round(player.getX()));
        setYCoord((byte) Math.round(player.getY()));
        setRangeBomb(player.getNumRangeUpgrades() + 1);
        _bombStack = bombStack;
        _map = player.getMap();
        _bombOwner = player;
    }

    public Bomb(byte x, byte y) {
        setXCoord(x);
        setYCoord(y);
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
        int explodedBombXCoordinate = getXCoord();
        int explodedBombYCoordinate = getYCoord();
        int explodedBombRange = getRangeBomb();
        for (Bomb activeBomb : getMap().getActiveBombArray()) {
            if (activeBomb.getYCoord() == explodedBombYCoordinate &&
                    activeBomb.getXCoord() >= explodedBombXCoordinate - explodedBombRange &&
                    activeBomb.getXCoord() <= explodedBombXCoordinate + explodedBombRange) {
                activeBomb.getBombOwner().getScheduledFuture().cancel(true);
                activeBomb.explode();
            }
            if (activeBomb.getXCoord() == explodedBombXCoordinate &&
                    activeBomb.getYCoord() >= explodedBombYCoordinate - explodedBombRange &&
                    activeBomb.getYCoord() <= explodedBombYCoordinate + explodedBombRange) {
                activeBomb.getBombOwner().getScheduledFuture().cancel(true);
                activeBomb.explode();
            }
        }
    }

    public Player getBombOwner() {
        return _bombOwner;
    }

    public void explodeBreakableBlocksInRange() {
        int explodedBombXCoordinate = getXCoord();
        int explodedBombYCoordinate = getYCoord();
        int explodedBombRange = getRangeBomb();
    }

    public byte getXCoord() {
        return _x;
    }

    public void setXCoord(byte x) {
        this._x = x;
        setX(x*48);
    }

    public byte getYCoord() {
        return _y;
    }

    public void setYCoord(byte y) {
        this._y = y;
        setY(y*48);
    }
}