package com.mygdx.crazybomber.model.map;

import com.mygdx.crazybomber.model.block.Block;
import com.mygdx.crazybomber.model.block.BreakableBlock;
import com.mygdx.crazybomber.model.block.EmptyBlock;
import com.mygdx.crazybomber.model.block.UnbreakableBlock;
import com.mygdx.crazybomber.model.bomb.Bomb;
import com.mygdx.crazybomber.model.item.Item;
import com.mygdx.crazybomber.model.item.ItemTypes;

import java.util.ArrayList;

public class Map {
    public Block[][] blockMatrix = new Block[15][15];
    private ArrayList<Bomb> _activeBombArray;
    private ArrayList<Item> _activeItemArray;

    public Map(byte[][] blockMap, byte[][] itemMap) {
        _activeBombArray = new ArrayList<Bomb>();
        _activeItemArray = new ArrayList<Item>();
        byte itemIDCounter = 0;

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (blockMap[i][j] == 1) {
                    blockMatrix[i][j] = new UnbreakableBlock(i, j);
                } else if (blockMap[i][j] == 2) {
                    blockMatrix[i][j] = new BreakableBlock(i, j);
                    switch (itemMap[i][j]) {
                        case 0:
                            ((BreakableBlock) blockMatrix[i][j]).setItem(new Item(i, j, itemIDCounter, ItemTypes.BombUp));
                            itemIDCounter++;
                            break;
                        case 1:
                            ((BreakableBlock) blockMatrix[i][j]).setItem(new Item(i, j, itemIDCounter, ItemTypes.RangeUp));
                            itemIDCounter++;
                            break;
                        case 2:
                            ((BreakableBlock) blockMatrix[i][j]).setItem(new Item(i, j, itemIDCounter, ItemTypes.SpeedUp));
                            itemIDCounter++;
                            break;
                        default:
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
