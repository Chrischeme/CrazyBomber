package com.mygdx.crazybomber.model.player;

import com.mygdx.crazybomber.model.block.BreakableBlock;
import com.mygdx.crazybomber.model.map.Map;

//just a test class will not be in the product
public class TestClass {
    public static void main(String args[]) {

       /* while (true) {
            String userInput = new Scanner(System.in).nextLine();
            if (userInput.equals("1")) {
                player1.addBomb();
            } else if (userInput.equals("2")) {
                player1.dropBomb();
            } else {
                break;
            }
        }*/
        int[][] intMap =
                {{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 2, 0, 2, 2, 2, 0, 0, 1, 0, 1, 0, 1, 0},
                        {0, 1, 0, 2, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 2, 0, 2, 2, 2, 0, 0, 1, 0, 1, 0, 1, 0},
                        {0, 1, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0},
                        {0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                        {0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

       Map firstMap =  new Map(intMap);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
            }
        }
    }

}