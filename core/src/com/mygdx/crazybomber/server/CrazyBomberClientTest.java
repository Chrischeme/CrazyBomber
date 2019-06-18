package com.mygdx.crazybomber.server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.nio.ByteBuffer;

public class CrazyBomberClientTest {
    // this class should have two threads; one for listening and one for running the game
    // Look into Future<> for async
    public static class ChatClient {
        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;

        public ChatClient(String serverAddress) {
            this.socket = new Socket(serverAddress, 59898);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        }

        public void sendOnPlayerDeath() throws IOException {
            byte[] data = new byte[1];
            data[0] = 6;
            sendByteArray(data);
        }

        public void sendOnItemPickedUp(byte itemType, byte itemId) throws IOException {
            byte[] data = new byte[];
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
            foreach(byte fromArrayByte : fromArray ){
                toArray[toArrayIndex] = fromArrayByte;
                toArrayIndex++;
            }
        }

        public void sendByteArray(byte[] data) throws IOException {
            out.writeInt(data.length);
            out.write(data);
        }

        public void run() throws IOException {
            try {
                while (true) {
                    int length = new in.readInt();
                    if (length > 0) {
                        byte[] data = new byte[length];
                        in.readFully(data, 0, data.length);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String argv[]) throws Exception {
        ChatClient client = new ChatClient("localhost");
    }
}