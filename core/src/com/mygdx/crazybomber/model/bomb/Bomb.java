package com.mygdx.crazybomber.model.bomb;

import com.mygdx.crazybomber.model.block.BreakableBlock;
import com.mygdx.crazybomber.model.block.EmptyBlock;
import com.mygdx.crazybomber.model.block.UnbreakableBlock;
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

        for (byte r = 1; r < this.getRangeBomb(); r++) {
            if (getMap().blockMatrix[getX()][getY() + r] instanceof BreakableBlock) {
                if (((BreakableBlock) getMap().blockMatrix[getX()][getY() + r]).getItem() != null) {
                    getMap().getActiveItemArray().add(((BreakableBlock) getMap().blockMatrix[getX()][getY() + r]).getItem());
                }
                getMap().blockMatrix[getX()][getY() + r] = new EmptyBlock(getX(), (byte) (getY() + r));
                break;
            } else if (getMap().blockMatrix[getX()][getY() + r] instanceof UnbreakableBlock) {
                break;
            }
            for (Bomb activeBomb : getMap().getActiveBombArray()) {
                if (activeBomb.getX() == getX() & activeBomb.getY() == ((byte) getY() + r)) {
                    activeBomb.getBombOwner().getScheduledFuture().cancel(true);
                    activeBomb.explode();
                }
            }
        }
        for (byte r = 1; r < this.getRangeBomb(); r++) {
            if (getMap().blockMatrix[getX() + r][getY()] instanceof BreakableBlock) {
                if (((BreakableBlock) getMap().blockMatrix[getX() + r][getY()]).getItem() != null) {
                    getMap().getActiveItemArray().add(((BreakableBlock) getMap().blockMatrix[getX() + r][getY()]).getItem());
                }
                getMap().blockMatrix[getX() + r][getY()] = new EmptyBlock(getX(), (byte) (getY() + r));
                break;
            } else if (getMap().blockMatrix[getX() + r][getY()] instanceof UnbreakableBlock) {
                break;
            }
            for (Bomb activeBomb : getMap().getActiveBombArray()) {
                if (activeBomb.getX() == ((byte) getX() + r) & activeBomb.getY() == getY()){
                    activeBomb.getBombOwner().getScheduledFuture().cancel(true);
                    activeBomb.explode();
                }
            }
        }
        for (byte r = 1; r < this.getRangeBomb(); r++) {
            if (getMap().blockMatrix[getX()][getY() - r] instanceof BreakableBlock) {
                if (((BreakableBlock) getMap().blockMatrix[getX()][getY() - r]).getItem() != null) {
                    getMap().getActiveItemArray().add(((BreakableBlock) getMap().blockMatrix[getX()][getY() - r]).getItem());
                }
                getMap().blockMatrix[getX()][getY() - r] = new EmptyBlock(getX(), (byte) (getY() + r));
                break;
            } else if (getMap().blockMatrix[getX()][getY() - r] instanceof UnbreakableBlock) {
                break;
            }
            for (Bomb activeBomb : getMap().getActiveBombArray()) {
                if (activeBomb.getX() == getX() & activeBomb.getY() == ((byte) getY() - r)) {
                    activeBomb.getBombOwner().getScheduledFuture().cancel(true);
                    activeBomb.explode();
                }
            }
        }
        for (byte r = 1; r < this.getRangeBomb(); r++) {
            if (getMap().blockMatrix[getX() - r][getY()] instanceof BreakableBlock) {
                if (((BreakableBlock) getMap().blockMatrix[getX() - r][getY()]).getItem() != null) {
                    getMap().getActiveItemArray().add(((BreakableBlock) getMap().blockMatrix[getX() - r][getY()]).getItem());
                }
                getMap().blockMatrix[getX() - r][getY()] = new EmptyBlock(getX(), (byte) (getY() + r));
                break;
            } else if (getMap().blockMatrix[getX() - r][getY()] instanceof UnbreakableBlock) {
                break;
            }
            for (Bomb activeBomb : getMap().getActiveBombArray()) {
                if (activeBomb.getX() == ((byte) getX() - r) & activeBomb.getY() == getY()){
                    activeBomb.getBombOwner().getScheduledFuture().cancel(true);
                    activeBomb.explode();
                }
            }
        }
    }

    public Player getBombOwner() {
        return _bombOwner;
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