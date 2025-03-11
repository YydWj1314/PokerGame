package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 1. Creating Server Socket and waiting for connection
 * 2. Accept connection
 * 3. Create new thread for each client
 * 4. Broadcasting information
 */
public class GameServer {
    private static final String SEVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 10000;
    public static void main(String[] args) throws IOException {
        System.out.println("Server starts running, waiting...");
        // 1. Create server Socket
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        // 2. Waiting for client connection
        while(true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected:" + clientSocket.getInetAddress());
        }

    }
}
