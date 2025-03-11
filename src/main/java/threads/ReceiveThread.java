package threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SocketHandler;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * The thead receiving message from clients
 * This thread is responsible for receiving messages through given socket
 */
public class ReceiveThread extends Thread{
    private static final Logger log = LoggerFactory.getLogger(ReceiveThread.class);

    private Socket socket;

    private SocketHandler socketHandler;

    /**
     * Constructor of the ReceiveThread with given socket
     *
     * @param socket the socket used for receiving messages
     */
    public ReceiveThread(Socket socket){
        if(socket == null){
            log.error("Receive Thread Error: socket is null");
            throw new IllegalArgumentException("Socket is null");
        }
        this.socket = socket;
        this.socketHandler = new SocketHandler(this.socket);
        log.info("ReceiveThread Initialized Successfully");
    }

    @Override
    public void run(){
        log.info("ReceiveThread is running...");
        try {
            String message = socketHandler.receiveMessage();
            log.info("Receive Message Successfully: {}", message);
        } catch (Exception e) {
            log.error("Receive Message Error", e);
        }

    }
}
