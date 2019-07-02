package com.mygdx.crazybomber.model.player;

import com.mygdx.crazybomber.model.map.InitialMap;
import com.mygdx.crazybomber.model.map.Map;

//just a test class will not be in the product
public class TestClass {
    public static void main(String args[]) {
        int[][] intMap = new InitialMap().getIntMap1();
        Map firstMap = new Map(intMap);
        Player player1 = new Player(0,0,firstMap);
        Player player2 = new Player(0,1,firstMap);
        Player player3 = new Player(1,1,firstMap);
        Player player4 = new Player(1,0,firstMap);
        System.out.println(player2.getXCoordinate());
        System.out.println(player2.getXCoordinate());
        System.out.println((double)System.nanoTime()/(1000000000));
        player1.dropBomb();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){}
        System.out.println((double)System.nanoTime()/(1000000000));
        player2.dropBomb();
        player3.dropBomb();
        player4.dropBomb();


    }

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


        /*Map firstMap = new Map(intMap);
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.println(firstMap.blockMatrix[i][j]);
            }
        }*/

}