package view;

import Controller.GameController;
import model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ServerReceiveThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    private static final Logger log = LoggerFactory.getLogger(GameServer.class);

    private static final int SERVER_PORT = 10087;

    private GameController gameController;

    public GameServer(){
        // Starting GameController
        gameController = new GameController();

        // Accepting connection
        log.info("Server starts running, waiting for connections...");
        try {
                ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            while (true) {
                Socket socket = serverSocket.accept();

                // Opening thread receiving message
                ServerReceiveThread serverReceiveThread = new ServerReceiveThread(socket);
                serverReceiveThread.start();
            }

        } catch (IOException e) {
            log.error("Server Socket Error", e);
        }

    }
}
