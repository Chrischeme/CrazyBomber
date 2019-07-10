package com.mygdx.crazybomber.model.player;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.crazybomber.Player;

import java.util.Scanner;

//just a test class will not be in the product
public class TestClass {

    public static void main(String args[]) {
        Texture playerTexture = new Texture("player.png");
        Player player1 = new Player(1,1,playerTexture);

        while (true) {
            String userInput = new Scanner(System.in).nextLine();
            if (userInput.equals("1")) {
                player1.addBomb();
            } else if (userInput.equals("2")) {
                player1.dropBomb();
            } else {
                break;
            }
        }
    }

}