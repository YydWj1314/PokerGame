package threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SocketHandler;

import java.net.Socket;

/**
 * The thread sending message to the server
 * This thread is responsible for sending messages through the given socket
 */
public class SendThread extends Thread {
    private static final Logger log = LoggerFactory.getLogger(SendThread.class);

    private Socket socket;

    private String message;

    private SocketHandler socketHandler;

    /**
     * Constructor of the SendThread with given socket
     *
     * @param socket the socket used for communication
     * @param message message to be sent
     */
    public SendThread(Socket socket, String message) {
        if (socket == null) {
            log.error("SendThread Initialize Error: socket is null");
            throw new IllegalArgumentException("Socket is null");
        }
        if (message == null || message.trim().isEmpty()) {
            log.error("SendThread Initialize Error: message is null");
        }
        this.socket = socket;
        this.message = message;
        this.socketHandler = new SocketHandler(this.socket);
        log.info("SendThread Initialized Successfully");
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        log.info("SendingThread is running...");
        try {
            socketHandler.sendMessage(message);
            log.info("Send Message Successfully: {}", message);
        } catch (Exception e) {
            log.error("Send Message Error", e);
        }
    }


}
