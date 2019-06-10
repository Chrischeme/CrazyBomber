package com.mygdx.crazybomber.model.bomb;

public class Bomb {
    private int _rangeBombs;
    private int _xCoordinate;
    private int _yCoordinate;
    private boolean _isExploding;

    public void explode(){
    }

    public Bomb(int playerXCoordinate, int playerYCoordinate, int rangeBombs){
        setXCoordinate(playerXCoordinate);
        setYCoordinate(playerYCoordinate);
        setRangeBombs(rangeBombs);
        setIsExploding(false);
    }

    public int getRangeBombs() {
        return _rangeBombs;
    }

    public void setRangeBombs(int _rangeBombs) {
        this._rangeBombs = _rangeBombs;
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

    public boolean getIsExploding() {
        return _isExploding;
    }

    public void setIsExploding(boolean _isExploding) {
        this._isExploding = _isExploding;
    }
}
