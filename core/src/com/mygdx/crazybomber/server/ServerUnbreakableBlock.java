package com.mygdx.crazybomber.server;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.crazybomber.model.block.Block;

public class ServerUnbreakableBlock extends Block{
        public ServerUnbreakableBlock(byte x, byte y) {
            setXCoord(x);
            setYCoord(y);
            setX(x*48);
            setY(y*48);
        }
    }
