package com.mygdx.crazybomber.server;

import com.mygdx.crazybomber.model.block.Block;
import com.mygdx.crazybomber.model.block.BreakableBlock;
import com.mygdx.crazybomber.model.block.EmptyBlock;
import com.mygdx.crazybomber.model.block.UnbreakableBlock;
import com.mygdx.crazybomber.model.bomb.Bomb;
import com.mygdx.crazybomber.model.item.Item;

import java.util.ArrayList;

public class ServerMap{

    public Block[][] blockMatrix = new Block[15][15];
    private ArrayList<Bomb> _activeBombArray;
    private ArrayList<Item> _activeItemArray;

    public Map(int[][] intMap) {
        _activeBombArray = new ArrayList<Bomb>();
        _activeItemArray = new ArrayList<Item>();
        byte itemIDCounter = 0;

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (intMap[i][j] == 1) {
                    blockMatrix[i][j] = new UnbreakableBlock(i, j);
                } else if (intMap[i][j] == 2) {
                    blockMatrix[i][j] = new BreakableBlock(i, j, itemIDCounter);
                    if (((BreakableBlock) (blockMatrix[i][j])).getItem() != null) {
                        itemIDCounter = ((byte) (itemIDCounter + 1));
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

    public ArrayList<Item> getItemArray() {
        return _activeItemArray;
    }
}
