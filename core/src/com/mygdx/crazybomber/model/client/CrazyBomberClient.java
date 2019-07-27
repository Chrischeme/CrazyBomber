package com.mygdx.crazybomber.model.client;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.crazybomber.model.gameState.GameState;
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

            byte[] byteArray = new byte[((int)data[0]) * ((int)data[1])];
            in.readFully(byteArray, 0, ((int)data[0]) * ((int)data[1]));
            byte[][] blockByte2dArray = new byte[data[0]][data[1]];
            for (int i = 0; i < data[1]; i++) {
                for (int j = 0; j < data[0]; j++) {
                    blockByte2dArray[j][i] = byteArray[j + i * data[0]];
                }
            }

            in.readFully(byteArray, 0, data[0] * data[1]);
            byte[][] itemBlockByte2dArray = new byte[data[0]][data[1]];
            for (int i = 0; i < data[1]; i++) {
                for (int j = 0; j < data[0]; j++) {
                    itemBlockByte2dArray[j][i] = byteArray[j + i * data[0]];
                }
            }
            _gameState = new GameState(new Map(blockByte2dArray, itemBlockByte2dArray));
            byte playerId = 0;
            if (coordinates[0] == 0 & coordinates[1] == 1){
                playerId = 1;
            } else if (coordinates[0] == 1 & coordinates[1]==1){
                playerId = 2;
            } else if (coordinates[0]== 1 & coordinates[1]==0){
                playerId =3;
            }
            _player = new Player(playerId, coordinates[0], coordinates[1], _gameState.getMap(), texture, this);
            _gameState.getPlayerList().add(_player);

            System.out.println("playerId is " + playerId);
            System.out.println("player coordinates are X:"+ _player.getX() + "Y:"+_player.getY());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameState getGameState() {
        return _gameState;
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

    public void sendOnItemPickedUp(byte itemType, byte itemId) throws IOException {
        byte[] data = new byte[3];
        data[0] = 5;
        data[1] = itemType;
        data[2] = itemId;
        sendByteArray(data);
    }

    // Think about combining this with block broken
    public void sendOnItemDropped(int x, int y, byte itemType, byte itemId) throws IOException {
        byte[] data = new byte[11];
        data[0] = 4;
        byte[] intInByteArray = new byte[4];
        ByteBuffer.wrap(intInByteArray).putInt(x);
        copyArrayToAnotherWithStartingIndexes(intInByteArray, data, 1);
        ByteBuffer.wrap(intInByteArray).putInt(y);
        copyArrayToAnotherWithStartingIndexes(intInByteArray, data, 5);
        data[9] = itemType;
        data[10] = itemId;
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

    private static class Handler implements Runnable {
        private DataInputStream in;

        public Handler(DataInputStream in) {
            this.in = in;
        }

        public void run() {
            while (true) {
                //todo implement switch case to handle data from the server and do things
               /* if (item.getItemType() == 0) {
                    final Bomb newBomb = new Bomb(this, _bombStack);
                    this._bombStack.push(newBomb);
                } else if ( item.getItemType()== 1){
                    setNumRangeUpgrades(getNumRangeUpgrades() + 1);
                } else {
                    setSpeed(getSpeed() + 1.0);
                }
                item = null;*/
            }
        }
        //e.printStackTrace();
    }
}