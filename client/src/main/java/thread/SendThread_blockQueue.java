package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SocketHandler;

import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * The thread sending message to the server
 * This thread is responsible for sending messages through the given socket
 */
public class SendThread_blockQueue extends Thread {
    private static final Logger log = LoggerFactory.getLogger(SendThread_blockQueue.class);

    private Socket socket;

    private SocketHandler socketHandler;

    private BlockingDeque<String> meesageQueue = new LinkedBlockingDeque<>();

    /**
     * Constructor of the SendThread_blockQueue with given socket
     *
     * @param socket the socket used for communication
     */
    public SendThread_blockQueue(Socket socket) {
        if (socket == null) {
            log.error("SendThread_blockQueue Initialize Error: socket is null");
            throw new IllegalArgumentException("Socket is null");
        }
        this.socket = socket;
        this.socketHandler = new SocketHandler(this.socket);
        log.info("SendThread_blockQueue Initialized Successfully");
    }

    /**
     * @param message
     */
    public void sendMessage(String message) {
        // Putting message in the block queue
        try {
            meesageQueue.put(message);
            log.info("Message Enqueue: {}", message);

        } catch (InterruptedException e) {
            log.error("Message Enqueue Error", e);
        }
    }

    /**
     *
     */
    @Override
    public void run() {
        log.info("SendingThread is running...");
        try {
            while (socket != null && !socket.isClosed()) {
                String message = meesageQueue.take();  // taking message
                socketHandler.sendMessage(message);
                log.info("Message Send Successfully: {}", message);
            }
        } catch (Exception e) {
            log.error("Message Send Error", e);
        }
    }


}
