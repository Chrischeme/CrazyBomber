package com.mygdx.crazybomber.model.player;

import com.mygdx.crazybomber.model.bomb.Bomb;
import com.mygdx.crazybomber.model.map.Map;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class CrazyBomberClient {
    // this class should have two threads; one for listening and one for running the game
    // Look into Future<> for async
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Map _map;

    public CrazyBomberClient(String serverAddress, Map map) throws IOException {
        _map = map;
        this.socket = new Socket(serverAddress, 59898);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.execute(new CrazyBomberClient.Handler(in));

        int length = in.readInt();
        byte[] data = new byte[length];
        if (length > 0) {
            in.readFully(data, 0, data.length);



        }
        for (byte b : data) {
            System.out.println(b);
        }
        ByteBuffer wrapped = ByteBuffer.wrap(data);
        System.out.println(wrapped.getInt(0));
        System.out.println(wrapped.getInt(4));
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
    public void sendOnPlayerCoordinateChange(double x, double y, byte headingDirection) throws IOException {
        byte[] data = new byte[18];
        data[0] = 1;
        byte[] doubleInByteArray = new byte[8];
        ByteBuffer.wrap(doubleInByteArray).putDouble(x);
        copyArrayToAnotherWithStartingIndexes(doubleInByteArray, data, 1);
        ByteBuffer.wrap(doubleInByteArray).putDouble(y);
        copyArrayToAnotherWithStartingIndexes(doubleInByteArray, data, 9);
        data[17] = headingDirection;
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

    public Map getMap() {
        return _map;
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