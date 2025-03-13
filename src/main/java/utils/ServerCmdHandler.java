package utils;


import controller.GameController;
import model.Command;
import model.enumuration.ExceptionMessage;
import model.exception.InvalidCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;

/**
 * Calling game controller operation according to command types
 */
public class ServerCmdHandler {
    private static final Logger log = LoggerFactory.getLogger(ServerCmdHandler.class);

    private GameController gameController;

    public ServerCmdHandler(GameController gameController) {
        if (gameController == null) {
            throw new IllegalArgumentException("GameController cannot be null");
        }
        this.gameController = gameController;
        log.info("ServerCmdHandler initialized successfully!");
    }


    /**
     *
     */
    public void handleCmd(Socket socket, String message) {
        if(message == null || message.isEmpty()){
            log.error("Message is null");
            throw new InvalidCommandException(ExceptionMessage.NullCommandException.getMessage());
        }

        Command command = new Command(message);
        String cmdType = command.getCmdType();
        String[] cmdParams = command.getCmdParams();

        log.info("ServerCmdHandler handling command: {}...", command);

        switch(cmdType){
            case "JOIN" -> {
                // eg: JOIN Daniel
                // Controller operation
                String username = cmdParams[0];
                log.info("ServerCmdHandler handled {JOIN}, username: {} ", username);
                gameController.addPlayer(socket, username);
                String response = "NOTIFY_CLIENT JOIN_SUCCESS " + username;
                new SocketHandler(socket).sendMessage(response);
                log.info("Sent welcome message to {}", username);
            }

        }
    }

}
