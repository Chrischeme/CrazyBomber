package com.mygdx.crazybomber.server;

import com.mygdx.crazybomber.model.block.EmptyBlock;
import com.mygdx.crazybomber.model.bomb.Bomb;
import com.mygdx.crazybomber.model.item.Item;
import com.mygdx.crazybomber.model.item.ItemTypes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrazyBomberServer {
    private static int numPlayers = 0;
    public static ArrayList<DataOutputStream> clientList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
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
        private ServerGameState gameState;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                int[][] intMap =
                        {
                                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 1, 0, 2, 0, 2, 2, 2, 0, 0, 1, 0, 1, 0, 1},
                                {0, 1, 0, 2, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0},
                                {0, 1, 0, 2, 0, 2, 2, 2, 0, 0, 1, 0, 1, 0, 1},
                                {0, 1, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                                {0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
                                {0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                                {0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
                gameState = new ServerGameState(intMap, new ArrayList<ServerPlayer>());
                out = new DataOutputStream(socket.getOutputStream());
                in = new DataInputStream(socket.getInputStream());

                clientList.add(out);
                ServerPlayer player;
                int x = 0, y = 0;

                switch (numPlayers) {
                    case 0:
                        player = new ServerPlayer((byte) 1, 0f, 0f);
                        break;
                    case 1:
                        player = new ServerPlayer((byte) 2, 0f, 14f);
                        y = 14;
                        break;
                    case 2:
                        player = new ServerPlayer((byte) 3, 14f, 0f);
                        x = 14;
                        break;
                    case 3:
                        player = new ServerPlayer((byte) 4, 14f, 14f);
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
                    byte[] data = new byte[2];
                    data[0] = (byte) x;
                    data[1] = (byte) y;
                    out.write(data);
                    //todo add code to use wrap and integers to make maps larger than 15x15
                    data[0] = 15;
                    data[1] = 15;
                    out.write(data);
                    byte[] blockData = new byte[((int) data[0]) * ((int) data[1])];
                    byte[] itemData = new byte[((int) data[0]) * ((int) data[1])];
                    for (int i = 0; i < (int) data[0]; i++) {
                        for (int j = 0; j < (int) data[1]; j++) {
                            blockData[i * ((int) data[0]) + j] = (byte) intMap[i][j];
                            if (intMap[i][j] == 2) {
                                itemData[i * ((int) data[0]) + j] = (byte) ((ServerBreakableBlock) gameState.getMap().blockMatrix[i][j]).
                                        getItem().getItemType().ordinal();
                            }
                        }
                    }
                    out.write(blockData);
                    out.write(itemData);
                }
                while (true) {
                    int length = in.readInt();
                    byte[] data = new byte[length];
                    if (length > 0) {
                        in.readFully(data, 0, data.length);
                    }
                    ByteBuffer wrapped = ByteBuffer.wrap(data);
                    byte switchByte = data[0];
                    System.out.println("Updating: " + switchByte);
                    byte xCoord, yCoord;
                    byte playerId;
                    switch (switchByte) {
                        // not sure about the data about which player, we might be able to decipher that with the different sockets
                        case 1:
                            // On player coord change
                            // data should have which player + new coord + if a movement key is pressed down (WASD)
                            // update the player coord in player data + send to all other players
                            player.setX((float) wrapped.getFloat(1));
                            player.setY((float) wrapped.getFloat(9));
                            break;
                        case 2:
                            // On player placed bomb
                            // data should have coord of bomb.  IMPLEMENT LATER : have time the bomb was placed
                            // update bomb in bomb data + send to all other players => NOT SURE
                            xCoord = data[1];
                            yCoord = data[2];
                            for (ServerBomb bomb : gameState.getMap().getActiveBombArray()) {
                                if (bomb.getXCoord() == xCoord & bomb.getYCoord() == yCoord) {
                                    break;
                                } else {
                                    gameState.getMap().getActiveBombArray().add(new ServerBomb(xCoord, yCoord));
                                    out.write(data);
                                }
                            }
                            break;
                        case 3:
                            // On block broken
                            // data should have coord of broken block.
                            // update the block in map data + send to all other players
                            xCoord = data[1];
                            yCoord = data[2];
                            gameState.getMap().blockMatrix[xCoord][yCoord] = new EmptyBlock(xCoord, yCoord);
                            break;
                        case 4:
                            // On item dropped
                            // data should have type of item + coord of item
                            // update item in item data + send to all other players => NOT SURE
                            xCoord = data[1];
                            yCoord = data[2];
                            ServerItem item;
                            switch (data[9]) {
                                case 1:
                                    item = new ServerItem(xCoord, yCoord, ItemTypes.BombUp);
                                case 2:
                                    item = new ServerItem(xCoord, yCoord, ItemTypes.RangeUp);
                                default:
                                    item = new ServerItem(xCoord, yCoord, ItemTypes.SpeedUp);
                            }
                            gameState.getMap().getActiveItemArray().add(item);
                            break;
                        case 5:
                            // On item pickedup
                            // data should have which player and item coords
                            // update the player fields in player data + send to all other players
                            xCoord = data[1];
                            yCoord = data[2];
                            for (ServerItem activeItem : gameState.getMap().getActiveItemArray()) {
                                if (activeItem.getXCoord() == xCoord & activeItem.getYCoord() == yCoord) {
                                    gameState.getMap().getActiveItemArray().remove(activeItem);
                                    out.write(data);
                                    break;
                                }
                            }
                            break;
                        case 6:
                            // On player death
                            // data should have which player
                            // send to all other players
                            playerId = data[1];
                            gameState.getPlayerList().remove(gameState.getPlayerList().get(playerId));
                            break;
                        case 7:
                            // Currently do not have a case for this one
                            break;
                        case 8:
                            // on Bomb explodes
                            // data should have player data // MAYBE should have bomb range
                            // send to all other players
                            xCoord = data[1];
                            yCoord = data[2];
                            for (int i = 0; i < gameState.getMap().getActiveBombArray().size(); i++) {
                                if (gameState.getMap().getActiveBombArray().get(i).getXCoord() == xCoord &&
                                        gameState.getMap().getActiveBombArray().get(i).getYCoord() == yCoord) {
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
            for (DataOutputStream outputStream : clientList) {
                if (out != outputStream) {
                    outputStream.writeInt(data.length);
                    outputStream.write(data);
                }
            }
        }

        public void copyArrayToAnotherWithStartingIndexes(byte[] fromArray, byte[] toArray, int toArrayIndex) {
            for (byte fromArrayByte : fromArray) {
                toArray[toArrayIndex] = fromArrayByte;
                toArrayIndex++;
            }
        }
    }
}
