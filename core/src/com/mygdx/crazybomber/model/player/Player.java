//TODO: FIX MOVE LATER
package com.mygdx.crazybomber.model.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.crazybomber.model.bomb.Bomb;
import com.mygdx.crazybomber.model.client.CrazyBomberClient;
import com.mygdx.crazybomber.model.item.Item;
import com.mygdx.crazybomber.model.map.Map;

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
    private byte _playerId;
    private float _speed;
    private boolean onItem;
    private Stack<Bomb> _bombStack;
    private ScheduledExecutorService _scheduledExecutorService;
    private ScheduledFuture _scheduledFuture;
    private Map _map;
    private CrazyBomberClient _playerClient;

    public Stack<Bomb> getBombStack() {
        return _bombStack;
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

    public Bomb dropBomb() throws IOException {
        if (_bombStack.isEmpty()) {
            System.out.println("out of bombs");
            return null;
        }
        final Bomb droppedBomb = _bombStack.pop();
        droppedBomb.setRangeBomb(getNumRangeUpgrades() + 1);
        droppedBomb.setXCoord((byte) Math.round(getX()));
        droppedBomb.setYCoord((byte) Math.round(getY()));

        _scheduledExecutorService = Executors.newScheduledThreadPool(1);
        System.out.println("bomb dropped");
        _scheduledFuture =
                _scheduledExecutorService.schedule(new Runnable() {
                    public void run() {
                        droppedBomb.explode();
                        _scheduledExecutorService.shutdown();
                    }
                }, 3, TimeUnit.SECONDS);
        getPlayerClient().sendOnBombPlaced(droppedBomb.getXCoord(), droppedBomb.getYCoord());
        return droppedBomb;
    }

    public Player(byte id, float playerSpawnX, float playerSpawnY, Map map, Texture texture, CrazyBomberClient client) throws IOException {
        super(texture);
        _playerClient = client;
        _playerId = id;
        _bombStack = new Stack<Bomb>();
        setIsAlive(false);
        setIsKnockedUp(false);
        setOnItem(false);
        setSpeed(1.5f);
        setNumRangeUpgrades(0);
        setX(playerSpawnX*48 + 180);
        setY(playerSpawnY*48);
        _map = map;
        Bomb bomb = new Bomb(this, _bombStack);
        _bombStack.push(bomb);
        getMap().getActiveBombArray().add(bomb);
        getPlayerClient().sendOnNewPlayer((byte) playerSpawnX, (byte) playerSpawnY,getPlayerId());
    }

    public void pickUpItem(Item item) throws IOException {
        _playerClient.sendOnItemPickedUp(item.getXCoord(), item.getYCoord(), (byte) item.getItemType().ordinal());
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

    public byte getPlayerId() {
        return _playerId;
    }

    public Player(byte x, byte y, byte playerId, Texture texture) {
        super(texture);
        setX((float)x*48 + 180);
        setY((float)y*48);
        _playerId = playerId;
    }
}
