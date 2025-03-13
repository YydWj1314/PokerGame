package Server;

import controller.GameController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import threads.ServerReceiveThread;
import utils.ServerCmdHandler;

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

    public static void main(String[] args) {
        GameController gameController = new GameController();

        ServerCmdHandler serverCmdHandler = new ServerCmdHandler(gameController);

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            log.info("Server starts running, waiting for connections...");
        } catch (IOException e) {
            log.error("Server Socket Error", e);
        }

        try {
            // 1. accept connection and creating receiving thread
            while(true){
                Socket socket = serverSocket.accept();
                log.info("Client connected: {}", socket.getInetAddress());

                // Creating thread receiving message
                ServerReceiveThread serverReceiveThread = new ServerReceiveThread(socket, serverCmdHandler);
                serverReceiveThread.start();

            }
        } catch (IOException e) {
            log.error("Server Error", e);
        }
    }
}
