package com.mygdx.crazybomber.model.player;

import com.mygdx.crazybomber.model.bomb.Bomb;

import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Player {
    private boolean _isKnockedUp;
    private boolean _isAlive;
    private int _rangeBomb;
    private double _speed;
    private boolean onItem;
    private double _xCoordinate;
    private double _yCoordinate;
    private Stack<Bomb> _bombStack;
    private ScheduledExecutorService _scheduleExecutorService;


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

    public int getRangeBomb() {
        return _rangeBomb;
    }

    public void setRangeBomb(int _rangeBombs) {
        this._rangeBomb = _rangeBombs;
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
        droppedBomb.setRangeBomb(getRangeBomb());
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
        ScheduledExecutorService _scheduledExecutorService = Executors.newScheduledThreadPool(5);
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

    public Player(double playerSpawnXCoordinate, double playerSpawnYCoordinate) {
        _bombStack = new Stack<Bomb>();
        setIsAlive(false);
        setIsKnockedUp(false);
        setOnItem(false);
        setSpeed(1.5);
        setRangeBomb(1);
        setXCoordinate(playerSpawnXCoordinate);
        setYCoordinate(playerSpawnYCoordinate);
        Bomb bomb = new Bomb((int) getXCoordinate(), (int) getYCoordinate(), getRangeBomb(), _bombStack);
        _bombStack.push(bomb);
    }

    public void addBomb() {
        final Bomb newBomb = new Bomb((int) getXCoordinate(), (int) getYCoordinate(), getRangeBomb(), _bombStack);
        this._bombStack.push(newBomb);
    }

    public void increaseSpeed() {
        setSpeed(getSpeed() + 0.5);
    }

    public void increaseBombRange() {
        setRangeBomb(getRangeBomb() + 1);
    }


}
