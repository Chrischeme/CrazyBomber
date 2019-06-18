package com.mygdx.crazybomber.server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class CrazyBomberClientTest {
    public static class ChatClient {

        String serverAddress;
        Scanner in;
        PrintWriter out;

        public ChatClient(String serverAddress) {
            this.serverAddress = serverAddress;
        }

        private String getName() {
            System.out.println("Enter your nickname");
            return new Scanner(System.in).nextLine();
        }

        public void run() throws IOException {
            try {
                Socket socket = new Socket(serverAddress, 8000);
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);

                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    if (line.startsWith("SUBMITNAME")) {
                        out.println(getName());
                    } else if (line.startsWith("NAMEACCEPTED")) {
                        System.out.println("Write your message");
                        out.println(new Scanner(System.in));
                    } else if (line.startsWith("MESSAGE")) {
                        System.out.println(line.substring(8) + "\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String argv[]) throws Exception {
        ChatClient client = new ChatClient("47.227.192.92");
        client.run();
    }
}