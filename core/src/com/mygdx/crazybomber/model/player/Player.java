package com.mygdx.crazybomber.model.player;

import com.mygdx.crazybomber.model.bomb.Bomb;
import com.mygdx.crazybomber.model.map.Map;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Player {
    private boolean _isKnockedUp;
    private boolean _isAlive;
    private int _numRangeUpgrades;
    private double _speed;
    private boolean onItem;
    private double _xCoordinate;
    private double _yCoordinate;
    private Stack<Bomb> _bombStack;
    private ScheduledExecutorService _scheduledExecutorService;
    private Map _map;


    public double getXCoordinate() {
        return _xCoordinate;
    }

    public void setXCoordinate(double _xCoordinate) {
        this._xCoordinate = _xCoordinate;
    }

    public double getYCoordinate() {
        return _yCoordinate;
    }

    public void setYCoordinate(double _yCoordinate) {
        this._yCoordinate = _yCoordinate;
    }

    public double getSpeed() {
        return _speed;
    }

    public void setSpeed(double _speed) {
        this._speed = _speed;
    }

    public boolean getIsKnockedUp() {
        return _isKnockedUp;
    }

    public void setIsKnockedUp(boolean _isKnockedUp) {
        this._isKnockedUp = _isKnockedUp;
    }

    public boolean getIsAlive() {
        return _isAlive;
    }

    public void setIsAlive(boolean _isAlive) {
        this._isAlive = _isAlive;
    }

    public boolean getIsOnItem() {
        return onItem;
    }

    public void setOnItem(boolean onItem) {
        this.onItem = onItem;
    }

    public void pickUpItem(int itemXCoordinate, int itemYCoordinate) {
    }

    public int getNumRangeUpgrades() {
        return _numRangeUpgrades;
    }

    public void setNumRangeUpgrades(int _rangeBombs) {
        this._numRangeUpgrades = _rangeBombs;
    }

    //todo: will need logic to not walk through walls and make traveling constant (frame rate or constant velocity, libgdx physics?)
    public void move(char direction) {
        switch (direction) {
            case 'W':
                setYCoordinate(getYCoordinate() + getSpeed());
                break;
            case 'A':
                setXCoordinate(getXCoordinate() - getSpeed());
                break;
            case 'S':
                setYCoordinate(getYCoordinate() - getSpeed());
                break;
            case 'D':
                setXCoordinate(getXCoordinate() + getSpeed());
                break;
        }
    }

    public Bomb dropBomb() {
        if (_bombStack.isEmpty()) {
            System.out.println("out of bombs");
            return null;
        }
        final Bomb droppedBomb = _bombStack.pop();
        droppedBomb.setRangeBomb(getNumRangeUpgrades() + 1);
        if (getXCoordinate() % 0.5 == 0) {
            droppedBomb.setXCoordinate((int) Math.round((getXCoordinate() - 0.01)));
        } else {
            droppedBomb.setXCoordinate((int) Math.round(getXCoordinate()));
        }
        if (getYCoordinate() % 0.5 == 0) {
            droppedBomb.setXCoordinate((int) Math.round((getYCoordinate() - 0.01)));
        } else {
            droppedBomb.setXCoordinate((int) Math.round(getYCoordinate()));
        }
        _scheduledExecutorService = Executors.newScheduledThreadPool(1);
        System.out.println("bomb dropped");
        ScheduledFuture _scheduledFuture =
                _scheduledExecutorService.schedule(new Runnable() {
                    public void run() {
                        droppedBomb.explode();
                    }
                }, 3, TimeUnit.SECONDS);
        _scheduledExecutorService.shutdown();
        return droppedBomb;
    }

    public Player(double playerSpawnXCoordinate, double playerSpawnYCoordinate, Map map) {
        _bombStack = new Stack<Bomb>();
        setIsAlive(false);
        setIsKnockedUp(false);
        setOnItem(false);
        setSpeed(1.5);
        setNumRangeUpgrades(0);
        setXCoordinate(playerSpawnXCoordinate);
        setYCoordinate(playerSpawnYCoordinate);
        _map = map;
        Bomb bomb = new Bomb((int) getXCoordinate(), (int) getYCoordinate(), getNumRangeUpgrades() + 1, _bombStack, map);
        _bombStack.push(bomb);
        getMap().getActiveBombArray().add(bomb);
    }

    public void addBomb(Map map) {
        final Bomb newBomb = new Bomb((int) getXCoordinate(), (int) getYCoordinate(), getNumRangeUpgrades() + 1, _bombStack, map);
        this._bombStack.push(newBomb);
    }

    public void increaseSpeed() {
        setSpeed(getSpeed() + 0.5);
    }

    public void pickUpRangeUpgrade() {
        setNumRangeUpgrades(getNumRangeUpgrades() + 1);
    }


    public Map getMap() {
        return _map;
    }
}
