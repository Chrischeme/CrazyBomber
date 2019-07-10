package com.mygdx.crazybomber.model.map;

import com.mygdx.crazybomber.model.block.Block;
import com.mygdx.crazybomber.model.block.BreakableBlock;
import com.mygdx.crazybomber.model.block.EmptyBlock;
import com.mygdx.crazybomber.model.block.UnbreakableBlock;
import com.mygdx.crazybomber.model.bomb.Bomb;
import com.mygdx.crazybomber.model.item.Item;

import java.util.ArrayList;

public class Map {
    public Block[][] blockMatrix = new Block[15][15];
    private ArrayList<Bomb> _activeBombArray;
    private ArrayList<Item> _activeItemArray;
    private byte _itemIDCounter = 0;

    public Map(int[][] intMap) {
        _activeBombArray = new ArrayList<Bomb>();
        _activeItemArray = new ArrayList<Item>();

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (intMap[i][j] == 1) {
                    blockMatrix[i][j] = new UnbreakableBlock(i, j);
                } else if (intMap[i][j] == 2) {
                    blockMatrix[i][j] = new BreakableBlock(i, j, getItemIDCounter());
                    if (((BreakableBlock) (blockMatrix[i][j])).getItem() != null) {
                        setItemIDCounter((byte) (getItemIDCounter() + 1));
                        getItemArray().add(((BreakableBlock) blockMatrix[i][j]).getItem());
                    } else {
                        blockMatrix[i][j] = new BreakableBlock(i, j);
                    }
                } else {
                    blockMatrix[i][j] = new EmptyBlock(i, j);
                }
            }
        }
    }

    public ArrayList<Bomb> getActiveBombArray() {
        return _activeBombArray;
    }

    public byte getItemIDCounter() {
        return _itemIDCounter;
    }

    public void setItemIDCounter(byte itemIDCounter) {
        this._itemIDCounter = itemIDCounter;
    }

    public ArrayList<Item> getItemArray() {
        return _activeItemArray;
    }
}
