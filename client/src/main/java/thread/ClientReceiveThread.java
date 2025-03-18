package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ClientMessageBuffer;
import utils.SocketHandler;

import java.net.Socket;


public class ClientReceiveThread extends Thread {
    private static final Logger log = LoggerFactory.getLogger(ClientReceiveThread.class);

    private Socket socket;

    private SocketHandler socketHandler;



    /**
     * @param socket
     */
    public ClientReceiveThread(Socket socket) {
        this.socket = socket;
    }

    public void ReceiveThread(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public SocketHandler getSocketHandler() {
        return socketHandler;
    }

    public void setSocketHandler(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @Override
    public void run(){
        socketHandler = new SocketHandler(this.socket);
        log.info("ClientReceiveThread Receiving Message...");

        while (true) {
            String message = socketHandler.receiveMessage();
            log.info("Message received successfully: {}", message);
            ClientMessageBuffer.addMessage(this.socket, message);
            log.info("Message added to ClientMessageBuffer: {}", message);

        }

    }
}