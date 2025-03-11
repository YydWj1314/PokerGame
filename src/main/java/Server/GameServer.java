package Server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import threads.ReceiveThread;
import threads.SendThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The backed-end server fo the game
 * 1. Creating Server Socket and waiting for connection
 * 2. Accept connection
 * 3. Create and start new thread for each client
 * 4. Broadcasting information
 */
public class GameServer {
    private static final Logger log = LoggerFactory.getLogger(GameServer.class);

    private static final String SEVER_IP = "127.0.0.1";

    private static final int SERVER_PORT = 10086;

    public static String getServerIp() {
        return SEVER_IP;
    }

    public static int getServerPort() {
        return SERVER_PORT;
    }

    public static void main(String[] args) throws IOException {
        log.info("Server starts running, waiting for connections...");

        try {
            // 1. Creating server Socket
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

            while(true){

                // 2. Accept connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("123123");
                log.info("Client connected: {}", clientSocket.getInetAddress());

                // 3. Creating and start thread for each client
                ReceiveThread receiveThread = new ReceiveThread(clientSocket);
                receiveThread.start();
            }
        } catch (IOException e) {
            log.error("Server Error", e);
        }

    }
}
