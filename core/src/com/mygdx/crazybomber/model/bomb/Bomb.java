package com.mygdx.crazybomber.model.bomb;

import com.mygdx.crazybomber.model.map.Map;
import java.util.Stack;

public class Bomb{
    private int _rangeBomb;
    private int _xCoordinate;
    private int _yCoordinate;
    private Stack<Bomb> _bombStack;
    private Map _map;



    public Bomb(int playerXCoordinate, int playerYCoordinate, int rangeBombs, Stack<Bomb> bombStack, Map map) {
        setXCoordinate(playerXCoordinate);
        setYCoordinate(playerYCoordinate);
        setRangeBomb(rangeBombs);
        _bombStack = bombStack;
        _map = map;
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

    public int getXCoordinate() {
        return _xCoordinate;
    }

    public void setXCoordinate(int _xCoordinate) {
        this._xCoordinate = _xCoordinate;
    }

    public int getYCoordinate() {
        return _yCoordinate;
    }

    public void setYCoordinate(int _yCoordinate) {
        this._yCoordinate = _yCoordinate;
    }

    public Map getMap() {
        return _map;
    }
    public void explode(){
        System.out.println(getXCoordinate()+getYCoordinate());
        System.out.println("boom!");
        getMap().getActiveBombArray().remove(this);
        getBombStack().push(this);

    }


}