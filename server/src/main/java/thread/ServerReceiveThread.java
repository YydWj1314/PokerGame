package thread;

import model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MessageBuffer;
import utils.SocketHandler;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ServerReceiveThread extends Thread {
    private static final Logger log = LoggerFactory.getLogger(ServerReceiveThread.class);

    private Socket socket;

    private SocketHandler socketHandler;



    /**
     * @param socket
     */
    public ServerReceiveThread(Socket socket) {
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
        log.info("ServerReceiveThread Receiving Message...");

        while (true) {
            String message = socketHandler.receiveMessage();
            log.info("Message Received Successfully: {}", message);
            MessageBuffer.addMessage(this.socket, message);
        }

    }
}