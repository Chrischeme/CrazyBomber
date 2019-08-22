package com.mygdx.crazybomber.model.client;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.crazybomber.model.block.EmptyBlock;
import com.mygdx.crazybomber.model.bomb.Bomb;
import com.mygdx.crazybomber.model.gameState.GameState;
import com.mygdx.crazybomber.model.item.Item;
import com.mygdx.crazybomber.model.item.ItemTypes;
import com.mygdx.crazybomber.model.map.Map;
import com.mygdx.crazybomber.model.player.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrazyBomberClient {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private GameState _gameState;
    private Player _player;
    private Texture playerTexture;

    public CrazyBomberClient(String serverAddress, Texture texture) {
        this.playerTexture = texture;
        try {
            this.socket = new Socket("100.2.163.226", 3000);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            byte[] coordinates = new byte[2];
            in.readFully(coordinates, 0, coordinates.length);

            byte[] data = new byte[2];
            in.readFully(data, 0, data.length);

            byte[] byteArray = new byte[((int) data[0]) * ((int) data[1])];
            in.readFully(byteArray, 0, ((int) data[0]) * ((int) data[1]));
            byte[][] blockByte2dArray = new byte[data[0]][data[1]];
            for (int i = 0; i < data[1]; i++) {
                for (int j = 0; j < data[0]; j++) {
                    blockByte2dArray[i][j] = byteArray[j + i * data[0]];
                }
            }

            in.readFully(byteArray, 0, data[0] * data[1]);
            byte[][] itemBlockByte2dArray = new byte[data[0]][data[1]];
            for (int i = 0; i < data[1]; i++) {
                for (int j = 0; j < data[0]; j++) {
                    itemBlockByte2dArray[i][j] = byteArray[j + i * data[0]];
                }
            }

            byte playerId = 1;
            _gameState = new GameState(new Map(blockByte2dArray, itemBlockByte2dArray));
            if (coordinates[0] == 0 & coordinates[1] == 14) {
                playerId = 2;
            } else if (coordinates[0] == 14 & coordinates[1] == 14) {
                playerId = 3;
            } else if (coordinates[0] == 14 & coordinates[1] == 0) {
                playerId = 4;
            }
            _player = new Player(playerId, coordinates[0], coordinates[1], _gameState.getMap(), texture, this);
            _gameState.getPlayerList().add(_player);

            int length = in.readInt();
            data = new byte[length * 3];
            if (length > 0) {
                in.readFully(data, 0, data.length);
            }

            for (int i = 0; i < length; i++) {
                getGameState().getPlayerList().add(new Player(data[i * 3], data[i * 3 + 1], data[i * 3 + 2], playerTexture));
            }


            /*System.out.println("playerId is " + playerId);
            System.out.println("player coordinates are X:" + _gameState.getPlayerList().get(playerId).getX() + "Y:" + _gameState.getPlayerList().get(playerId).getY());
            for (byte[] arr : blockByte2dArray) {
                for (byte b : arr) {
                    System.out.print(b + " ");
                }
                System.out.println();
            }

            for (byte[] arr : itemBlockByte2dArray) {
                for (byte b : arr) {
                    System.out.print(b + " ");
                }
                System.out.println();
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }

        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.execute(new CrazyBomberClient.Handler(in, playerTexture));
    }

    public GameState getGameState() {
        return _gameState;
    }

    public Player getPlayer() {
        return _player;
    }

    public void sendOnNewPlayer(byte x, byte y, byte playerId) throws IOException {
        byte[] data = new byte[4];
        data[0] = 7;
        data[1] = x;
        data[2] = y;
        data[3] = playerId;
        sendByteArray(data);
    }

    public void sendOnPlayerDeath() throws IOException {
        byte[] data = new byte[1];
        data[0] = 6;
        sendByteArray(data);
    }

    public void sendOnItemPickedUp(byte x, byte y, byte itemType) throws IOException {
        byte[] data = new byte[5];
        data[0] = 5;
        data[1] = x;
        data[2] = y;
        data[3] = itemType;
        data[4] = _player.getPlayerId();
        sendByteArray(data);
    }

    // Think about combining this with block broken
    public void sendOnItemDropped(byte x, byte y, byte itemType) throws IOException {
        byte[] data = new byte[4];
        data[0] = 4;
        data[1] = x;
        data[2] = y;
        data[3] = itemType;
        sendByteArray(data);
    }

    public void sendOnBlockBroken(byte x, byte y) throws IOException {
        byte[] data = new byte[3];
        data[0] = 3;
        data[1] = x;
        data[2] = y;
        sendByteArray(data);
    }

    public void sendOnBombPlaced(byte x, byte y) throws IOException {
        byte[] data = new byte[4];
        data[0] = 2;
        data[1] = x;
        data[2] = y;
        data[3] = _player.getPlayerId();
        sendByteArray(data);
    }

    // headingDirection => 0 if no keys held down
    //                     1 if going up
    //                     2 if going right
    //                     3 if going down
    //                     4 if going left
    public void sendOnPlayerCoordinateChange(float x, float y, byte headingDirection) throws IOException {
        System.out.println(x + " X ");
        System.out.println(y + " Y ");
        byte[] data = new byte[10];
        data[0] = 1;
        byte[] floatInByteArray = new byte[4];
        ByteBuffer.wrap(floatInByteArray).putFloat(x);
        copyArrayToAnotherWithStartingIndexes(floatInByteArray, data, 1);
        ByteBuffer.wrap(floatInByteArray).putFloat(y);
        copyArrayToAnotherWithStartingIndexes(floatInByteArray, data, 5);
        data[9] = headingDirection;
        sendByteArray(data);
    }

    public void copyArrayToAnotherWithStartingIndexes(byte[] fromArray, byte[] toArray, int toArrayIndex) {
        for (byte fromArrayByte : fromArray) {
            toArray[toArrayIndex] = fromArrayByte;
            toArrayIndex++;
        }
    }

    public void sendByteArray(byte[] data) throws IOException {
        out.writeInt(data.length);
        out.write(data);
    }

    private class Handler implements Runnable {
        private DataInputStream in;
        private Texture playerTexture;

        public Handler(DataInputStream in, Texture texture) {
            this.in = in;
            this.playerTexture = texture;
        }

        public void run() {
            while (true) {
                try {
                    int length = in.readInt();
                    byte[] data = new byte[length];
                    in.readFully(data, 0, data.length);
                    if (length > 0) {
                    ByteBuffer wrapped = ByteBuffer.wrap(data);
                    byte switchByte = data[0];
                    System.out.println("Updating: " + switchByte);
                    byte xCoord, yCoord;
                    byte playerId;
                    byte headingDirection;

                    System.out.println("START");
                    for (byte b : data) {
                        System.out.print(b + " ");
                    }
                    System.out.println("END");
                    switch (switchByte) {
                        // not sure about the data about which player, we might be able to decipher that with the different sockets
                        case 1:
                            playerId = data[10];
                            headingDirection = wrapped.get(9);
                            for (Player player : getGameState().getPlayerList()) {
                                if (player.getPlayerId() == playerId) {
                                    player.setX(180 + (float) wrapped.getFloat(1) * 48);
                                    player.setY(180 + (float) wrapped.getFloat(5) * 48);
                                }
                            }
                            data[9] = wrapped.get(9);
                            break;
                        case 2:
                            // On player placed bomb
                            // data should have coord of bomb.  IMPLEMENT LATER : have time the bomb was placed
                            // update bomb in bomb data => NOT SURE
                            xCoord = data[1];
                            yCoord = data[2];
                            playerId = data[3];
                            for (Player player : getGameState().getPlayerList()) {
                                if (player.getPlayerId() == playerId) {
                                    Bomb bomb = new Bomb(player, player.getBombStack());
                                    getGameState().getMap().getActiveBombArray().add(bomb);
                                }
                            }
                            break;
                        case 3:
                            // On block broken
                            // data should have coord of broken block.
                            // update the block in map data
                            xCoord = data[1];
                            yCoord = data[2];
                            getGameState().getMap().blockMatrix[xCoord][yCoord] = new EmptyBlock(xCoord, yCoord);
                            break;
                        case 4:
                            // On item dropped
                            // data should have type of item + coord of item
                            // update item in item data => NOT SURE
                            xCoord = data[1];
                            yCoord = data[2];
                            Item item;
                            switch (data[3]) {
                                case 1:
                                    item = new Item(xCoord, yCoord, ItemTypes.BombUp);
                                case 2:
                                    item = new Item(xCoord, yCoord, ItemTypes.RangeUp);
                                default:
                                    item = new Item(xCoord, yCoord, ItemTypes.SpeedUp);
                            }
                            getGameState().getMap().getActiveItemArray().add(item);
                            break;
                        case 5:
                            // On item pickedup
                            // data should have which player and item coords
                            // update the player fields in player data
                            xCoord = data[1];
                            yCoord = data[2];
                            byte itemType = data[3];
                            playerId = data[4];
                            for (Item activeItem : getGameState().getMap().getActiveItemArray()) {
                                if (activeItem.getXCoord() == xCoord & activeItem.getYCoord() == yCoord) {
                                    getGameState().getMap().getActiveItemArray().remove(activeItem);
                                    activeItem = null;
                                }
                            }
                            if (itemType == 1) {
                                for (Player player : getGameState().getPlayerList()) {
                                    if (player.getPlayerId() == playerId) {
                                        final Bomb newBomb = new Bomb(player, player.getBombStack());
                                        player.getBombStack().push(newBomb);
                                    } else if (itemType == 2) {
                                        player.setNumRangeUpgrades(
                                                player.getNumRangeUpgrades() + 1);
                                    } else if (itemType == 3) {
                                        player.setSpeed(
                                                player.getSpeed() + (float) 1.0);
                                    }
                                }
                            }
                            break;
                        case 6:
                            // On player death
                            // data should have which player
                            // send to all other players
                            playerId = data[1];
                            for (Player player : getGameState().getPlayerList()) {
                                if (player.getPlayerId() == playerId) {
                                    getGameState().getPlayerList().remove(player);
                                }
                            }
                            break;
                        case 7:
                            // On player creation
                            // data should have player coordinates and playerID
                            // send to all others
                            xCoord = data[1];
                            yCoord = data[2];
                            playerId = data[3];
                            getGameState().getPlayerList().add(new Player(xCoord, yCoord, playerId, playerTexture));
                            break;
                        case 8:
                            // on Bomb explodes
                            // data should have player data // MAYBE should have bomb range
                            xCoord = data[1];
                            yCoord = data[2];
                            for (int i = 0; i < getGameState().getMap().getActiveBombArray().size(); i++) {
                                if (getGameState().getMap().getActiveBombArray().get(i).getX() == xCoord &&
                                        getGameState().getMap().getActiveBombArray().get(i).getY() == yCoord) {
                                    getGameState().getMap().getActiveBombArray().remove(i);
                                    break;
                                }
                            }
                            break;
                        default:
                            // Unspecified
                            // ignore??
                            break;
                    }

                } }catch (IOException e) {
                }
            }
            //e.printStackTrace();
        }
    }
}