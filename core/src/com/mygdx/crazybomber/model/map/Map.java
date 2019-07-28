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

        for (byte i = 0; i < 15; i++) {
            for (byte j = 0; j < 15; j++) {
                if (blockMap[i][j] == 1) {
                    blockMatrix[i][j] = new UnbreakableBlock(i, j);
                } else if (blockMap[i][j] == 2) {
                    blockMatrix[i][j] = new BreakableBlock(i, j);
                    switch (itemMap[i][j]) {
                        case 1:
                            ((BreakableBlock) blockMatrix[i][j]).setItem(new Item(i, j, ItemTypes.BombUp));
                            break;
                        case 2:
                            ((BreakableBlock) blockMatrix[i][j]).setItem(new Item(i, j, ItemTypes.RangeUp));
                            break;
                        case 3:
                            ((BreakableBlock) blockMatrix[i][j]).setItem(new Item(i, j, ItemTypes.SpeedUp));
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

    public ArrayList<Item> getActiveItemArray() {
        return _activeItemArray;
    }
}
