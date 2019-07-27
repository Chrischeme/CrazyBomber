package com.mygdx.crazybomber.model.client;

import com.badlogic.gdx.graphics.Texture;
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

    public CrazyBomberClient(String serverAddress, Texture texture) {
        try {
            this.socket = new Socket(serverAddress, 59898);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            ExecutorService pool = Executors.newFixedThreadPool(1);
            pool.execute(new CrazyBomberClient.Handler(in));

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

            byte playerId = 0;
            _gameState = new GameState(new Map(blockByte2dArray, itemBlockByte2dArray));
            if (coordinates[0] == 0 & coordinates[1] == 14) {
                playerId = 1;
            } else if (coordinates[0] == 14 & coordinates[1] == 14) {
                playerId = 2;
            } else if (coordinates[0] == 14 & coordinates[1] == 0) {
                playerId = 3;
            }
            _player = new Player(playerId, coordinates[0], coordinates[1], _gameState.getMap(), texture, this);
            _gameState.getPlayerList().add(_player);

            System.out.println("playerId is " + playerId);
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
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameState getGameState() {
        return _gameState;
    }

    public Player getPlayer() {
        return _player;
    }

    public void sendOnNewPlayer(int x, int y) throws IOException {
        byte[] data = new byte[9];
        data[0] = 7;
        byte[] intInByteArray = new byte[4];
        ByteBuffer.wrap(intInByteArray).putInt(x);
        copyArrayToAnotherWithStartingIndexes(intInByteArray, data, 1);
        ByteBuffer.wrap(intInByteArray).putInt(y);
        copyArrayToAnotherWithStartingIndexes(intInByteArray, data, 5);
        sendByteArray(data);
    }

    public void sendOnPlayerDeath() throws IOException {
        byte[] data = new byte[1];
        data[0] = 6;
        sendByteArray(data);
    }

    public void sendOnItemPickedUp(int x, int y, byte itemType) throws IOException {
        byte[] data = new byte[11];
        data[0] = 5;
        byte[] intInByteArray = new byte[4];
        ByteBuffer.wrap(intInByteArray).putInt(x);
        copyArrayToAnotherWithStartingIndexes(intInByteArray, data, 1);
        ByteBuffer.wrap(intInByteArray).putInt(y);
        copyArrayToAnotherWithStartingIndexes(intInByteArray, data, 5);
        data[9] = itemType;
        data[10] = _player.getPlayerId();
        sendByteArray(data);
    }

    // Think about combining this with block broken
    public void sendOnItemDropped(int x, int y, byte itemType) throws IOException {
        byte[] data = new byte[10];
        data[0] = 4;
        byte[] intInByteArray = new byte[4];
        ByteBuffer.wrap(intInByteArray).putInt(x);
        copyArrayToAnotherWithStartingIndexes(intInByteArray, data, 1);
        ByteBuffer.wrap(intInByteArray).putInt(y);
        copyArrayToAnotherWithStartingIndexes(intInByteArray, data, 5);
        data[9] = itemType;
        sendByteArray(data);
    }

    public void sendOnBlockBroken(int x, int y) throws IOException {
        byte[] data = new byte[9];
        data[0] = 3;
        byte[] intInByteArray = new byte[4];
        ByteBuffer.wrap(intInByteArray).putInt(x);
        copyArrayToAnotherWithStartingIndexes(intInByteArray, data, 1);
        ByteBuffer.wrap(intInByteArray).putInt(y);
        copyArrayToAnotherWithStartingIndexes(intInByteArray, data, 5);
        sendByteArray(data);
    }

    public void sendOnBombPlaced(int x, int y) throws IOException {
        byte[] data = new byte[9];
        data[0] = 2;
        byte[] intInByteArray = new byte[4];
        ByteBuffer.wrap(intInByteArray).putInt(x);
        copyArrayToAnotherWithStartingIndexes(intInByteArray, data, 1);
        ByteBuffer.wrap(intInByteArray).putInt(y);
        copyArrayToAnotherWithStartingIndexes(intInByteArray, data, 5);
        sendByteArray(data);
    }

    // headingDirection => 0 if no keys held down
    //                     1 if going up
    //                     2 if going right
    //                     3 if going down
    //                     4 if going left
    public void sendOnPlayerCoordinateChange(float x, float y, byte headingDirection) throws IOException {
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

        public Handler(DataInputStream in) {
            this.in = in;
        }

        public void run() {
            while (true) {
                try {
                    int length = in.readInt();
                    byte[] data = new byte[length];
                    if (length > 0) {
                        in.readFully(data, 0, data.length);
                    }
                    ByteBuffer wrapped = ByteBuffer.wrap(data);
                    byte switchByte = data[0];
                    System.out.println("Updating: " + switchByte);
                    int xCoord, yCoord;
                    byte playerId;
                    switch (switchByte) {
                        // not sure about the data about which player, we might be able to decipher that with the different sockets
                        case 1:
                            // On player coord change
                            // data should have which player + new coord + if a movement key is pressed down (WASD)
                            // update the player coord in player data + send to all other players
                            break;
                        case 2:
                            // On player placed bomb
                            // data should have coord of bomb.  IMPLEMENT LATER : have time the bomb was placed
                            // update bomb in bomb data + send to all other players => NOT SURE
                        case 3:
                            // On block broken
                            // data should have coord of broken block.
                            // update the block in map data + send to all other players
                        case 4:
                            // On item dropped
                            // data should have type of item + coord of item
                            // update item in item data + send to all other players => NOT SURE
                            break;
                        case 5:
                            // On item pickedup
                            // data should have which player and item coords
                            // update the player fields in player data + send to all other players
                            xCoord = wrapped.getInt(1);
                            yCoord = wrapped.getInt(5);
                            byte itemType = data[9];
                            playerId = data[10];
                            for (Item activeItem : getGameState().getMap().getActiveItemArray()) {
                                if (activeItem.getXCoordinate() == xCoord & activeItem.getYCoordinate() == yCoord) {
                                    getGameState().getMap().getActiveItemArray().remove(activeItem);
                                    activeItem = null;
                                }
                            }
                            if (itemType == 1) {
                                final Bomb newBomb = new Bomb(getGameState().getPlayerList().get(playerId),
                                        getGameState().getPlayerList().get(playerId).getBombStack());
                                getGameState().getPlayerList().get(playerId).getBombStack().push(newBomb);
                            } else if (itemType == 2) {
                                getGameState().getPlayerList().get(playerId).setNumRangeUpgrades(
                                        getGameState().getPlayerList().get(playerId).getNumRangeUpgrades() + 1);
                            } else if (itemType == 3) {
                                getGameState().getPlayerList().get(playerId).setSpeed(
                                        getGameState().getPlayerList().get(playerId).getSpeed() + (float) 1.0);
                            }
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
                            break;
                        default:
                            // Unspecified
                            // ignore??
                            break;
                    }

                } catch (IOException e) {
                }
            }
            //e.printStackTrace();
        }
    }
}