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
    private ArrayList<ServerBomb> _activeBombArray;
    private ArrayList<ServerItem> _activeItemArray;

    public ServerMap(int[][] intMap) {
        _activeBombArray = new ArrayList<ServerBomb>();
        _activeItemArray = new ArrayList<ServerItem>();
        byte itemIDCounter = 0;

        for (byte i = 0; i < 15; i++) {
            for (byte j = 0; j < 15; j++) {
                if (intMap[i][j] == 1) {
                    blockMatrix[i][j] = new ServerUnbreakableBlock(i,j);
                } else if (intMap[i][j] == 2) {
                    blockMatrix[i][j] = new ServerBreakableBlock(i,j, itemIDCounter);
                    if (((ServerBreakableBlock) (blockMatrix[i][j])).getItem() != null) {
                        itemIDCounter = ((byte) (itemIDCounter + 1));
                        getActiveItemArray().add(((ServerBreakableBlock) blockMatrix[i][j]).getItem());
                    }
                } else {
                    blockMatrix[i][j] = new EmptyBlock(i, j);
                }
            }
        }
    }

    public ArrayList<ServerBomb> getActiveBombArray() {
        return _activeBombArray;
    }

    public ArrayList<ServerItem> getActiveItemArray() {
        return _activeItemArray;
    }
}
