package com.mygdx.crazybomber.server;

import com.badlogic.gdx.Game;
import com.mygdx.crazybomber.Player;
import com.mygdx.crazybomber.model.GameState;
import com.mygdx.crazybomber.model.block.EmptyBlock;
import com.mygdx.crazybomber.model.item.BombUp;
import com.mygdx.crazybomber.model.item.Item;
import com.mygdx.crazybomber.model.item.RangeUp;
import com.mygdx.crazybomber.model.item.SpeedUp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrazyBomberServer {
    private static int numPlayers = 0;
    public static ArrayList<DataOutputStream> clientList = new ArrayList<>();

    public static void main (String[] args) throws IOException {
        try (ServerSocket listener = new ServerSocket(59898)) {
            System.out.println("The server is running...");
            ExecutorService pool = Executors.newFixedThreadPool(4);
            while (true) {
                pool.execute(new Handler(listener.accept()));
            }
        }
    }
    private static class Handler implements Runnable {
        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;
        private GameState gameState;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
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
                gameState = new GameState(intMap, new ArrayList<>());
                out = new DataOutputStream(socket.getOutputStream());
                in = new DataInputStream(socket.getInputStream());

                clientList.add(out);
                Player player;
                int x = 0, y = 0;

                switch (numPlayers) {
                    case 0:
                        player = new Player(0f, 0f, gameState.getMap());
                        break;
                    case 1:
                        player = new Player(0f, 14f, gameState.getMap());
                        y = 14;
                        break;
                    case 2:
                        player = new Player(14f, 0f, gameState.getMap());
                         x = 14;
                        break;
                    case 3:
                        player = new Player(14f, 14f, gameState.getMap());
                        x = 14;
                        y = 14;
                        break;
                    default:
                        player = null;
                        break;
                }
                numPlayers++;
                if (player != null) {
                    gameState.getPlayerList().add(player);
                    byte[] data = new byte[8];
                    byte[] intInByteArray = new byte[4];
                    ByteBuffer.wrap(intInByteArray).putInt(x);
                    copyArrayToAnotherWithStartingIndexes(intInByteArray, data, 0);
                    ByteBuffer.wrap(intInByteArray).putInt(y);
                    copyArrayToAnotherWithStartingIndexes(intInByteArray, data, 4);
                    out.writeInt(data.length);
                    out.write(data);
                }

                while(true) {
                    int length = in.readInt();
                    byte[] data = new byte[length];
                    if (length > 0) {
                        in.readFully(data, 0, data.length);
                    }
                    ByteBuffer wrapped = ByteBuffer.wrap(data);
                    byte switchByte = data[0];
                    System.out.println("Updating: " + switchByte);
                    int xCoord, yCoord;
                    switch (switchByte) {
                        // not sure about the data about which player, we might be able to decipher that with the different sockets
                        case 1:
                            // On player coord change
                            // data should have which player + new coord + if a movement key is pressed down (WASD)
                            // update the player coord in player data + send to all other players
                            player.setX((float)wrapped.getDouble(1));
                            player.setY((float)wrapped.getDouble(9));
                            break;
                        case 2:
                            // On player placed bomb
                            // data should have coord of bomb.  IMPLEMENT LATER : have time the bomb was placed
                            // update bomb in bomb data + send to all other players => NOT SURE

                            break;
                        case 3:
                            // On block broken
                            // data should have coord of broken block.
                            // update the block in map data + send to all other players
                            xCoord = wrapped.getInt(1);
                            yCoord = wrapped.getInt(5);
                            gameState.getMap().blockMatrix[xCoord][yCoord] = new EmptyBlock(xCoord, yCoord);
                            break;
                        case 4:
                            // On item dropped
                            // data should have type of item + coord of item
                            // update item in item data + send to all other players => NOT SURE
                            xCoord = wrapped.getInt(1);
                            yCoord = wrapped.getInt(5);
                            Item item;
                            switch (data[9]) {
                                case 0:
                                    item = new BombUp(xCoord, yCoord, data[10]);
                                case 1:
                                    item = new RangeUp(xCoord, yCoord, data[10]);
                                default:
                                    item = new SpeedUp(xCoord, yCoord, data[10]);
                            }
                            gameState.getMap().getItemArray().add(item);
                            break;
                        case 5:
                            // On item pickedup
                            // data should have which player
                            // update the player fields in player data + send to all other players
                            break;
                        case 6:
                            // On player death
                            // data should have which player
                            // send to all other players
                            break;
                        case 7:
                            // Currently do not have a case for this one
                            break;
                        case 8:
                            // on Bomb explodes
                            // data should have player data // MAYBE should have bomb range
                            // send to all other players
                            xCoord = wrapped.getInt(1);
                            yCoord = wrapped.getInt(5);
                            for (int i = 0; i < gameState.getMap().getActiveBombArray().size(); i++) {
                                if (gameState.getMap().getActiveBombArray().get(i).getXCoordinate() == xCoord &&
                                        gameState.getMap().getActiveBombArray().get(i).getYCoordinate() == yCoord) {
                                    gameState.getMap().getActiveBombArray().remove(i);
                                    break;
                                }
                            }
                            break;
                        default:
                            // Unspecified
                            // ignore??
                            break;
                    }
                    sendDataToOtherPlayers(data, out);
                }
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }

        private void sendDataToOtherPlayers(byte[] data, DataOutputStream out) throws IOException {
            for(DataOutputStream outputStream : clientList) {
                if (out != outputStream) {
                    outputStream.writeInt(data.length);
                    outputStream.write(data);
                }
            }
        }

        public void copyArrayToAnotherWithStartingIndexes(byte[] fromArray, byte[] toArray, int toArrayIndex) {
            for(byte fromArrayByte : fromArray){
                toArray[toArrayIndex] = fromArrayByte;
                toArrayIndex++;
            }
        }
    }
}
