package com.mygdx.crazybomber.model.map;

import com.mygdx.crazybomber.model.block.Block;
import com.mygdx.crazybomber.model.block.BreakableBlock;
import com.mygdx.crazybomber.model.block.EmptyBlock;
import com.mygdx.crazybomber.model.block.UnbreakableBlock;
import com.mygdx.crazybomber.model.bomb.Bomb;
import com.mygdx.crazybomber.model.item.Item;

import java.util.ArrayList;

public class Map implements Screen{

    public Block[][] blockMatrix = new Block[15][15];
    private ArrayList<Bomb> _activeBombArray;
    private ArrayList<Item> _activeItemArray;

    public Map(int[][] intMap) {
        _activeBombArray = new ArrayList<Bomb>();
        _activeItemArray = new ArrayList<Item>();
    }

    public Map(byte[][] blockMap, byte[][] itemMap) {

    }

    public ArrayList<Bomb> getActiveBombArray() {
        return _activeBombArray;
    }

    public ArrayList<Item> getItemArray() {
        return _activeItemArray;
    }
}
