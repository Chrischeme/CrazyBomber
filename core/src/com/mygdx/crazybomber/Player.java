//TODO: FIX MOVE LATER
package com.mygdx.crazybomber;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.crazybomber.model.bomb.Bomb;
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
    private Stack<Bomb> _bombStack;
    private ScheduledExecutorService _scheduledExecutorService;

    public void draw(Batch batch)
    {
        super.draw(batch);
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

    public Bomb dropBomb() {
        if (_bombStack.isEmpty()) {
            System.out.println("out of bombs");
            return null;
        }
        final Bomb droppedBomb = _bombStack.pop();
        droppedBomb.setRangeBomb(getNumRangeUpgrades() + 1);
        if (getX() % 0.5 == 0) {
            droppedBomb.setXCoordinate((int) Math.round((getX() - 0.01)));
        } else {
            droppedBomb.setXCoordinate((int) Math.round(getX()));
        }
        if (getY() % 0.5 == 0) {
            droppedBomb.setXCoordinate((int) Math.round((getY() - 0.01)));
        } else {
            droppedBomb.setXCoordinate((int) Math.round(getY()));
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

    public Player(float playerSpawnXCoordinate, float playerSpawnYCoordinate, Texture texture) {
        super(texture);
        _bombStack = new Stack<Bomb>();
        setIsAlive(false);
        setIsKnockedUp(false);
        setOnItem(false);
        setSpeed(1.5f);
        setNumRangeUpgrades(0);
        setX(playerSpawnXCoordinate);
        setY(playerSpawnYCoordinate);
        Bomb bomb = new Bomb((int) getX(), (int) getY(), getNumRangeUpgrades() + 1, _bombStack);
        _bombStack.push(bomb);
    }

    public void addBomb() {
        final Bomb newBomb = new Bomb((int) getX(), (int) getY(), getNumRangeUpgrades() + 1, _bombStack);
        this._bombStack.push(newBomb);
    }

    public void increaseSpeed() {
        setSpeed(getSpeed() + 0.5f);
    }

    public void pickUpRangeUpgrade() {
        setNumRangeUpgrades(getNumRangeUpgrades() + 1);
    }


}
