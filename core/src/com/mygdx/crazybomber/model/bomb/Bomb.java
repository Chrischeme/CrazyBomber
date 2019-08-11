package com.mygdx.crazybomber.model.bomb;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.crazybomber.model.block.BreakableBlock;
import com.mygdx.crazybomber.model.block.EmptyBlock;
import com.mygdx.crazybomber.model.block.UnbreakableBlock;
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

    public Bomb(Player player, Stack<Bomb> bombStack) {
        setXCoord((byte) Math.round(player.getX()));
        setYCoord((byte) Math.round(player.getY()));
        setRangeBomb(player.getNumRangeUpgrades() + 1);
        _bombStack = bombStack;
        _map = player.getMap();
        _bombOwner = player;
        this.setTexture(new Texture("object/bomb.png"));
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
        getMap().getActiveBombArray().remove(this);
        getBombStack().push(this);

        for (byte r = 1; r < this.getRangeBomb(); r++) {
            if (((int) getYCoord() + r) > getMap().blockMatrix[0].length) {
                break;
            }
                if (getMap().blockMatrix[getXCoord()][getYCoord() + r] instanceof BreakableBlock) {
                    if (((BreakableBlock) getMap().blockMatrix[getXCoord()][getYCoord() + r]).getItem() != null) {
                        getMap().getActiveItemArray().add(((BreakableBlock) getMap().blockMatrix[getXCoord()][getYCoord() + r]).getItem());
                    }
                    getMap().blockMatrix[getXCoord()][getYCoord() + r] = new EmptyBlock(getXCoord(), (byte) (getYCoord() + r));
                    break;
                } else if (getMap().blockMatrix[getXCoord()][getYCoord() + r] instanceof UnbreakableBlock) {
                    break;
                }
            for (Bomb activeBomb : getMap().getActiveBombArray()) {
                if (activeBomb.getXCoord() == getXCoord() & activeBomb.getYCoord() == ((byte) getYCoord() + r)) {
                    activeBomb.getBombOwner().getScheduledFuture().cancel(true);
                    activeBomb.explode();
                }
            }
        }
        for (byte r = 1; r < this.getRangeBomb(); r++) {
            if (((int) getXCoord() + r) > getMap().blockMatrix.length) {
                break;
            }
            if (getMap().blockMatrix[getXCoord() + r][getYCoord()] instanceof BreakableBlock) {
                if (((BreakableBlock) getMap().blockMatrix[getXCoord() + r][getYCoord()]).getItem() != null) {
                    getMap().getActiveItemArray().add(((BreakableBlock) getMap().blockMatrix[getXCoord() + r][getYCoord()]).getItem());
                }
                getMap().blockMatrix[getXCoord() + r][getYCoord()] = new EmptyBlock(getXCoord(), (byte) (getYCoord() + r));
                break;
            } else if (getMap().blockMatrix[getXCoord() + r][getYCoord()] instanceof UnbreakableBlock) {
                break;
            }
            for (Bomb activeBomb : getMap().getActiveBombArray()) {
                if (activeBomb.getXCoord() == ((byte) getXCoord() + r) & activeBomb.getYCoord() == getYCoord()) {
                    activeBomb.getBombOwner().getScheduledFuture().cancel(true);
                    activeBomb.explode();
                }
            }
        }
        for (byte r = 1; r < this.getRangeBomb(); r++) {
            if (((int) getYCoord() - r) > 0) {
                break;
            }
            if (getMap().blockMatrix[getXCoord()][getYCoord() - r] instanceof BreakableBlock) {
                if (((BreakableBlock) getMap().blockMatrix[getXCoord()][getYCoord() - r]).getItem() != null) {
                    getMap().getActiveItemArray().add(((BreakableBlock) getMap().blockMatrix[getXCoord()][getYCoord() - r]).getItem());
                }
                getMap().blockMatrix[getXCoord()][getYCoord() - r] = new EmptyBlock(getXCoord(), (byte) (getYCoord() + r));
                break;
            } else if (getMap().blockMatrix[getXCoord()][getYCoord() - r] instanceof UnbreakableBlock) {
                break;
            }
            for (Bomb activeBomb : getMap().getActiveBombArray()) {
                if (activeBomb.getXCoord() == getXCoord() & activeBomb.getYCoord() == ((byte) getYCoord() - r)) {
                    activeBomb.getBombOwner().getScheduledFuture().cancel(true);
                    activeBomb.explode();
                }
            }
        }
        for (byte r = 1; r < this.getRangeBomb(); r++) {
            if (((int) getXCoord() - r) > 0) {
                break;
            }
            if (getMap().blockMatrix[getXCoord() - r][getYCoord()] instanceof BreakableBlock) {
                if (((BreakableBlock) getMap().blockMatrix[getXCoord() - r][getYCoord()]).getItem() != null) {
                    getMap().getActiveItemArray().add(((BreakableBlock) getMap().blockMatrix[getXCoord() - r][getYCoord()]).getItem());
                }
                getMap().blockMatrix[getXCoord() - r][getYCoord()] = new EmptyBlock(getXCoord(), (byte) (getYCoord() + r));
                break;
            } else if (getMap().blockMatrix[getXCoord() - r][getYCoord()] instanceof UnbreakableBlock) {
                break;
            }
            for (Bomb activeBomb : getMap().getActiveBombArray()) {
                if (activeBomb.getXCoord() == ((byte) getXCoord() - r) & activeBomb.getYCoord() == getYCoord()) {
                    activeBomb.getBombOwner().getScheduledFuture().cancel(true);
                    activeBomb.explode();
                }
            }
        }
    }

    public Player getBombOwner() {
        return _bombOwner;
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