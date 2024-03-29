package com.mygdx.crazybomber.server;

import com.mygdx.crazybomber.model.bomb.Bomb;
import com.mygdx.crazybomber.model.item.Item;
import com.mygdx.crazybomber.model.map.Map;
import com.mygdx.crazybomber.model.client.CrazyBomberClient;

import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class ServerPlayer {
    private float x;
    private float y;
    private boolean _isKnockedUp;
    private boolean _isAlive;
    private int _numRangeUpgrades;
    private byte _id;
    private float _speed;
    private Map _map;

    public float getSpeed() {
        return _speed;
    }

    public void setSpeed(float _speed) {
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

    public int getNumRangeUpgrades() {
        return _numRangeUpgrades;
    }

    public void setNumRangeUpgrades(int _rangeBombs) {
        this._numRangeUpgrades = _rangeBombs;
    }

    public ServerPlayer(byte id, float playerSpawnXCoordinate, float playerSpawnYCoordinate) {
        _id = id;
        setIsAlive(false);
        setIsKnockedUp(false);
        setSpeed(1.5f);
        setNumRangeUpgrades(0);
        x = playerSpawnXCoordinate;
        y = playerSpawnYCoordinate;
    }

    public Map getMap() {
        return _map;
    }


    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public byte getId() {
        return _id;
    }

    public void setId(byte _id) {
        this._id = _id;
    }
}
