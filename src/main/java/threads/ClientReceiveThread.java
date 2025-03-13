package threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MessageListener;
import utils.SocketHandler;

import java.net.Socket;

public class ClientReceiveThread extends Thread{

    private static final Logger log = LoggerFactory.getLogger(ClientReceiveThread.class);

    private Socket socket;

    private MessageListener messageListener;

    private SocketHandler socketHandler;

    /**
     * Constructor of the ServerReceiveThread with given socket
     *
     * @param socket the socket used for receiving messages
     */
    public ClientReceiveThread(Socket socket, MessageListener messageListener){
        if(socket == null){
            log.error("Receive Thread Error: socket is null");
            throw new IllegalArgumentException("Socket is null");
        }
        this.socket = socket;
        this.messageListener = messageListener;
        this.socketHandler = new SocketHandler(this.socket);
        log.info("ClientReceiveThread Initialized Successfully");
    }

    @Override
    public void run(){
        log.info("ClientReceiveThread is running...");
        try {
            while (!socket.isClosed()) {
                String message = socketHandler.receiveMessage(); //
                if (message != null) {
                    log.info("Client received message: {}", message);
                    messageListener.onMessageReceived(socket, message);
                }
            }
        } catch (Exception e) {
            log.error("ClientReceiveThread Error", e);
        }
    }

}
