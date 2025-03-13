package threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ServerCmdHandler;
import utils.SocketHandler;

import java.net.Socket;

/**
 * The thead receiving message from clients
 * This thread is responsible for receiving messages through given socket
 */
public class ServerReceiveThread extends Thread{
    private static final Logger log = LoggerFactory.getLogger(ServerReceiveThread.class);

    private Socket socket;

    private SocketHandler socketHandler;

    private ServerCmdHandler serverCmdHandler;


    /**
     * Constructor of the ServerReceiveThread with given socket
     *
     * @param socket the socket used for receiving messages
     */
    public ServerReceiveThread(Socket socket, ServerCmdHandler serverCmdHandler){
        if(socket == null){
            log.error("Receive Thread Error: socket is null");
            throw new IllegalArgumentException("Socket is null");
        }
        this.socket = socket;
        this.socketHandler = new SocketHandler(this.socket);
        this.serverCmdHandler = serverCmdHandler;
        log.info("ServerReceiveThread Initialized Successfully");
    }

    @Override
    public void run(){
        log.info("ServerReceiveThread is running...");
        try {
            while (socket != null && !socket.isClosed()) {
                String message = socketHandler.receiveMessage();
                log.info("Receive Message Successfully: {}", message);
                serverCmdHandler.handleCmd(socket, message);
                log.info("Handle Message Successfully");
            }
        } catch (Exception e) {
            log.error("Receive Message Error", e);
        }
    }
}
