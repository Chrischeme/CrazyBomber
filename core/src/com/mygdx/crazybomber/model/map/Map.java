package com.mygdx.crazybomber.model.map;

import com.mygdx.crazybomber.model.block.Block;
import com.mygdx.crazybomber.model.block.BreakableBlock;
import com.mygdx.crazybomber.model.bomb.Bomb;

import java.util.ArrayList;

public class Map {
    public Block[][] blockMatrix = new Block[16][16];
    private ArrayList<Bomb> _activeBombArray;


    public Map(int[][] intMap) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {

            }
        }
    }

    public ArrayList<Bomb> getActiveBombArray() {
        return _activeBombArray;
    }
}
