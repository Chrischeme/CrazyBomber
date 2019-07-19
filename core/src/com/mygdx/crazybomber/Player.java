//TODO: FIX MOVE LATER
package com.mygdx.crazybomber;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.crazybomber.model.bomb.Bomb;
import com.mygdx.crazybomber.model.item.Item;
import com.mygdx.crazybomber.model.map.Map;
import com.mygdx.crazybomber.model.player.CrazyBomberClient;

import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Player extends Sprite {
    private boolean _isKnockedUp;
    private boolean _isAlive;
    private int _numRangeUpgrades;
    private float _speed;
    private boolean onItem;
    private double _xCoordinate;
    private double _yCoordinate;
    private Stack<Bomb> _bombStack;
    private ScheduledExecutorService _scheduledExecutorService;
    private ScheduledFuture _scheduledFuture;
    private Map _map;
    private CrazyBomberClient _playerClient;

    public double getXCoordinate() {
        return _xCoordinate;
    }

    public void setXCoordinate(double _xCoordinate) {
        this._xCoordinate = _xCoordinate;
    }

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
        if (direction == 'W'){
            setY(getY() + getSpeed());
        }
        if (direction == 'A'){
            setX(getX() - getSpeed());
        }if (direction == 'S'){
            setY(getY() - getSpeed());
        }if (direction == 'D'){
            setX(getX() + getSpeed());
        }
    }

    public Bomb dropBomb() throws IOException {
        if (_bombStack.isEmpty()) {
            System.out.println("out of bombs");
            return null;
        }
        final Bomb droppedBomb = _bombStack.pop();
        droppedBomb.setRangeBomb(getNumRangeUpgrades() + 1);
        droppedBomb.setXCoordinate((int) Math.round(getX()));
        droppedBomb.setYCoordinate((int) Math.round(getY()));

        _scheduledExecutorService = Executors.newScheduledThreadPool(1);
        System.out.println("bomb dropped");
        _scheduledFuture =
                _scheduledExecutorService.schedule(new Runnable() {
                    public void run() {
                        droppedBomb.explode();
                        _scheduledExecutorService.shutdown();
                    }
                }, 3, TimeUnit.SECONDS);
        getPlayerClient().sendOnBombPlaced(droppedBomb.getXCoordinate(), droppedBomb.getYCoordinate());
        return droppedBomb;
    }

    public Player(float playerSpawnXCoordinate, float playerSpawnYCoordinate, Map map, Texture texture) {
        super(texture);
        _bombStack = new Stack<Bomb>();
        setIsAlive(false);
        setIsKnockedUp(false);
        setOnItem(false);
        setSpeed(1.5f);
        setNumRangeUpgrades(0);
        setX(playerSpawnXCoordinate);
        setY(playerSpawnYCoordinate);
        _map = map;
        Bomb bomb = new Bomb(this, _bombStack);
        _bombStack.push(bomb);
        getMap().getActiveBombArray().add(bomb);
    }

    public Player(float playerSpawnXCoordinate, float playerSpawnYCoordinate, Map map) {
        _bombStack = new Stack<Bomb>();
        setIsAlive(false);
        setIsKnockedUp(false);
        setOnItem(false);
        setSpeed(1.5f);
        setNumRangeUpgrades(0);
        setX(playerSpawnXCoordinate);
        setY(playerSpawnYCoordinate);
        _map = map;
        Bomb bomb = new Bomb(this, _bombStack);
        _bombStack.push(bomb);
        getMap().getActiveBombArray().add(bomb);
    }

    public void pickUpItem(Item item, Map map) throws IOException {
        getPlayerClient().sendOnItemPickedUp(item.getItemType(),item.getItemID());


    }

    public Map getMap() {
        return _map;
    }

    public ScheduledFuture getScheduledFuture() {
        return _scheduledFuture;
    }

    public CrazyBomberClient getPlayerClient() {
        return _playerClient;
    }
}
