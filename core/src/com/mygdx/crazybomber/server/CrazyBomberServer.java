package com.mygdx.crazybomber.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrazyBomberServer {
    private static Set<String> names = new HashSet<>();
    private static Set<PrintWriter> writers = new HashSet<>();

    public static void main (String[] args) throws IOException {
        try (ServerSocket listener = new ServerSocket(59898)) {
            System.out.println("The server is running...");
            ExecutorService pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute(new Handler(listener.accept()));
            }
        }
    }
    private static class Handler implements Runnable {
        private String name;
        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void updateMapData() {

        }

        public void run() {
            try {
                out = new DataOutputStream(socket.getOutputStream());
                in = new DataInputStream(socket.getInputStream());
                while(true) {
                    int length = in.readInt();
                    if (length > 0) {
                        byte[] data = new byte[length];
                        in.readFully(data, 0, data.length);
                    }
                    int switchByte = (int) data[0];
                    switch (switchByte) {
                        case 1:
                            // On player coord change
                            // data should have new coord + if a movement key is pressed down (WASD)
                            // update the player coord in player data + send to all other players
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
                            break;
                        case 4:
                            // On item dropped
                            // data should have type of item + coord of item
                            // update item in item data + send to all other players => NOT SURE
                            break;
                        case 5:
                            // On player death
                            // not sure there should be data
                            // send to all other players
                            break;
                        default:
                            // Unspecified
                            // ignore??
                            break;
                    }
                    updateMapData();
                }
            }
        }
    }
}
