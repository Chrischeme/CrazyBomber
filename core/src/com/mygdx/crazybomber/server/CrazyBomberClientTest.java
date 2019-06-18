package com.mygdx.crazybomber.server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class CrazyBomberClientTest {
    // this class should have two threads; one for listening and one for running the game
    public static class ChatClient {
        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;

        public ChatClient(String serverAddress) {
            this.socket = new Socket(serverAddress, 59898);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
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
        client.run();
    }
}