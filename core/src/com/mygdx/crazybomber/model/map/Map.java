package com.mygdx.crazybomber.model.map;

import com.mygdx.crazybomber.model.block.Block;
import com.mygdx.crazybomber.model.bomb.Bomb;

import java.util.ArrayList;

public class Map {
    public Block[][] blockMatrix = new Block[15][15];
    private ArrayList<Bomb> _activeBombArray;

    //todo population logic will go here
    public Map(int[][] intMap) {
        _activeBombArray = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
            }
        }
    }

    public ArrayList<Bomb> getActiveBombArray() {
        return _activeBombArray;
    }
}
